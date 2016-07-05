/**
 * 
 */
package org.tlg.bot.mem.web;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.tlg.bot.mem.web.rest.RsTagsEditor;
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
        final URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        final ResourceConfig config = ResourceConfig.forApplication(new Application() {
            
            @Override
            public Set<Class<?>> getClasses() {
                return new HashSet<>(Arrays.asList(RsTagsEditor.class));
            }
            
            
        });
        
        config.register(new AbstractBinder() {
            
            @Override
            protected void configure() {
                bindFactory(DsFactory.class).to(Ds.class);
            }
        });
        final HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        
	}
	
 

	
    
}
