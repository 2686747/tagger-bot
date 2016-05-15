/**
 * 
 */
package org.tlg.bot.mem.db.domain;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TlgSticker extends BasePicture {

    public TlgSticker(final Integer userId, final String fileId) {
        super(userId, fileId, TlgMediaType.STICKER);
    }

}
