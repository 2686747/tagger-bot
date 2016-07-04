/**
 * 
 */
package org.tlg.bot.mem.db.domain;

/**
 * Tag edit page
 * @author "Maksim Vakhnik"
 *
 */
public class PageLink {
 
    public static final Object TABLE = "PageLinks";
    
    private final long userId;
    private final long created;

    public PageLink(final long userId, final long created) {
        this.userId = userId;
        this.created = created;
    }

    public long getCreated() {
        return created;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PageLink [userId=");
        builder.append(userId);
        builder.append(", created=");
        builder.append(created);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (created ^ (created >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PageLink))
            return false;
        final PageLink other = (PageLink) obj;
        if (created != other.created)
            return false;
        if (userId != other.userId)
            return false;
        return true;
    }


}
