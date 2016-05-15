/**
 * 
 */
package org.tlg.bot.mem.msg;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class HelpMessage extends TextMessage {

    
    private static final String MSG = "Send to bot your pictures"
        + ", add tags to these media. After this you can "
        + "search these media by these tags "
        + "and send it to your companion in a chat" ;

    public HelpMessage(final String chatId) {
        super(chatId, HelpMessage.MSG);
    }


}
