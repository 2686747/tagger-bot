/**
 * 
 */
package org.tlg.bot.mem.web.dto;

import org.tlg.bot.mem.util.EncodedTagsId;

/**
 * TagsId is used for connection between user and media
 * @author Maksim Vakhnik
 *
 */
public class TagsId {
    private final EncodedTagsId encoded;

    private TagsId(final EncodedTagsId id) {
        this.encoded = id;
    }

    public TagsId(final int userId, final String fileId) {
        this(new EncodedTagsId(userId, fileId));
    }

    public String getId() {
        return encoded.id();
    }

  

}
