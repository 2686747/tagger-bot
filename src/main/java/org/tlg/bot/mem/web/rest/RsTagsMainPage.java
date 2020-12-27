/**
 *
 */
package org.tlg.bot.mem.web.rest;

import java.sql.SQLException;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.server.mvc.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.db.RepPageLinks;
import org.tlg.bot.mem.db.domain.PageLink;
import org.tlg.bot.mem.exceptions.EncodedException;
import org.tlg.bot.mem.util.EncodedPageLink;
import org.tlg.bot.mem.db.ds.Ds;
import jersey.repackaged.com.google.common.collect.ImmutableMap;

/**
 * Bot will return url to /edit tag. This url will be processed by this
 * conroller and user will receive html page where could edit his tags.
 *
 * @author Maksim Vakhnik
 *
 */
@Path("/")
public class RsTagsMainPage {

    private static final Logger log = LoggerFactory
        .getLogger(RsTagsMainPage.class.getName());

    private final Ds ds;

    @Inject
    public RsTagsMainPage(final Ds ds) {
        this.ds = ds;
    }

    /**
     * Checks url, return page or 404
     *
     * @return correct html page for editing
     */
    @GET
    @Path("{page}")
    @Produces(MediaType.TEXT_HTML)
    public Response getPage(@NotNull @PathParam("page") final String page) {

        try {

            final PageLink pageLink = new PageLink(new EncodedPageLink(page));
            final Optional<PageLink> pl = new RepPageLinks(this.getDs())
                .findByUserId(pageLink.getUserId());
            if (pl.isPresent()) {
                return Response.ok().entity(
                    new Viewable("/index", ImmutableMap.of("token", page)))
                    .build();
            }
            return Response.status(Status.NOT_FOUND).entity("Not found").build();
        } catch (final EncodedException e) {
            log.debug("wrong request:{}", page);
            return Response.status(Status.NOT_FOUND).entity("Not found")
                .build();
        } catch (final SQLException e) {
            log.error("Error for url:" + page, e);
            throw new InternalError();
        }

    }

    public Ds getDs() {
        return ds;
    }
}
