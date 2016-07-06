package org.tlg.bot.mem.web.rest;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.PageLink;
import org.tlg.bot.mem.exceptions.WrongUrlException;
import org.vmk.db.ds.Ds;

@Path("/")
public class RsTagsEditor {

    private final Ds ds;

    @Inject
    public RsTagsEditor(final Ds ds) {
        this.ds = ds;
    }

    private static final Logger log = LoggerFactory
        .getLogger(RsTagsEditor.class.getName());

    /**
     * 
     * @return JSON response with tags
     */
    @GET
    @Path("tags")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tags(@HeaderParam("token") final String token) {
        log.debug("token:{}", token);
        final Optional<Integer> userId = userId(token);
        if (userId.isPresent()) {
            try {
                return response(
                    new RepTags(this.getDs()).findUserMediaTags(userId.get())
                    );
            } catch (final SQLException e) {
                log.error(e.getMessage(), e);
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    private Response response(final Collection<MediaTags> tags) {
        return Response.ok().entity(tags).build();
    }

    /**
     * Converts token to PageLink and returns userId
     * 
     * @param token
     * @return Optinal.emtpy() if can't find
     */
    private Optional<Integer> userId(final String token) {
        try {
            return Optional.of(new PageLink(token).getUserId());
        } catch (final WrongUrlException e) {
            log.error("wrong token:{}", token);
        }
        return Optional.empty();
    }

    private Ds getDs() {
        return ds;
    }
}
