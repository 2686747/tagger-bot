/**
 * 
 */
package org.tlg.bot.mem.web.rest;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.App;
import org.tlg.bot.mem.AppConfig;
import org.tlg.bot.mem.MemBot;
import org.tlg.bot.mem.services.MediaLoader;
import org.tlg.bot.mem.services.MediaLoader.Callback;

/**
 * Loads media
 * 
 * @author Maksim Vakhnik
 *
 */
@Path("/")
public class RsMedia {

    private static final Logger log = LoggerFactory
        .getLogger(RsMedia.class.getName());

    public static final String URL_MEDIAS = "/media/";
    
    /**
     * Loads image from telegram
     * @param id
     * @param asyncResponse
     * @return
     */
    @GET
    @Path(RsMedia.URL_MEDIAS + "{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    //TODO cache
    public Response media(@PathParam("id") final String id,
        @Suspended final AsyncResponse asyncResponse) {
        log.debug("media:{}", id);
        new MediaLoader(
            new MemBot(
                new AppConfig(App.KEY_NAME).value(),
                new AppConfig(App.KEY_TOKEN).value())).asyncLoad(id,
                    new Callback() {

                        @Override
                        public void onSuccess(final InputStream is) {
                            final CacheControl cc = new CacheControl();
                            cc.setMaxAge((int)TimeUnit.HOURS.toSeconds(1));
                            cc.setPrivate(true);
                            
                            asyncResponse
                                .resume(Response.ok().entity(is).cacheControl(cc).build());
                        }

                        @Override
                        public void onError(final String reason) {
                            asyncResponse.resume(
                                Response.status(Status.NOT_FOUND).build());
                        }

                    });
        return Response.status(Status.NOT_FOUND).build();
    }
}
