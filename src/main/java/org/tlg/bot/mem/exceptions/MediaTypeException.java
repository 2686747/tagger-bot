/**
 * 
 */
package org.tlg.bot.mem.exceptions;

import java.io.IOException;
import org.telegram.telegrambots.api.objects.Message;

/**
 * 
 * @author "Maksim Vakhnik"
 *
 */
public class MediaTypeException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 201605291536L;
    private final Message msg;
    public MediaTypeException(final Message msg, final String message) {
        super(message);
        this.msg = msg;
    }

    public MediaTypeException(final Message msg) {
        this(msg, String.format("Wrong type of media in message:{%s}", msg));
    }

    public final Message causeTelegramMessage() {
        return msg;
    }
    
    
    
}
