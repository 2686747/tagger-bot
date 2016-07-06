package org.tlg.bot.mem.db;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.tlg.bot.mem.db.domain.PageLink;
import org.tlg.bot.mem.db.init.DbTest;
import helper.db.TestDs;

/**
 * 
 * @author "Maksim Vakhnik"
 *
 */
public class RepPageLinksTest {

    private TestDs ds;

    @BeforeMethod
    public void setUp()
            throws SQLException, IOException, URISyntaxException {
        this.ds = new TestDs();
        new DbTest(this.ds).create();
    }
    
    @Test
    public void createShouldReturnCorrectEntity() throws SQLException {
        final int userId = 1;
        final long testTime = System.nanoTime();
        new RepPageLinks(this.ds).create(userId);
        final Optional<PageLink> pl = new RepPageLinks(this.ds).findByUserId(userId);
        assertTrue(pl.isPresent());
        assertEquals(userId, pl.get().getUserId());
        assertThat(pl.get().getCreated(), greaterThanOrEqualTo(testTime));
    }
}
