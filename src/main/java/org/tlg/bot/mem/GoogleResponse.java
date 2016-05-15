/**
 * 
 */
package org.tlg.bot.mem;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.AbsSender;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class GoogleResponse {
    private static final Logger log = LoggerFactory
        .getLogger(GoogleResponse.class.getName());
    
    private final AbsSender sender;
    private final Update update;
    
    public GoogleResponse(final AbsSender sender, final Update update) {
        this.sender = sender;
        this.update = update;
    }
    
    /**
     * Send response to chat
     */
    public void send() {
        final SendMessage outMsg = new SendMessage();
        outMsg.setChatId(String.valueOf(update.getMessage().getChatId()));
        
        final String url = "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Ffc05.deviantart.net%2Ffs71%2Ff%2F2013%2F008%2Fb%2F5%2Fyao_ming_true_story_plz_by_mingtruestoryplz-d5qtvjo.jpg";
        final SendPhoto sendSticker = new SendPhoto();
        Path path;
        try {
            path = Paths.get(
                this.getClass().getClassLoader().getResource("sticker.jpg").toURI()
            );
        } catch (final URISyntaxException e2) {
            e2.printStackTrace();
            return;
        }
        if (
            path.toFile().exists() && path.toFile().canRead()
        ) {
            log.debug("file:{}", path.toAbsolutePath().toString());
//            sendSticker.setSticker(path.toAbsolutePath().toString(), "test-sticker");
            sendSticker.setNewPhoto(path.toAbsolutePath().toString(), "sticker.jpg");
            sendSticker.enableNotification();
            sendSticker.setChatId(String.valueOf(update.getMessage().getChatId()));
            try {
//                sender.sendSticker(sendSticker);
                sender.sendPhoto(sendSticker);
            } catch (final TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
    

}
