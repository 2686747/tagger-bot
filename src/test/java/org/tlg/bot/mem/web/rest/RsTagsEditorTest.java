package org.tlg.bot.mem.web.rest;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.tlg.bot.mem.db.RepPageLinks;
import org.tlg.bot.mem.db.RepTags;
import org.tlg.bot.mem.db.domain.BasePicture;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.PageLink;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgMediaType;
import org.tlg.bot.mem.web.dto.TagsDto;

/**
 * 
 * @author Maksim Vakhnik
 *
 */
public class RsTagsEditorTest extends JerseyDbMethodTest {
    private Integer userId;
    private MediaTags pht1;
    private MediaTags pht2;
    private MediaTags phtUnsaved;

    @BeforeMethod
    public void fillDb() throws SQLException {
        this.userId = 1;
        final Picture saved1 = new BasePicture(userId, "1", TlgMediaType.PHOTO);
        final Tags tags1 = new Tags("tag1 tag2 tag3");
        this.pht1 = new MediaTags(saved1, tags1);
        final Picture saved2 = new BasePicture(userId, "2", TlgMediaType.PHOTO);
        final Tags tags2 = new Tags("tag22 tag23");
        this.pht2 = new MediaTags(saved2, tags2);
        this.phtUnsaved = new MediaTags(
            new BasePicture(userId + 1, "2", TlgMediaType.PHOTO),
            new Tags("tag22 tag23"));
        new RepTags(this.getDs()).save(pht1);
        new RepTags(this.getDs()).save(pht2);
        new RepPageLinks(this.getDs()).create(userId);
    }

    @Test
    public void withEmptyTagsShouldDeleteMedia() throws SQLException {
        final Optional<PageLink> pl = new RepPageLinks(this.getDs())
            .findByUserId(this.userId);
        final String token = pl.get().getUrl();
        final Tags updatedTags = new Tags("");
        final TagsDto tagsDto = new TagsDto(
            new MediaTags(this.pht1.getPicture(), updatedTags));
        final Entity<Collection<TagsDto>> json = Entity
            .json(Arrays.asList(tagsDto));
        target(RsTagsEditor.URL_TAGS_UPDATE).request()
            .header("token", token).post(json);
        final Optional<MediaTags> updated = new RepTags(this.getDs())
            .findById(this.pht1.getId());
        assertFalse("Should be deleted", updated.isPresent());
    }
    
    @Test
    public void tagsWithCorrectTokenShouldReturnTags() throws SQLException {
        final Optional<PageLink> pl = new RepPageLinks(this.getDs())
            .findByUserId(userId);
        final String token = pl.get().getUrl();
        final Response response = target(RsTagsEditor.URL_TAGS_ALL).request()
            .header("token", token).get();
        assertThat(response.getStatus(), equalTo(200));
        final String strResp = response.readEntity(String.class);

        assertThat(strResp, containsString("tag22"));
        assertThat(strResp, containsString("tag23"));
        assertThat(strResp, containsString("tag1"));
        assertThat(strResp, containsString("tag2"));
        assertThat(strResp, containsString("tag3"));
    }

    @Test
    public void updateWithCorrectData() throws Exception {
        final Optional<PageLink> pl = new RepPageLinks(this.getDs())
            .findByUserId(this.userId);
        final String token = pl.get().getUrl();
        final Tags updatedTags = new Tags("updatedTag1 updatedTag2");
        final TagsDto tagsDto = new TagsDto(
            new MediaTags(this.pht1.getPicture(), updatedTags));
        final Entity<Collection<TagsDto>> json = Entity
            .json(Arrays.asList(tagsDto));
        target(RsTagsEditor.URL_TAGS_UPDATE).request()
            .header("token", token).post(json);
        final Optional<MediaTags> updated = new RepTags(this.getDs())
            .findById(this.pht1.getId());
        assertThat(updated.get(), IsNot.not(this.pht1));
        assertThat(updated.get().getTags(), Is.is(updatedTags));
    }

    @Test
    public void updateWithIncorrectTokenShouldReturn404() throws Exception {
        final Tags updatedTags = new Tags("updatedTag1 updatedTag2");
        final TagsDto tagsDto = new TagsDto(
            new MediaTags(this.pht1.getPicture(), updatedTags));
        final Entity<Collection<TagsDto>> json = Entity
            .json(Arrays.asList(tagsDto));
        assertThat(target(RsTagsEditor.URL_TAGS_UPDATE).request()
            .header("token", "sometoken").post(json).getStatus(), Is.is(404));
    }
    
    @Test
    public void updateWithIncorrectDataShouldReturnNot200() throws Exception {
        final Optional<PageLink> pl = new RepPageLinks(this.getDs())
            .findByUserId(this.userId);
        final String token = pl.get().getUrl();
        final Tags updatedTags = new Tags("updatedTag1 updatedTag2");
        final TagsDto tagsDto = new TagsDto(
            new MediaTags(this.phtUnsaved.getPicture(), updatedTags));
        final Entity<Collection<TagsDto>> json = Entity
            .json(Arrays.asList(tagsDto));
        assertThat(target(RsTagsEditor.URL_TAGS_UPDATE).request()
            .header("token", token).post(json).getStatus(), IsNot.not(200));
    }
}
