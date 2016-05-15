package org.tlg.bot.mem.msg;

import org.telegram.telegrambots.api.methods.send.SendMessage;

/**
 * Simple text answer to chatId
 * @author "Maksim Vakhnik"
 *
 */
public class TextMessage extends SendMessage {
    
    public TextMessage(final String chatId, final String text) {
        super.setChatId(chatId);
        super.setText(text);
    }

    public TextMessage(final Long chatId, final String text) {
        this(String.valueOf(chatId), text);
    }


}
