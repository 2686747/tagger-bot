/**
 * 
 */
package org.tlg.bot.mem.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.commands.Command;
import org.tlg.bot.mem.commands.HelpCommand;
import org.tlg.bot.mem.commands.UploadCommand;
import org.tlg.bot.mem.commands.VersionCommand;

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
    private final MemBot bot;
    public CommandProcessor(final MemBot bot, final Update update) {
        this.update = update;
        this.bot = bot;
    }

    public Command command() {
        final Message message = this.update.getMessage();

        if (message.hasText()) {
            // if help
            if (message.getText().startsWith("/help")) {
                return new HelpCommand(this.bot, message.getChatId());
            }
            if (message.getText().startsWith("/ver")) {
                return new VersionCommand(this.bot, message.getChatId());
            }
            //todo reaction to text
            return new HelpCommand(this.bot, message.getChatId());
        }
        // if this is photo or sticker
        if (message.getSticker() != null || !message.getPhoto().isEmpty()) {
            return new UploadCommand(this.bot, message);
        }

        // if message was not processed
        return new HelpCommand(this.bot, this.update.getMessage().getChatId());

    }

}
