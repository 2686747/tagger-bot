/**
 * 
 */
package org.tlg.bot.mem.web.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Bot will return url to /edit tag. This url will be processed by this 
 * conroller and user will receive html page where could edit his tags.
 * @author Maksim Vakhnik
 *
 */
@Path("/")
public class RsTagsEditor {
    /**
     * Checks url, return page or 404
     * @return correct html page for editing 
     */
    @GET
    @Path("{page}")
    @Produces(MediaType.TEXT_HTML)
    public String getPage(@PathParam("page") final String page) {
        
        return String.format("<div>Hello, from %s</div>", page);
    }
}
