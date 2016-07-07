package org.tlg.bot.mem.web.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import java.sql.SQLException;
import java.util.Optional;
import javax.ws.rs.core.Response;
import org.testng.annotations.Test;
import org.tlg.bot.mem.db.RepPageLinks;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.BasePicture;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.PageLink;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgMediaType;

/**
 * 
 * @author Maksim Vakhnik
 *
 */
public class RsTagsEditorTest  extends JerseyDbMethodTest {
    
    @Test
    public void tagsWithCorrectTokenShouldReturnTags() throws SQLException {
        // create PageUrl for current user
        final Integer userId = 1;
        final Picture saved1 = new BasePicture(userId, "1", TlgMediaType.PHOTO);
        final Tags tags1 = new Tags("tag1 tag2 tag3");
        final MediaTags pht1 = new MediaTags(saved1, tags1);
        final Picture saved2 = new BasePicture(userId, "2", TlgMediaType.PHOTO);
        final Tags tags2 = new Tags("tag22 tag23");
        final MediaTags pht2 = new MediaTags(saved2, tags2);
        new RepTags(this.getDs()).save(pht1);
        new RepTags(this.getDs()).save(pht2);
        
        new RepPageLinks(this.getDs()).create(userId);
        final Optional<PageLink> pl = new RepPageLinks(this.getDs())
            .findByUserId(userId);
        final PageLink pageLink = pl.get();
        final String token = pageLink.getUrl();
        final Response response =
            target("tags").request().header("token", token).get();
        assertThat(response.getStatus(), equalTo(200));
        System.out.printf("res:%s", response.readEntity(String.class));
       
//        assertThat(response.readEntity(String.class), containsString(url));
    }
    

}
