/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.PhotoSize;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TlgPhoto extends BasePicture {

    public TlgPhoto(final PhotoSize photoSize, final Integer userId) {
        super(userId, TlgPhoto.photoId(photoSize), TlgMediaType.PHOTO);
    }
    //will be created from biggest photo
    public TlgPhoto(final Message message) {
        this(
            message.getPhoto().get(message.getPhoto().size() - 1),
            message.getFrom().getId()
        );
    }
    private static String photoId(final PhotoSize photoSize) {
        return photoSize.getFileId();
    }

    
}
