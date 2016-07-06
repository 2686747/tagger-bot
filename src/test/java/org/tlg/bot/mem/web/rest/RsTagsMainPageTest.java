package org.tlg.bot.mem.web.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;
import javax.ws.rs.core.Response;
import org.testng.annotations.Test;
import org.tlg.bot.mem.db.RepPageLinks;
import org.tlg.bot.mem.db.domain.PageLink;

/**
 * 
 * @author Maksim Vakhnik
 *
 */
// TODO server to diff port
public class RsTagsMainPageTest extends JerseyDbMethodTest {
   

    @Test
    public void inccorrectEmptyUrlShouldReturn404() {
        assertThat(target("").request().get().getStatus(), equalTo(404));
    }

    @Test
    public void inccorrectUrlShouldReturn404() {
        assertThat(target("url/url1").request().get().getStatus(),
            equalTo(404));
    }

    @Test
    public void correctNonexistedUrlShouldReturn404() {
        final String url = new PageLink(1, 1L).getUrl();
        final Response response = target(url).request().get();
        assertThat(response.getStatus(), equalTo(404));
    }

 
    @Test
    public void correctExistedUrlShouldReturnCorrectPageWithToken()
        throws SQLException, IOException, URISyntaxException {
        // create PageUrl for current user
        final int userId = 1;

        new RepPageLinks(this.getDs()).create(userId);
        final Optional<PageLink> pl = new RepPageLinks(this.getDs())
            .findByUserId(userId);
        final PageLink pageLink = pl.get();
        final String url = pageLink.getUrl();
        final Response response = target(url).request().get();
        assertThat(response.getStatus(), equalTo(200));
        assertThat(response.readEntity(String.class), containsString(url));

    }
}
