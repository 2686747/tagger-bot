package org.tlg.bot.mem.db.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.testng.annotations.Test;

/**
 * 
 * @author "Maksim Vakhnik"
 *
 */
public class PageUrlTest {

    @Test
    public void diffPageLinksShouldHaveDiffPageUrl() {
        final PageUrl pu1 = new PageUrl(
            new PageLink(Long.MIN_VALUE, 2L));
        final PageUrl pu2 = new PageUrl(
            new PageLink(0, Long.MAX_VALUE));
        assertNotEquals(pu1.getUrl(), pu2.getUrl());
    }
    
    @Test
    public void samePageLinksShouldHaveSamePageUrl() {
        final PageUrl pu1 = new PageUrl(
            new PageLink(Long.MIN_VALUE, 2L));
        final PageUrl pu2 = new PageUrl(
            new PageLink(Long.MIN_VALUE, 2L));
        assertEquals(pu1.getUrl(), pu2.getUrl());
    }
}
