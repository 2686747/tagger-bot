/**
 * 
 */
package org.tlg.bot.mem.commands;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Sticker;
import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgPhoto;
import org.tlg.bot.mem.db.domain.TlgSticker;
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
                savePicture(this.message, tags);
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

    private void savePicture(final Message message, final Tags tags) {
     
        if (message.getPhoto() != null) {
            savePhoto(message.getPhoto(), tags);
            return;
        }
        if (message.getSticker() != null) {
            saveSticker(message.getSticker(), tags);
            return;
        }
 
    }

    private void saveSticker(final Sticker sticker, final Tags tags) {
        try {
            
            final TlgSticker photo =
                new TlgSticker(message.getFrom().getId(), sticker.getFileId());
//            log.debug("Try save photo:{}", photo);
//            new RepPhotos(DsHikari.ds()).save(photo);
            log.debug("Try save tags:{}", tags);
            new RepTags(DsHikari.ds()).save(
                new MediaTags(photo, tags)
                );
        } catch (final Exception e) {
            log.error("Can't save photo", e);
        }
        
    }

    private void savePhoto(final List<PhotoSize> photos, final Tags tags) {
        //first photoId will be saved, size doesn't matter
        final PhotoSize ps = photos.iterator().next();
            try {
               
                final TlgPhoto photo =
                    new TlgPhoto(ps, message.getFrom().getId());
//                log.debug("Try save photo:{}", photo);
//                new RepPhotos(DsHikari.ds()).save(photo);
                log.debug("Try save tags:{}", tags);
                new RepTags(DsHikari.ds()).save(
                    new MediaTags(photo, tags)
                    );
            } catch (final Exception e) {
                log.error("Can't save photo", e);
            }
        
    }



}
