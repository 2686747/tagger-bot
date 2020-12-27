/**
 * 
 */
package org.tlg.bot.mem.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.exceptions.EncodedException;

/**
 * @author Maksim Vakhnik
 *
 */
public class EncodedTagsId {
    private static final Logger log = LoggerFactory
        .getLogger(EncodedTagsId.class.getName());

    private final Encoded encoded;

    public EncodedTagsId(final int userId, final String fileId) {
        this.encoded = new Encoded(userId, fileId);
    }

    public EncodedTagsId(final String encodedString) throws EncodedException {
        this(new Encoded(encodedString));
    }

    private EncodedTagsId(final Encoded encoded) throws EncodedException {
        this(EncodedTagsId.id(encoded), EncodedTagsId.fileId(encoded));
    }

    private static String fileId(final Encoded encoded)
        throws EncodedException {
        final String fileId = encoded.second();
        if (fileId == null || fileId.isEmpty()) {
            log.warn("wrong encoded value:{}", encoded);
            throw new EncodedException(encoded.encoded());
        }
        return fileId;
    }

    private static int id(final Encoded encoded) throws EncodedException {
        try {
            return Integer.valueOf(encoded.first());
        } catch (final Exception e) {
            log.warn("wrong encoded value:{}", encoded);
            throw new EncodedException(encoded.encoded());
        }
    }

    public String id() {
        return encoded.encoded();
    }

    public Integer userId() {
        return Integer.valueOf(this.encoded.first());
    }

    public String mediaId() {
        return this.encoded.second();
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
        if (!(obj instanceof EncodedTagsId))
            return false;
        final EncodedTagsId other = (EncodedTagsId) obj;
        if (encoded == null) {
            if (other.encoded != null)
                return false;
        } else if (!encoded.equals(other.encoded))
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EncodedTagsId [encoded=");
        builder.append(encoded);
        builder.append("]");
        return builder.toString();
    }

}
