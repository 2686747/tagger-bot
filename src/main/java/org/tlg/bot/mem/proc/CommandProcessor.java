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
            //todo reaction to text
            return new HelpCommand(message.getChatId());
        }
        // if this is photo or sticker
        if (message.getSticker() != null || !message.getPhoto().isEmpty()) {
            return new UploadCommand(message);
        }

        // if message was not processed
        return new HelpCommand(this.update.getMessage().getChatId());

    }

}
