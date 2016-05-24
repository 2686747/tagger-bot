/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import org.telegram.telegrambots.api.objects.Message;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TlgVideo extends BasePicture {

    public TlgVideo(final Message msg) {
        super(
            msg.getFrom().getId(),
            msg.getVideo().getFileId(),
            TlgMediaType.VIDEO
        );
    }

}
