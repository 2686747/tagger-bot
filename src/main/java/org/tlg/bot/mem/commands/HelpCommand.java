package org.tlg.bot.mem.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.msg.HelpMessage;

/**
 * Help response.
 * @author "Maksim Vakhnik"
 *
 */
public class HelpCommand extends ExecuteCommand {
    private static final Logger log = LoggerFactory
        .getLogger(HelpCommand.class.getName());
   
    private final Long chatId;

    public HelpCommand(final MemBot bot, final Long chatId) {
        super(bot);
        this.chatId = chatId;
    }

    @Override
    public void execute() {
        try {
            getBot().sendMessage(new HelpMessage(this.chatId));
        } catch (final TelegramApiException e) {
            log.error("Can't send help messagemessage", e);
        }
        
    }


    
}
