/**
 * 
 */
package org.tlg.bot.mem.web.dto;

/**
 * @author Maksim Vakhnik
 *
 */
public class TagDto {

    private final String tag;
    
    
    @SuppressWarnings("unused")
    //Jersey
    private TagDto() {
        this(null);
    }

    public TagDto(final String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("TagDto [tag=");
        builder.append(tag);
        builder.append("]");
        return builder.toString();
    }
}
