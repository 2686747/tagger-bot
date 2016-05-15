package org.tlg.bot.mem.db.init;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Start H2 server.
 * 
 * @author "Maksim Vakhnik"
 *
 */
public class DbH2OpenShift {
    private static final Logger log = LoggerFactory
        .getLogger(DbH2OpenShift.class.getName());

    public static void main(final String... args) {
        log.debug("Start H2 server...");
        DbH2OpenShift.startH2Server();
    }

    private static void startH2Server() {
        try {
            final Server server = Server.createTcpServer().start();
            
            log.debug("Started:{}", server);
        } catch (final SQLException e) {
            log.error("Can't create H2 server", e);
        }
    }
}
