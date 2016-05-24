/**
 * 
 */
package org.tlg.bot.mem.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgDocumentVideo;
import org.tlg.bot.mem.db.domain.TlgPhoto;
import org.tlg.bot.mem.db.domain.TlgSticker;
import org.tlg.bot.mem.db.domain.TlgVideo;
import org.tlg.bot.mem.db.ds.DsHikari;
import org.tlg.bot.mem.msg.TextMessage;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class UploadCommand implements Command {

    private static final Logger log = LoggerFactory
        .getLogger(UploadCommand.class.getName());

    private final Message message;

    public UploadCommand(final Message message) {
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.tlg.bot.mem.commands.Command#execute()
     */
    @Override
    public void execute(final MemBot sender) {
        try {
            sender.answerAwait(this.message.getChatId(), this);
            sender.sendMessage(
                new TextMessage(
                    message.getChatId(), askTags()
                    )
                );
        } catch (final TelegramApiException e) {
            log.error("Can't send message", e);
        }

    }

    private String askTags() {
        if (message.getSticker() != null) {
            return "Please, input tags for this sticker";
        }
        return "Please, input tags for this photo";
    }

    @Override
    public void resume(final MemBot sender, final Update update) {
   
        // if tags are correct - save picture
        if (update.getMessage().hasText()) {
            final Tags tags = new Tags(update.getMessage().getText());
            if (!tags.isEmpty()) {
                processMessage(this.message, tags);
                try {
                    sender.sendMessage(
                        new TextMessage(
                            update.getMessage().getChatId(),
                            "Picture is saved successfully" 
                            )
                        );
                } catch (final TelegramApiException e) {
                    log.error("Can't send message", e);
                }
            } else {
             // else add itself to queue and wait for correct tags
                sender.answerAwait(update.getMessage().getChatId(), this);
            }
        }
        
    }

    private void processMessage(final Message message, final Tags tags) {
     
        if (message.getPhoto() != null) {
            saveMedia(photo(message), tags);
            return;
        }
        if (message.getSticker() != null) {
            saveMedia(
                new TlgSticker(
                    message.getFrom().getId(),
                    message.getSticker().getFileId()),
                tags);
            return;
        }
        
        if (message.getVideo() != null) {
            saveMedia(new TlgVideo(message), tags);
            return;
        }
        
        //forwarded media sent as document
        if (message.getDocument() != null) {
            saveMedia(new TlgDocumentVideo(message), tags);
        }
        
    }

    private Picture photo(final Message message) {
        //original(it seems like last) photoId will be saved
        final PhotoSize ps =
            message.getPhoto().get(message.getPhoto().size() - 1);
        return new TlgPhoto(ps, message.getFrom().getId());
        
    }

    private void saveMedia(final Picture media, final Tags tags) {
        try {
            log.debug("Try save tags [{}] for media", tags, media);
            new RepTags(DsHikari.ds()).save(
                new MediaTags(media, tags)
                );
        } catch (final Exception e) {
            log.error("Can't save media", e);
        }
        
    }

}
