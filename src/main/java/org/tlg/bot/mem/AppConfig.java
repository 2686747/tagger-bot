/**
 * 
 */
package org.tlg.bot.mem;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class
 * @author "Maksim Vakhnik"
 *
 */
public class AppConfig {

    private static final Logger log = LoggerFactory
        .getLogger(AppConfig.class.getName());
    
    private static final String ENV_PROPFILE = "app.config";
    private static final String DEFAULT_FILEPROP = "/app.properties";
    private final String property;
    public AppConfig(final String property) {
        this.property = property;
    }
    public String value() {
        final Properties props = new Properties();
        final String fileProp = System.getProperty(ENV_PROPFILE);
        
        final String fileRes = (fileProp == null || fileProp.isEmpty()) ?
            DEFAULT_FILEPROP :
            fileProp;
        log.debug("app.config:{}", fileRes);
        try (final InputStream is = Paths.get(fileRes).toFile().isFile() ? 
            new FileInputStream(Paths.get(fileRes).toFile()) :
            AppConfig.class.getResourceAsStream(fileRes)) {
            
        
            props.load(is);
        } catch (final Throwable e) {
            log.error("Can't load properties from resource [{}]", fileRes);
            throw new RuntimeException(e);
        }
        return props.getProperty(property);
    }
    
    public static void main(final String[] args) {
        System.out.println(new AppConfig("membot.token").value());
    }
    

}
