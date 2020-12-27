package org.tlg.bot.mem.web.rest;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.MediaTagsId;
import org.tlg.bot.mem.db.domain.PageLink;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.exceptions.EncodedException;
import org.tlg.bot.mem.util.EncodedPageLink;
import org.tlg.bot.mem.util.EncodedTagsId;
import org.tlg.bot.mem.web.dto.TagsDto;
import org.vmk.db.ds.Ds;

@Path("/")
public class RsTagsEditor {

    public static final String URL_TAGS_ALL = "tags/all";
    public static final String URL_TAGS_UPDATE = "tags/update";
    private final Ds ds;

    @Inject
    public RsTagsEditor(final Ds ds) {
        this.ds = ds;
    }

    private static final Logger log = LoggerFactory
        .getLogger(RsTagsEditor.class.getName());

    /**
     * Updates medias. If medias contains empty tags - it will be removed.
     * 
     * @param token
     * @param tags
     * @return 200 if ok, 404 if not
     */
    @POST
    @Path(URL_TAGS_UPDATE)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTags(@HeaderParam("token") final String token,
        final Collection<TagsDto> tags) {
        log.debug("data:{}", tags);
        // update PageLink time creation
        final Optional<Integer> userId = this.userId(token);
        if (userId.isPresent()) {
            final RepTags repTags = new RepTags(this.getDs());
            // update medias
            for (final TagsDto tagsDto : tags) {

                try {
                    final Optional<MediaTags> found = repTags
                        .findById(new MediaTagsId(
                            new EncodedTagsId(tagsDto.getId())));
                    if (found.isPresent()) {
                        repTags
                            .update(RsTagsEditor.updated(found.get(), tagsDto));
                    } else {
                        return Response.status(Status.NOT_FOUND).build();
                    }
                } catch (final EncodedException e) {
                    log.warn("Can't update tags:[{}]", tagsDto);
                    return Response.status(Status.BAD_REQUEST).build();
                } catch (final SQLException e) {
                    log.error("Can't update tags:[{}]", tagsDto);
                    return Response.serverError().build();
                }
            }
            return Response.ok().build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    private static MediaTags updated(final MediaTags current,
        final TagsDto tagsDto) {
        return new MediaTags(
            current.getPicture(),
            new Tags(
                tagsDto.getTags().stream()
                    .map(tagDto -> {
                        return tagDto.getTag();
                    }).collect(Collectors.toList())));
    }

    /**
     * @param String
     *            token - bot's generated token for user
     * @return JSON response with tags
     */
    @GET
    @Path(URL_TAGS_ALL)
    @Produces(MediaType.APPLICATION_JSON)
    public Response tags(@HeaderParam("token") final String token) {
        log.debug("token:{}", token);
        final Optional<Integer> userId = this.userId(token);
        if (userId.isPresent()) {
            try {
                return response(
                    new RepTags(this.getDs()).findUserMediaTags(userId.get()));
            } catch (final SQLException e) {
                log.error(e.getMessage(), e);
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    /**
     * @param String
     *            token - bot's generated token for user
     * @return JSON response with tags
     */
    @GET
    @Path("tags/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tagsTest() {
        log.debug("test");
        
        final String tokenTmp = new EncodedPageLink(98470981, 1467912868474L)
            .url();
        log.debug("testtoken:{}", tokenTmp);
        final Optional<Integer> userId = this.userId(tokenTmp);
        if (userId.isPresent()) {
            try {
                return response(
                    new RepTags(this.getDs()).findUserMediaTags(userId.get()));
            } catch (final SQLException e) {
                log.error(e.getMessage(), e);
                return Response.status(Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    private Response response(final Collection<MediaTags> tags) {
        final Collection<TagsDto> map = tags.stream().map(mTag -> {
            return new TagsDto(mTag);
        }).collect(Collectors.toList());
        return Response.ok().entity(map).build();
    }

    /**
     * Converts token to PageLink and returns userId
     * 
     * @param token
     * @return Optinal.emtpy() if can't find
     */
    private Optional<Integer> userId(final String token) {
        try {
            return Optional
                .of(new PageLink(new EncodedPageLink(token)).getUserId());
        } catch (final EncodedException e) {
            log.error("wrong token:{}", token);
        }
        return Optional.empty();
    }

    private Ds getDs() {
        return ds;
    }
}
