/**
 * 
 */
package org.tlg.bot.mem.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.commands.Command;
import org.tlg.bot.mem.commands.HelpCommand;
import org.tlg.bot.mem.commands.PhotoCommands;
import org.tlg.bot.mem.commands.UploadCommand;

/**
 * Process udpate object and return needed command.
 * 
 * @author "Maksim Vakhnik"
 *
 */
public class CommandProcessor {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory
        .getLogger(CommandProcessor.class.getName());

    private final Update update;

    public CommandProcessor(final Update update) {
        this.update = update;
    }

    public Command command() {
        final Message message = this.update.getMessage();

        if (message.hasText()) {
            // if help
            if (message.getText().startsWith("/help")) {
                return new HelpCommand(message.getChatId());
            }
            //if tags
            return new PhotoCommands();
        }
        // if this is photo or sticker
        if (message.getSticker() != null || !message.getPhoto().isEmpty()) {
            return new UploadCommand(message);
        }

        // if message was not processed
        return new HelpCommand(this.update.getMessage().getChatId());
        // if just photo return new
        // log.debug("update:{}", this.update);
        // final Message message = this.update.getMessage();
        // final List<PhotoSize> photo = message.getPhoto();
        // System.out.println(photo.get(0).getFilePath());
        // return new UploadCommand(message);
        // if (message.hasText()) {
        // final String text = message.getText();
        // if (text.startsWith("/help")) {
        // return new HelpCommand(this.udpate);
        // }
        //
        //
        //
        // }
        //
        // return new HelpCommand();

    }

}
