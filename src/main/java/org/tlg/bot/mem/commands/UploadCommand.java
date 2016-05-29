/**
 * 
 */
package org.tlg.bot.mem.commands;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgPhoto;
import org.tlg.bot.mem.db.domain.TlgSticker;
import org.tlg.bot.mem.db.ds.DsHikari;
import org.tlg.bot.mem.exceptions.MediaTypeException;
import org.tlg.bot.mem.msg.HtmlMessage;
import org.tlg.bot.mem.msg.TextMessage;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class UploadCommand implements Command {

    public enum Commands {
        DELETE("/delete"), CANCEL("/cancel");
        private final String value;

        private Commands(final String value) {
            this.value = value;
        }

        public static final Optional<Commands> commandOf(final String command) {
            for (final Commands cmd : Commands.values()) {
                if (cmd.value.equals(command)) {
                    return Optional.of(cmd);
                }
            }
            return Optional.empty();
        }

        public final String value() {
            return value;
        }
    }

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
            sender.sendMessage(uploadResponse());
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
            sender.sendMessage(new TextMessage(this.message.getChatId(),
                "Sorry, i can't process your media"));
        } catch (final TelegramApiException e) {
            log.error(e.getApiResponse(), e);
        }

    }

    private void mediaTypeError(final MemBot sender) {
        sender.leaveAwaitQueue(this);
        try {
            sender.sendMessage(new TextMessage(this.message.getChatId(),
                "Sorry, this type of media is not supported yet"));
        } catch (final TelegramApiException e) {
            log.error(e.getApiResponse(), e);
        }
    }

    private void cancelCurrent(final MemBot sender) {
        sender.leaveAwaitQueue(this);
        try {
            sender.sendMessage(new TextMessage(this.message.getChatId(),
                "Canceled"));
        } catch (final TelegramApiException e) {
            log.error(e.getApiResponse(), e);
        }
        
    }
    
    private HtmlMessage uploadResponse() throws Exception {
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
        HtmlMessage response;
        final ReplyKeyboardMarkup kbd = new ReplyKeyboardMarkup();
        kbd.setResizeKeyboard(true);
        kbd.setOneTimeKeyboad(true);
        final KeyboardRow kRow = new KeyboardRow();
        if (media != null) {
            // if is stored already

            final Optional<Tags> tags = new RepTags(DsHikari.ds())
                .findTagsByFileId(media);
            if (tags.isPresent()) {

                final StringBuilder resp = new StringBuilder("This ")
                    .append(mediaName).append(" is already stored with tags:\n")
                    .append("<b>");
                resp.append(tags.get().asStringRow());
                resp.append("</b>\n").append("Please input new tags.");
                response = new HtmlMessage(message.getChatId(),
                    resp.toString());
                kRow.add(Commands.DELETE.value());
                kRow.add(Commands.CANCEL.value());
            } else {
                response = new HtmlMessage(message.getChatId(),
                    "Please, input tags for this " + mediaName);
                kRow.add(Commands.CANCEL.value());

            }
            kbd.setKeyboard(Arrays.asList(kRow));
            response.setReplayMarkup(kbd);
            return response;
        }
        // just simple message
        return new HtmlMessage(message.getChatId(),
            "Sorry. Such media is not supported yet.");
    }

    @Override
    public void resume(final MemBot sender, final Update update) {
        try {
            processAnswer(sender, update);
        } catch (final MediaTypeException e) {
            log.debug("Wrong type of media", e);
            mediaTypeError(sender);
        } catch (final TelegramApiException e) {
            log.error(e.getApiResponse(), e);
            internalError(sender);
        } catch (final SQLException e) {
            log.error(e.getMessage(), e);
            internalError(sender);
            e.printStackTrace();
        }

    }

    private void processAnswer(final MemBot sender, final Update update)
        throws TelegramApiException, SQLException, MediaTypeException {
        // if tags are correct - save picture
        if (update.getMessage().hasText()) {
            final Optional<Commands> cmd = Commands
                .commandOf(update.getMessage().getText());

            if (!cmd.isPresent()) {
                final Tags tags = new Tags(update.getMessage().getText());
                if (!tags.isEmpty()) {

                    saveOrUpdatePicture(this.message, tags);
                    sender.sendMessage(
                        new TextMessage(update.getMessage().getChatId(),
                            "Picture is saved successfully"));
                } else {
                    // else add itself to queue and wait for correct tags
                    sender.answerAwait(message.getChatId(), this);
                }
            } else {
                executeCommand(sender, cmd);
            }

        }
    }

    private void executeCommand(
        final MemBot sender, final Optional<Commands> cmd
        ) {
        switch (cmd.get()) {
        case DELETE:
            deleteMedia();
            return;
        case CANCEL:
            cancelCurrent(sender);
            return;
        }
    }

   

    private void deleteMedia() {
//        try {
//            final Picture media = getMedia(this.message);
//        } catch (MediaTypeException e) {
//            e.printStackTrace();
//        }
        
    }

    private void saveOrUpdatePicture(final Message message, final Tags tags)
        throws SQLException, MediaTypeException {
        saveUpdate(new MediaTags(getMedia(message), tags));

    }

    private static Picture getMedia(final Message message)
        throws MediaTypeException {
        if (message.getPhoto() != null) {
            return new TlgPhoto(message);
        }
        if (message.getSticker() != null) {
            return new TlgSticker(message);
        }
        throw new MediaTypeException(message);
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
