/**
 * 
 */
package org.tlg.bot.mem.commands;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgPhoto;
import org.tlg.bot.mem.db.domain.TlgSticker;
import org.tlg.bot.mem.db.ds.DsHikari;
import org.tlg.bot.mem.msg.HtmlMessage;
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
        sender.answerAwait(message.getChatId(), this);
        try {
            sender.sendMessage(
                new HtmlMessage(message.getChatId(), uploadResponse())
            );
        } catch (final TelegramApiException e) {
            log.error(e.getApiResponse(), e);
            
            internalError(sender);
         } catch (final Exception e) {
           log.error(e.getMessage(), e);
           internalError(sender);
        }
    }

    private void internalError(final MemBot sender) {
        sender.leaveAwaitQueue(this);
       try {
        sender.sendMessage(
               new TextMessage(
                   this.message.getChatId(),
                   "Sorry, i can't process your media"
                   )
               );
    } catch (final TelegramApiException e) {
        log.error(e.getApiResponse(), e);
    }
        
    }

    private String uploadResponse() throws Exception {
        String mediaName = "media";
        Picture media = null;

        if (message.getSticker() != null) {
            mediaName = "sticker";
            media = new TlgSticker(this.message);
        }
        if (message.getPhoto() != null) {
            mediaName = "photo";
            media = new TlgPhoto(this.message);
        }

        if (media != null) {
            // if is stored already
            
            final Optional<Tags> tags =
                new RepTags(DsHikari.ds()).findTagsByFileId(media);
            if (tags.isPresent()) {

                final StringBuilder resp = new StringBuilder("This ")
                    .append(mediaName).append(" is already stored with tags:\n")
                    .append("<b>");
                resp.append(tags.get().asStringRow());
                resp.append("</b>\n").append("Please input new tags.");
                return resp.toString();
            } else {
                return "Please, input tags for this " + mediaName;
            }
        }
        return "Sorry. Such media is not supported yet.";
    }

    @Override
    public void resume(final MemBot sender, final Update update) {

        // if tags are correct - save picture
        if (update.getMessage().hasText()) {
            final Tags tags = new Tags(update.getMessage().getText());
            if (!tags.isEmpty()) {
               
                saveOrUpdatePicture(this.message, tags);
                try {
                    sender.sendMessage(
                        new TextMessage(update.getMessage().getChatId(),
                            "Picture is saved successfully"));
                } catch (final TelegramApiException e) {
                    log.error("Can't send message", e);
                }
            } else {
                // else add itself to queue and wait for correct tags
                sender.answerAwait(message.getChatId(), this);
            }
        }

    }

    private void saveOrUpdatePicture(final Message message, final Tags tags) {

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

            final TlgSticker photo = new TlgSticker(message.getFrom().getId(),
                sticker.getFileId());
            log.debug("Try save tags:{}", tags);
            saveUpdate(new MediaTags(photo, tags));
        } catch (final Exception e) {
            log.error("Can't save sticker", e);
        }

    }

    private void savePhoto(final List<PhotoSize> photos, final Tags tags) {
        try {
            final TlgPhoto photo = new TlgPhoto(this.message);
            log.debug("Try save tags:{}", tags);
            saveUpdate(new MediaTags(photo, tags));
        } catch (final Exception e) {
            log.error("Can't save photo", e);
        }

    }

    private void saveUpdate(final MediaTags tags) throws SQLException {
        final RepTags repTags = new RepTags(DsHikari.ds());
        if (repTags.isSaved(tags.getPicture())) {
            repTags.update(tags);
        } else {
            repTags.save(tags);
        }
       
        
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UploadCommand [message=");
        builder.append(message);
        builder.append("]");
        return builder.toString();
    }

}
