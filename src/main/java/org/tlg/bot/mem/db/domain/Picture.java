/**
 * 
 */
package org.tlg.bot.mem.db.domain;

/**
 * @author "Maksim Vakhnik"
 *
 */
public interface Picture {
    public static final String TABLE = "Photo";
    
    Integer getUserId();

    String getFileId();
    
    TlgMediaType getType();
}
