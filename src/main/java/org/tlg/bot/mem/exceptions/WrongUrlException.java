/**
 * 
 */
package org.tlg.bot.mem.exceptions;

import java.io.IOException;

/**
 * @author Maksim Vakhnik
 *
 */
public class WrongUrlException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 201607052346L;

    public WrongUrlException(final String url) {
        super("Url is wrong:" + url);
    }

    
}
