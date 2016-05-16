/**
 * 
 */
package org.tlg.bot.mem.commands;

import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedGif;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedPhoto;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedSticker;
import org.tlg.bot.mem.db.domain.Picture;

/**
 * Process picture and return correct type.
 * @author "Maksim Vakhnik"
 *
 */
public class InlineQueryResultCachedTlgMedia {

    
    private final Picture picture;

    public InlineQueryResultCachedTlgMedia(final Picture picture) {
        this.picture = picture;
    }

    public InlineQueryResult result() {
        switch (picture.getType()) {
        case PHOTO:
          return new InlineQueryResultCachedPhoto()
          .setPhotoFileId(picture.getFileId())
          .setId(String.valueOf(System.nanoTime()));
        case STICKER:
            return new InlineQueryResultCachedSticker()
            .setStickerFileId(picture.getFileId())
            .setId(String.valueOf(System.nanoTime()));
            //TODO gif is not implemented yet
        case GIF:
            return new InlineQueryResultCachedGif()
            .setGifFileId(picture.getFileId())
            .setId(String.valueOf(System.nanoTime()));
          //TODO do video processing
//        case VIDEO:
//            return new InlineQueryResultCachedVideo()
//            .setTitle(picture.getFileId())
//            .setId(String.valueOf(System.nanoTime()));
          //TODO  document processing
          //TODO  audio processing
        default:
            //todo add needed types
            return null;
        }
    }
    
    
}
