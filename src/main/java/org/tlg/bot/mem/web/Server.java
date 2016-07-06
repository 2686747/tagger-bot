/**
 * 
 */
package org.tlg.bot.mem.web;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.vmk.db.ds.Ds;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class Server {
    public static void main(final String[] args) throws Exception {
        new Server().start();
    }

    public void start() throws Exception {
        final URI baseUri = UriBuilder.fromUri("http://localhost/api")
            .port(9998).build();
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(
            baseUri,
            //injection
            new JerseyConfig().register(
                new AbstractBinder() {

                @Override
                protected void configure() {
                    bindFactory(DsFactory.class).to(Ds.class);
                }

            })
        );
        httpServer.getServerConfiguration()
            .addHttpHandler(new StaticHttpHandler("src/main/webapp"), "/");
    }

}
