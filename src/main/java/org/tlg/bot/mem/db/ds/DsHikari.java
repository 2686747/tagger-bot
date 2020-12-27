/**
 *
 */
package org.tlg.bot.mem.db.ds;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton dataSource. You have to define hikari.file property for env
 * @author "Maksim Vakhnik"
 *
 */
public class DsHikari implements Ds {
    private static final Logger log = LoggerFactory
        .getLogger(DsHikari.class.getName());

    private static final Ds ds = new DsHikari();
    //environment settings, must set in prod
    private static final String ENV_FILENAME = "hikari.config";
    //this file will be found in classpath
    public static final String DEFAULT_CONFIG = "/hikari.properties";

    private final DataSource dataSource;

    private DsHikari()  {
        log.debug("init ds");
        this.dataSource = initDs();
    }

    private DataSource initDs() {

        final String fileProp = System.getProperty(ENV_FILENAME);
        log.debug("Hikari config file:{}", fileProp);
        final HikariConfig config = new HikariConfig(
            fileProp == null || fileProp.isEmpty() ?
                DEFAULT_CONFIG :
                fileProp
            );
        return new HikariDataSource(config);
    }

    public static Ds ds() {
        return DsHikari.ds;
    }
    @Override
    public DataSource dataSource() {
        return this.dataSource;
    }


}
