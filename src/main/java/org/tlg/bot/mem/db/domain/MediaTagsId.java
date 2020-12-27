/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import org.tlg.bot.mem.util.EncodedTagsId;

/**
 * TagsId is used for connection between user and media
 * @author Maksim Vakhnik
 *
 */
public class MediaTagsId {
    private final EncodedTagsId encoded;

    public MediaTagsId(final EncodedTagsId id) {
        this.encoded = id;
    }

    public MediaTagsId(final int userId, final String fileId) {
        this(new EncodedTagsId(userId, fileId));
    }

    public String getId() {
        return encoded.id();
    }

    public Integer getUserId() {
        return this.encoded.userId();
    }

    public String getMediaId() {
        return this.encoded.mediaId();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("MediaTagsId [encoded=");
        builder.append(encoded);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((encoded == null) ? 0 : encoded.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof MediaTagsId))
            return false;
        final MediaTagsId other = (MediaTagsId) obj;
        if (encoded == null) {
            if (other.encoded != null)
                return false;
        } else if (!encoded.equals(other.encoded))
            return false;
        return true;
    }

}
