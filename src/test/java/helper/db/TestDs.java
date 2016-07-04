/**
 * 
 */
package helper.db;

import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.vmk.db.ds.Ds;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Creates random DataSource.
 * @author Maksim Vakhnik
 *
 */
public class TestDs implements Ds{

    private final DataSource ds;
    
    public TestDs() {
        this.ds = TestDs.ds();
    }
    
    private static DataSource ds() {
        final HikariConfig config = new HikariConfig();
        final JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(
            String.format("jdbc:h2:mem:test%s", UUID.randomUUID().toString())
            );
        config.setDataSource(jdbcDataSource);
        return new HikariDataSource(config);
    }

    @Override
    public DataSource dataSource() throws SQLException {
        return this.ds;
    }

    @Override
    public String toString() {
        return "TestDs [ds=" + ds + "]";
    }

}
