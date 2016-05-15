/**
 * 
 */
package org.tlg.bot.mem;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.tlg.bot.mem.db.ds.DsHikari;

/**
 * Connect to test db.
 * @author "Maksim Vakhnik"
 *
 */
public class H2Console {

    public static void main(final String... args) throws SQLException {
        Server.startWebServer(DsHikari.ds().dataSource().getConnection()); 
    }
}
