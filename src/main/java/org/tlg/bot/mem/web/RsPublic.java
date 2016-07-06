/**
 * 
 */
package org.tlg.bot.mem.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Maksim Vakhnik
 *
 */
@Path("/static")
public class RsPublic {
    
    @GET
    @Path("{path}")
    public String resources(@PathParam("path") final String path) {
        System.out.printf("path:%s", path);
        return path;
    }
}
