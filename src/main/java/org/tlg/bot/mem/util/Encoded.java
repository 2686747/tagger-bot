/**
 * 
 */
package org.tlg.bot.mem.util;

import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.exceptions.EncodedException;

/**
 * Encodes/decodes pair of values only for simple hiding real values.
 * 
 * @author Maksim Vakhnik
 *
 */
public class Encoded {

    private static final Logger log = LoggerFactory
        .getLogger(Encoded.class.getName());

    private static final String BOUNDARY = "\u1D560\u26900\u1D300";
    
    private final String val1;
    private final String val2;

    public Encoded(final Object obj1, final Object obj2) {
        this.val1 = Encoded.str(obj1);
        this.val2 = Encoded.str(obj2);
    }

    /**
     * 
     * @param obj
     * @return "" for null or non-null
     */
    private static String str(final Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    public Encoded(final String encoded) throws EncodedException {
        this(Encoded.decode(encoded));
    }

    private Encoded(final String[] decode) {
        this(decode[0], decode[1]);
    }

    private static String[] decode(final String encoded)
        throws EncodedException {
        try {
            final String decoded = new String(
                Base64.getUrlDecoder().decode(encoded));
            final String[] result = new String[2];
            result[0] = decoded.substring(0, decoded.indexOf(Encoded.BOUNDARY));
            result[1] = decoded.substring(
                decoded.indexOf(Encoded.BOUNDARY) + Encoded.BOUNDARY.length());
            return result;
        } catch (final Exception e) {
            log.warn("Wrong encoded string:[{}]", encoded);
            throw new EncodedException(encoded);
        }
    }

    public String encoded() {
        return Base64.getUrlEncoder().encodeToString(
            (new StringBuilder()
                .append(this.val1)
                .append(Encoded.BOUNDARY)
                .append(this.val2)
                .toString()).getBytes());
    }

    public String first() {
        return this.val1;
    }

    public String second() {
        return this.val2;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Encoded [val1=");
        builder.append(val1);
        builder.append(", val2=");
        builder.append(val2);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((val1 == null) ? 0 : val1.hashCode());
        result = prime * result + ((val2 == null) ? 0 : val2.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Encoded))
            return false;
        final Encoded other = (Encoded) obj;
        if (val1 == null) {
            if (other.val1 != null)
                return false;
        } else if (!val1.equals(other.val1))
            return false;
        if (val2 == null) {
            if (other.val2 != null)
                return false;
        } else if (!val2.equals(other.val2))
            return false;
        return true;
    }

}
