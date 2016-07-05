package org.tlg.bot.mem.web.rest;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTestNg;
import org.testng.annotations.Test;
import org.tlg.bot.mem.db.RepPageLinks;
import org.tlg.bot.mem.db.domain.PageLink;
import org.tlg.bot.mem.db.init.DbTest;
import helper.db.TestDs;

/**
 * 
 * @author Maksim Vakhnik
 *
 */
//TODO server to diff port
public class RsTagsEditorTest extends JerseyTestNg.ContainerPerClassTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(RsTagsEditor.class);
    }
 
    @Test
    public void inccorrectEmptyUrlShouldReturn404() {
        assertThat(target("").request().get().getStatus(), equalTo(404));
    }
    
    @Test
    public void inccorrectUrlShouldReturn404() {
        assertThat(target("url/url1").request().get().getStatus(), equalTo(404));
    }
   
    @Test
    public void correctNonexistedUrlShouldReturn404() {
        final String url = new PageLink(1L, 1L).getUrl();
        final Response response = target(url).request().get();
        assertThat(response.getStatus(), equalTo(404));
    }
    
    //TODO check real page
    @Test
    public void correctEsixtedUrlShouldReturnCorrectPage()
        throws SQLException, IOException, URISyntaxException {
        final TestDs ds = new TestDs();
        new DbTest(ds).create();
        //create PageUrl for current user
        final long userId = 1L;
        
        new RepPageLinks(ds).create(userId);
        final Optional<PageLink> pl = new RepPageLinks(ds).findByUserId(userId);
        final PageLink pageLink = pl.get();
        final String url = pageLink.getUrl();
        final Response response = target(url).request().get();
        assertThat(response.getStatus(), equalTo(200));
        assertThat(response.getEntity().toString(), containsString("Tags editor"));
       
    }
}
