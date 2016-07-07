package org.tlg.bot.mem.db.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.testng.annotations.Test;
import org.tlg.bot.mem.util.EncodedPageLink;

/**
 * 
 * @author Maksim Vakhnik
 *
 */
public class PageLinkTest {

    @Test
    public void diffUserIdEntityShouldBeNotEquals() throws Exception {
        final long created = System.nanoTime();
        assertNotEquals(new PageLink(1, created), new PageLink(2, created));
    }

    @Test
    public void diffCreatedTimeEntityShouldBeNotEquals() throws Exception {
        final long created = System.nanoTime();
        assertNotEquals(
            new PageLink(1, created),
            new PageLink(1, created + 1));
    }

    @Test
    public void sameCreatedAndIdEntityShouldBeEquals() throws Exception {
        final long created = System.nanoTime();
        assertEquals(
            new PageLink(1, created),
            new PageLink(1, created));
    }

    @Test
    public void urlCtorShouldBeEqualIdCreatedCtor() throws Exception {
        final long created = System.nanoTime();
        final int id = 1;
        final EncodedPageLink url = new EncodedPageLink(id, created);
        assertEquals(new PageLink(id, created), new PageLink(url));
    }

    @Test
    public void diffUrlEntityShouldBeNotEquals() throws Exception {
        final long created = System.nanoTime();
        final EncodedPageLink url1 = new EncodedPageLink(1, created);
        final EncodedPageLink url2 = new EncodedPageLink(2, created);
        assertNotEquals(
            new PageLink(url1),
            new PageLink(url2));
    }

}
