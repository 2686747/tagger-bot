/**
 * 
 */
package org.tlg.bot.mem.web.dto;

import java.util.Collection;

/**
 * @author Maksim Vakhnik
 *
 */
public class TagsDto {

    private final String url;
    private final String id;
    private final Collection<TagDto> tags;
    
    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public Collection<TagDto> getTags() {
        return tags;
    }

    public TagsDto(final String url, final String id, final Collection<TagDto> tags) {
        this.url = url;
        this.id = id;
        this.tags = tags;
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
