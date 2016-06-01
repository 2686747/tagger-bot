package org.tlg.bot.mem.db.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class TagsTest {

    @Test
    public void testEqualTags() {
        assertEquals(
            "Tags have to be equals",
            new Tags("tag1 tag2"),
            new Tags("tag1 tag2")
        );
    }

    @Test
    public void testEqualTagsDiffOrder() {
        assertEquals(
            "Tags have to be equals",
            new Tags("tag1 tag2"),
            new Tags("tag2 tag1")
        );
    }
    
    @Test
    public void testDiffTags() {
        assertNotEquals(
            "Tags have to be not equals",
            new Tags("tag1 tag2"),
            new Tags("")
        );
    }
    
    @Test
    public void testAlmostTags() {
        assertNotEquals(
            "Tags have to be not equals",
            new Tags("tag1 tag2 tag"),
            new Tags("tag1 tag2 tag3")
        );
    }
    
    @Test
    public void extraSpacesShouldRemoved() {
        assertEquals(
            "Tags have to be not equals",
            new Tags("      tag1    tag2    tag3    "),
            new Tags("tag1 tag2 tag3")
        );
        
        assertEquals(
            "Tags count is wrong",
            3,
            new Tags("tag1     tag2    tag   ").getTags().size()
            );
    }
}
