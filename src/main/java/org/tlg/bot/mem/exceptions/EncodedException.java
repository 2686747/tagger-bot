/**
 * 
 */
package org.tlg.bot.mem.exceptions;

import java.io.IOException;

/**
 * Occurs when encoded value is incorrect.
 * @author Maksim Vakhnik
 *
 */
public class EncodedException extends IOException {

    /**
     * 
     */
    private static final long serialVersionUID = 201607071457L;

    public EncodedException(final String message) {
        super(message);
    }

    
}
