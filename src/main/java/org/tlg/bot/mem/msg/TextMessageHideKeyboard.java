/**
 * 
 */
package org.tlg.bot.mem.msg;

import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TextMessageHideKeyboard extends TextMessage{

    public TextMessageHideKeyboard(final Long chatId, final String text) {
        super(chatId, text);
        this.setReplayMarkup(new ReplyKeyboardHide().setHideKeyboard(true));
    }

}
