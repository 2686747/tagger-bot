/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import org.telegram.telegrambots.api.objects.Message;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TlgDocumentVideo extends BasePicture {

    public TlgDocumentVideo(final Message message) {
        super(
            message.getFrom().getId(),
            message.getDocument().getFileId(),
            TlgMediaType.DOCUMENT
        );
    }


}
