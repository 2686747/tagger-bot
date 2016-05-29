/**
 * 
 */
package org.tlg.bot.mem.commands;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedGif;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedPhoto;
import org.telegram.telegrambots.api.objects.inlinequery.result.chached.InlineQueryResultCachedSticker;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.tlg.bot.mem.db.domain.Picture;

/**
 * Process picture and return correct type.
 * 
 * @author "Maksim Vakhnik"
 *
 */
public class InlineQueryResultCachedTlgMedia {
    private static final Logger log = LoggerFactory
        .getLogger(InlineQueryResultCachedTlgMedia.class.getName());

    private final Picture picture;

    public InlineQueryResultCachedTlgMedia(final Picture picture) {
        this.picture = picture;
    }

    public InlineQueryResult result() {
        switch (picture.getType()) {
        case PHOTO:
            final List<InlineKeyboardButton> buttons = Arrays.asList(
                new InlineKeyboardButton().setText("DELETE")
                    .setCallbackData("delete"),
                new InlineKeyboardButton().setText("CANCEL")
                    .setCallbackData("cancel"));
            final List<List<InlineKeyboardButton>> keyboard = Arrays
                .asList(buttons);

            return new InlineQueryResultCachedPhoto()
                .setPhotoFileId(picture.getFileId())
                .setId(String.valueOf(System.nanoTime()))
                .setReplyMarkup(
                    new InlineKeyboardMarkup().setKeyboard(keyboard)
                );

        case STICKER:
            return new InlineQueryResultCachedSticker()
                .setStickerFileId(picture.getFileId())
                .setId(String.valueOf(System.nanoTime()));
        // TODO gif is not implemented yet
        case GIF:
            return new InlineQueryResultCachedGif()
                .setGifFileId(picture.getFileId())
                .setId(String.valueOf(System.nanoTime()));
        // TODO do video processing

        // TODO document processing
        // TODO audio processing
        default:
            // todo add needed types
            return null;
        }
    }

}
