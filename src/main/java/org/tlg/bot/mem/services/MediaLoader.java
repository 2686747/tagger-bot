/**
 * 
 */
package org.tlg.bot.mem.services;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.updateshandlers.SentCallback;
import org.tlg.bot.mem.MemBot;

/**
 * Loads medias from telegram server
 * 
 * @author Maksim Vakhnik
 *
 */
public class MediaLoader {

    public interface Callback {

        void onError(String reason);

        void onSuccess(InputStream stream);
    }

    private static final Logger log = LoggerFactory
        .getLogger(MediaLoader.class.getName());

    private final MemBot bot;

    public MediaLoader(final MemBot bot) {
        this.bot = bot;
    }

    public void asyncLoad(final String mediaId, final Callback callback) {
        try {
            bot.getFileAsync(
                new GetFile().setFileId(mediaId),
                new SentCallback<File>() {

                    @Override
                    public void onResult(final BotApiMethod<File> method,
                        final JSONObject jsonObject) {
                        final Optional<InputStream> is = MediaLoader.this
                            .loadFile(jsonObject);
                        if (is.isPresent()) {
                            callback.onSuccess(is.get());
                        } else {
                            callback.onError("Can't load file");
                        }

                    }

                    @Override
                    public void onException(final BotApiMethod<File> method,
                        final Exception exception) {
                        callback.onError(
                            "Can't load file: " + exception.getMessage());

                    }

                    @Override
                    public void onError(final BotApiMethod<File> method,
                        final JSONObject jsonObject) {
                        callback.onError(
                            "Can't load file: " + jsonObject);

                    }
                });
        } catch (final TelegramApiException e) {
            log.error(e.getApiResponse(), e);
        }
    }

    protected Optional<InputStream> loadFile(final JSONObject jsonObject) {

        final JSONObject result = jsonObject.getJSONObject("result");
        if (result != null) {
            final String filePath = result.getString("file_path");
            final String uri = new StringBuilder(
                "https://api.telegram.org/file/bot")
                    .append(this.bot.getBotToken())
                    .append("/")
                    .append(filePath).toString();
            try {
//                return Optional.ofNullable(
//                    ClientBuilder.newClient().target(uri)
//                        .request()
//                        .accept(MediaType.APPLICATION_OCTET_STREAM)
//                        .get().readEntity(java.io.File.class)
//                );
                
                return Optional.ofNullable(new URL(uri).openStream());
            } catch (final Exception e) {
                log.error("Can't load file", e);
            }
        }
        return Optional.empty();
    }
}
