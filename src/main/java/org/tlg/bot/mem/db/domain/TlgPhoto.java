/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import org.telegram.telegrambots.api.objects.PhotoSize;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TlgPhoto extends BasePicture {

    public TlgPhoto(final PhotoSize photoSize, final Integer userId) {
        super(userId, TlgPhoto.photoId(photoSize), TlgMediaType.PHOTO);
    }

    private static String photoId(final PhotoSize photoSize) {
        return photoSize.getFileId();
    }

    
}
