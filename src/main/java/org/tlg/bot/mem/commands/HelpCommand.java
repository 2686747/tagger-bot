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
    
   
    private final String chatId;
    
    public HelpCommand(final String chatId) {
        this.chatId = chatId;
    }

    public HelpCommand(final Long chatId) {
        this(String.valueOf(chatId));
    }

    @Override
    public void execute(final MemBot sender) {
        try {
            sender.sendMessage(new HelpMessage(this.chatId));
        } catch (final TelegramApiException e) {
            log.error("Can't send help messagemessage", e);
        }
        
    }


    
}
