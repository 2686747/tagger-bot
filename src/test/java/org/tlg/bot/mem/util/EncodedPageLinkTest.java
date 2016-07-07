package org.tlg.bot.mem.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.tlg.bot.mem.exceptions.EncodedException;

public class EncodedPageLinkTest {

    @Test
    public void incorrectInputShouldThrowException() {
        try {
            new EncodedPageLink("incorrect page");
            fail("Created with incorrect input");
        } catch (final EncodedException e) {
            // done
        }
    }

    @Test
    public void correctInputShouldReturnCorrectData() {
        final long created = System.currentTimeMillis();
        final int userId = 11234;
        final EncodedPageLink epl = new EncodedPageLink(userId, created);
        assertThat(epl.id(), equalTo(userId));
        assertThat(epl.created(), equalTo(created));
        assertThat(epl.url(), not(containsString(String.valueOf(userId))));
        assertThat(epl.url(), not(containsString(String.valueOf(created))));
    }
}
