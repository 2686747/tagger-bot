/**
 * 
 */
package org.tlg.bot.mem.util;

import org.tlg.bot.mem.exceptions.EncodedException;

/**
 * @author Maksim Vakhnik
 *
 */
public class EncodedPageLink{
    private final Encoded encoded;
    public EncodedPageLink(final String encoded) throws EncodedException {
        this(EncodedPageLink.checked(encoded));
    }

    private static Encoded checked(final String encoded)
        throws EncodedException {
        try {
            final Encoded tmp = new Encoded(encoded);
            Integer.valueOf(tmp.first());
            Long.valueOf(tmp.second());
            return tmp;
        } catch (NumberFormatException | EncodedException e) {
            throw new EncodedException(encoded);
        }
    }

    public EncodedPageLink(final int userId, final long created) {
       this(new Encoded(userId, created));
    }

    private EncodedPageLink(final Encoded encoded) {
        this.encoded = encoded;
    }
    
    public long created() {
        return Long.valueOf(this.encoded.second());
    }

    public int id() {
        return Integer.valueOf(this.encoded.first());
    }

    public String url() {
        return this.encoded.encoded();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EncodedPageLink [encoded=");
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
        if (!(obj instanceof EncodedPageLink))
            return false;
        final EncodedPageLink other = (EncodedPageLink) obj;
        if (encoded == null) {
            if (other.encoded != null)
                return false;
        } else if (!encoded.equals(other.encoded))
            return false;
        return true;
    }
}
