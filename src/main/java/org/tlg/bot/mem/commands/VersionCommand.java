package org.tlg.bot.mem.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.msg.VersionMessage;

/**
 * Version response.
 * @author "Maksim Vakhnik"
 *
 */
public class VersionCommand extends ExecuteCommand {
    private static final Logger log = LoggerFactory
        .getLogger(VersionCommand.class.getName());
    
   
    private final Long chatId;

    public VersionCommand(final Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public void execute(final MemBot sender) {
        try {
            sender.sendMessage(new VersionMessage(this.chatId));
        } catch (final TelegramApiException e) {
            log.error("Can't send help messagemessage", e);
        }
        
    }


    
}
