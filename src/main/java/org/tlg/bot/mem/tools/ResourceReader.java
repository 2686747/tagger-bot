/**
 * 
 */
package org.tlg.bot.mem.tools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jcabi.manifests.Manifests;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class ResourceReader {
private static final Logger log = LoggerFactory
    .getLogger(ResourceReader.class.getName());

    public static Optional<String> read(final String resource) {
        final InputStream is = ResourceReader.class.getResourceAsStream(resource);
        try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            final byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return Optional.of(new String(result.toByteArray()));
        } catch (final Exception e) {
            log.error("Can't read from resource:{}", resource);
            return Optional.empty();
        }
    }

    public static Optional<String> readManifest(final String entry) {
        try {
            return Optional.of(Manifests.read(entry));
        } catch (final Exception e) {
            return Optional.empty();
        }
    }
}
