package org.tlg.bot.mem.db.ds;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Simple readed text.
 * @author "Maksim Vakhnik"
 *
 */
public class ReadedString {

    private final String sql;

    public ReadedString(final InputStream resource) throws IOException {
        this(sql(resource));
    }
    public ReadedString(final String sql) {
        this.sql = sql;
    }
    public ReadedString(final File resource) throws IOException {
        this(new String(Files.readAllBytes(resource.toPath())));
    }
    private static String sql(final InputStream resource) throws IOException {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            final byte[] buffer = new byte[1024];
            int length;
            while ((length = resource.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
            return new String(out.toByteArray());
        }
    }

    @Override
    public String toString() {
        return this.sql;
    }
}
