/**
 * 
 */
package org.tlg.bot.mem.db.domain;

import org.tlg.bot.mem.util.EncodedPageLink;

/**
 * Tag edit page link. Decode/encode url to/from userId, created.
 * @author "Maksim Vakhnik"
 *
 */
public class PageLink {
 
    public static final Object TABLE = "PageLinks";

    private final EncodedPageLink encoded;
    
    public PageLink(final int userId, final long created) {
        this(new EncodedPageLink(userId, created));
    }

    public PageLink(final EncodedPageLink encoded) {
        this.encoded = encoded;
    }

    public long getCreated() {
        return this.encoded.created();
    }


    public int getUserId() {
        return this.encoded.id();
    }

    public String getUrl() {
        return this.encoded.url();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PageLink [encoded=");
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
        if (!(obj instanceof PageLink))
            return false;
        final PageLink other = (PageLink) obj;
        if (encoded == null) {
            if (other.encoded != null)
                return false;
        } else if (!encoded.equals(other.encoded))
            return false;
        return true;
    }

   

}
