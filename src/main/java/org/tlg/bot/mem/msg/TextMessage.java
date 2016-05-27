package org.tlg.bot.mem.msg;

import org.telegram.telegrambots.api.methods.send.SendMessage;

/**
 * Simple text answer to chatId
 * @author "Maksim Vakhnik"
 *
 */
public class TextMessage extends SendMessage {

    public TextMessage(final Long chatId, final String text) {
        super.setChatId(String.valueOf(chatId));
        super.setText(text);
    }


}
