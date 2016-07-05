package org.tlg.bot.mem.db.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import org.testng.annotations.Test;
import org.tlg.bot.mem.exceptions.WrongUrlException;

/**
 * 
 * @author Maksim Vakhnik
 *
 */
public class PageLinkTest {

    @Test
    public void diffUserIdEntityShouldBeNotEquals() throws Exception {
        final long created = System.nanoTime();
        assertNotEquals(new PageLink(1L, created), new PageLink(2L, created));
    }

    @Test
    public void diffCreatedTimeEntityShouldBeNotEquals() throws Exception {
        final long created = System.nanoTime();
        assertNotEquals(
            new PageLink(1L, created),
            new PageLink(1L, created + 1)
            );
    }
    
    @Test
    public void sameCreatedAndIdEntityShouldBeEquals() throws Exception {
        final long created = System.nanoTime();
        assertEquals(
            new PageLink(1L, created),
            new PageLink(1L, created)
            );
    }

    @Test
    public void wrongUrlEntityShouldThrowException() {
            try {
                new PageLink("url2");
                fail("Exception is expected");
            } catch (final WrongUrlException e) {
                //done
            }
    }
    
    @Test
    public void urlCtorShouldBeEqualIdCreatedCtor() throws Exception {
        final long created = System.nanoTime();
        final long id = 1L;
        final String url = new PageLink(id, created).getUrl();
        assertEquals(new PageLink(id, created), new PageLink(url));
    }
    
    @Test
    public void diffUrlEntityShouldBeNotEquals() throws Exception {
        final long created = System.nanoTime();
        final String url1 = new PageLink(1L, created).getUrl();
        final String url2 = new PageLink(2L, created).getUrl();
        assertNotEquals(
            new PageLink(url1),
            new PageLink(url2)
            );
    }
    
}