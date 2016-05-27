/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import org.telegram.telegrambots.api.objects.Message;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TlgSticker extends BasePicture {

    public TlgSticker(final Integer userId, final String fileId) {
        super(userId, fileId, TlgMediaType.STICKER);
    }

    public TlgSticker(final Message message) {
        this(message.getFrom().getId(), message.getSticker().getFileId());
    }

}
