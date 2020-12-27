package org.tlg.bot.mem.db.ds;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Execute sql from file.
 * @author "Maksim Vakhnik"
 *
 */
public class Db {

    private final ReadedString sql;
    private final Ds ds;

    /**
     *
     * @param ds
     * @param resource
     * @throws IOException
     */
    public Db(final Ds ds, final File resource) throws IOException {
        this(ds, new ReadedString(resource));
    }

    public Db(final Ds ds, final InputStream resource) throws IOException {
        this(ds, new ReadedString(resource));
    }


    private Db(final Ds ds, final ReadedString sql) {
        this.ds = ds;
        this.sql = sql;
    }
    /**
     * Creates/updates tables in database.
     * @throws SQLException
     * @throws IOException
     * @return either (1) the row count for SQL Data Manipulation Language (DML)
     *  statements or (2) 0 for SQL statements that return nothing
     */
    public int exec() throws SQLException, IOException {
        final PreparedStatement ps = this.ds.dataSource()
            .getConnection().prepareStatement(this.sql.toString());
        return ps.executeUpdate();
    }

}