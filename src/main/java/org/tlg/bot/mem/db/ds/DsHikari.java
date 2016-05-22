/**
 * 
 */
package org.tlg.bot.mem.db.ds;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vmk.db.ds.Ds;
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
    private static final String ENV_FILENAME = "hikari.config";

    private final DataSource dataSource;
    
    private DsHikari()  {
        log.debug("init ds");
        this.dataSource = initDs();
    }
    
    private DataSource initDs() {
        
        HikariConfig config;
        final String fileProp = System.getProperty(ENV_FILENAME);
        System.getProperties().forEach((v, k) -> {
            log.debug("{}:{}", v, k);
        });
        log.debug("HIKARI:{}", fileProp);
        config = new HikariConfig(fileProp);
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
