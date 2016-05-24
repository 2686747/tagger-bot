/**
 * 
 */
package org.tlg.bot.mem.db.domain;

/**
 * Media type for database
 * @author "Maksim Vakhnik"
 *
 */
public enum TlgMediaType {

    PHOTO(1),
    STICKER(2),
    GIF(3),
    VIDEO(4),
    AUDIO(5),
    DOCUMENT(6);
    
    private final Byte type;
    
    private TlgMediaType(final Integer type) {
        this.type = type.byteValue();
    }
     
    public static TlgMediaType get(final Byte type) {
        if (type > TlgMediaType.values().length || type < 0) {
            throw new IllegalArgumentException(
                "There is not such type of media:" + type
                );
        }
        return TlgMediaType.values()[type - 1];
    }
    public Byte asByte() {
        return this.type;
    }
    
}
