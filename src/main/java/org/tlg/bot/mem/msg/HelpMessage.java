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
        + "and send it to your companion in a chat.\n"
        + "More details you can see here:\n"
        + "http://2686747.github.io/tagger-bot/" ;

    public HelpMessage(final Long chatId) {
        super(chatId, HelpMessage.MSG);
    }


}
