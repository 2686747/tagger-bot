package org.tlg.bot.mem.msg;

/**
 * Simple text answer to chatId
 * @author "Maksim Vakhnik"
 *
 */
public class HtmlMessage extends TextMessage {
    
    public HtmlMessage(final Long chatId, final String text) {
        super(chatId, text);
        super.enableHtml(true);
    }


}
