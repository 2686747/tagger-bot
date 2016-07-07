/**
 * 
 */
package org.tlg.bot.mem.web.dto;

import java.util.Collection;
import java.util.stream.Collectors;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.web.rest.RsMedia;

/**
 * @author Maksim Vakhnik
 *
 */
public class TagsDto {

    private final String url;
    private final TagsId id;
    private final Collection<TagDto> tags;

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id.getId();
    }

    public Collection<TagDto> getTags() {
        return tags;
    }

    private TagsDto(final String url, final TagsId id,
        final Collection<TagDto> tags) {
        this.url = url;
        this.id = id;
        this.tags = tags;
    }

    public TagsDto(final MediaTags tags) {
        this(TagsDto.url(tags), TagsDto.id(tags), TagsDto.tags(tags));
    }

    private static TagsId id(final MediaTags tags) {
        return new TagsId(tags.getPicture().getUserId(),
            tags.getPicture().getFileId());
    }

    /**
     * 
     * @param tags
     * @return url for {@code RsMedia.tags()}
     */
    private static String url(final MediaTags tags) {
        return RsMedia.URL_MEDIAS + tags.getPicture().getFileId();
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
