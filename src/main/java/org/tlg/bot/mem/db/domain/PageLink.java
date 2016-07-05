/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import java.util.Base64;
import org.tlg.bot.mem.exceptions.WrongUrlException;

/**
 * Tag edit page link. Decode/encode url to/from userId, created.
 * @author "Maksim Vakhnik"
 *
 */
public class PageLink {
 
    public static final Object TABLE = "PageLinks";

    private static final String DELIM = "%";
    
    private final long userId;
    private final long created;

    public PageLink(final long userId, final long created) {
        this.userId = userId;
        this.created = created;
    }

    public PageLink(final String url) throws WrongUrlException {
        this(PageLink.id(url), PageLink.created(url));
    }

    public long getCreated() {
        return this.created;
    }

    private static String decode(final String url) {
        return new String(Base64.getUrlDecoder().decode(url));
    }

    public long getUserId() {
        return this.userId;
    }
    
    private static long created(final String url) throws WrongUrlException {
        final String decoded = PageLink.decode(url);
        try {
            return Long.valueOf(decoded.substring(
                decoded.indexOf(PageLink.DELIM) + 1, decoded.length()
                )
            );
        } catch (final Exception e) {
            throw new WrongUrlException(url);
        }
        
    }

    private static long id(final String url) throws WrongUrlException {
        final String decoded = PageLink.decode(url);
        try {
            return Long.valueOf(
                decoded.substring(0, decoded.indexOf(PageLink.DELIM))
                );
        } catch (final Exception e) {
            throw new WrongUrlException(url);
        }
    }

    public String getUrl() {
        return PageLink.url(this.getUserId(), this.getCreated());
    }
    /**
     * Creates short url from pageLink
     * @param pageLink
     * @return
     */
    private static String url(final long userId, final long created) {
        return Base64.getUrlEncoder().encodeToString(
            (
                new StringBuilder()
                    .append(String.valueOf(userId))
                    .append(DELIM)
                    .append(String.valueOf(created))
                    .toString()      
            ).getBytes()
        );
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
