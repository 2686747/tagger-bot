/**
 * 
 */
package org.tlg.bot.mem.db.init;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import org.vmk.db.Db;
import org.vmk.db.ds.Ds;

/**
 * Creates test db
 * @author "Maksim Vakhnik"
 *
 */
public class DbTest {
    private static final String SQL_CREATE_TABLE = "/sql/create-tables.sql";
    private final Ds ds;
    
    
    public DbTest(final Ds ds) {
        this.ds = ds;
    }


    public void create() throws SQLException, IOException, URISyntaxException {
        new Db(
            this.ds,
            Paths.get(
                this.getClass().getResource(DbTest.SQL_CREATE_TABLE).toURI()
            ).toFile()
        ).exec();
    }
}
