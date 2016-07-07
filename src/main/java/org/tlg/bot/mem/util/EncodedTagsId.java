/**
 * 
 */
package org.tlg.bot.mem.util;

/**
 * @author Maksim Vakhnik
 *
 */
public class EncodedTagsId {

    private final Encoded encoded;
    
    public EncodedTagsId(final int userId, final String fileId) {
        this.encoded = new Encoded(userId, fileId);
    }
    
    public String id() {
        return encoded.encoded();
    }
    

}
