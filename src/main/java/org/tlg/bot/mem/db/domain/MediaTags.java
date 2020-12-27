/**
 * 
 */
package org.tlg.bot.mem.db.domain;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class MediaTags {

    public static final Object TABLE = "Tags";

    private final Picture picture;
    private final Tags tags;
    //transient
    private final MediaTagsId id;
    public MediaTags(
        final Picture photo, final Tags tags
        ) {
//        if (tags.isEmpty()) {
//            throw new IllegalArgumentException("Tags can't be empty");
//        }
        this.picture = photo;
        this.tags = tags;
        this.id = MediaTags.id(photo);
    }

    private static MediaTagsId id(final Picture photo) {
        return new MediaTagsId(photo.getUserId(), photo.getFileId());
    }

    public final Picture getPicture() {
        return picture;
    }
    public final Tags getTags() {
        return tags;
    }

    public MediaTagsId getId() {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((picture == null) ? 0 : picture.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof MediaTags))
            return false;
        final MediaTags other = (MediaTags) obj;
        if (picture == null) {
            if (other.picture != null)
                return false;
        } else if (!picture.equals(other.picture))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PhotoTags [photo=");
        builder.append(picture);
        builder.append(", tags=");
        builder.append(tags);
        builder.append("]");
        return builder.toString();
    }
}
