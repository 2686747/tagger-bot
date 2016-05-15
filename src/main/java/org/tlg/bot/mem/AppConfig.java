/**
 * 
 */
package org.tlg.bot.mem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

/**
 * Configuration class
 * @author "Maksim Vakhnik"
 *
 */
public class AppConfig {

    private static final String PROP_FILE = "app.properties";
    private final String property;
    public AppConfig(final String property) {
        this.property = property;
    }
    public String value() {
        final Properties props = new Properties();
        try {
            props.load(
                Files.newInputStream(
                    Paths.get(PROP_FILE),
                    StandardOpenOption.READ
                )
            );
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return props.getProperty(property);
    }
    
    

}
