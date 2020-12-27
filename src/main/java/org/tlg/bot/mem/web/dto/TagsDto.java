/**
 * 
 */
package org.tlg.bot.mem.web.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.web.Server;
import org.tlg.bot.mem.web.rest.RsMedia;

/**
 * Tags structure only for response.
 * 
 * @author Maksim Vakhnik
 *
 */
public class TagsDto {

    private final String url;
    private final String id;
    private final Collection<TagDto> tags;

    @SuppressWarnings("unused")
    // jersey ctor
    private TagsDto() {
        this("", "", Collections.emptyList());
    }

    private TagsDto(final String url, final String id,
        final Collection<TagDto> tags) {
        this.url = url;
        this.id = id;
        this.tags = tags;
    }

    public TagsDto(final MediaTags tags) {
        this(TagsDto.url(tags), TagsDto.id(tags), TagsDto.tags(tags));
    }

    public String getUrl() {
        return this.url;
    }

    public String getId() {
        return this.id;
    }

    public Collection<TagDto> getTags() {
        return tags;
    }

    private static String id(final MediaTags tags) {
        return tags.getId().getId();
    }

    /**
     * 
     * @param tags
     * @return url for {@code RsMedia.tags()}
     */
    private static String url(final MediaTags tags) {
        return Server.URL_REST + RsMedia.URL_MEDIAS
            + tags.getPicture().getFileId();
    }

    private static Collection<TagDto> tags(final MediaTags tags) {
        return tags.getTags().getTags().stream().map(tag -> {
            return new TagDto(tag);
        }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("TagsDto [url=");
        builder.append(url);
        builder.append(", id=");
        builder.append(id);
        builder.append(", tags=");
        builder.append(tags);
        builder.append("]");
        return builder.toString();
    }

}
