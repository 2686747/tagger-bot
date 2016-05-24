/**
 * 
 */
package org.tlg.bot.mem.db.domain;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class BasePicture implements Picture{
    
    private final Integer userId;
    private final String photoId;
    private final TlgMediaType type;
    

    public BasePicture(
        final Integer userId, final String fileId, final TlgMediaType type
        ) {
        this.userId = userId;
        this.photoId = fileId;
        this.type = type;
    }
    
    @Override
    public final Integer getUserId() {
        return userId;
    }
    @Override
    public final String getFileId() {
        return photoId;
    }

    @Override
    public final TlgMediaType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((photoId == null) ? 0 : photoId.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BasePicture))
            return false;
        final BasePicture other = (BasePicture) obj;
        if (photoId == null) {
            if (other.photoId != null)
                return false;
        } else if (!photoId.equals(other.photoId))
            return false;
        if (type != other.type)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BasePicture [photoId=");
        builder.append(photoId);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", type=");
        builder.append(type);
        builder.append("]");
        return builder.toString();
    }



    
}
