/**
 * 
 */
package org.tlg.bot.mem.db;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collection;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.db.domain.BasePicture;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgMediaType;
import org.tlg.bot.mem.db.ds.DsHikari;
import org.tlg.bot.mem.db.init.DbTest;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class TagsTest {
    private static final Logger log = LoggerFactory
        .getLogger(TagsTest.class.getName());

    @Before
    public void setUp() throws SQLException, IOException, URISyntaxException {
        new DbTest(DsHikari.ds()).create();
    }

    @Test
    public void saveTwoPhotoFindByTagShouldFindOne() throws SQLException {

        final Integer userId = 1;
        final Picture saved1 = new BasePicture(userId, "1", TlgMediaType.PHOTO);
        final Picture saved2 = new BasePicture(userId, "2", TlgMediaType.PHOTO);
        // new RepPhotos(DsHikari.ds()).save(saved1);
        // new RepPhotos(DsHikari.ds()).save(saved2);
        final String tag1 = "tag1";
        final Tags tags1 = new Tags(tag1 + ".tag2.tag3");
        final Tags tags2 = new Tags("tag2.tag3");
        final Tags srchTag = new Tags(tag1);
        final MediaTags pht1 = new MediaTags(saved1, tags1);
        final MediaTags pht2 = new MediaTags(saved2, tags2);
        new RepTags(DsHikari.ds()).save(pht1);
        new RepTags(DsHikari.ds()).save(pht2);
        final Collection<Picture> photos = new RepTags(DsHikari.ds())
            .findByTags(srchTag, userId);
        MatcherAssert.assertThat("Result of tags is not correct", photos,
            Matchers.hasSize(1));
        MatcherAssert.assertThat("Photo is not correct",
            photos.iterator().next(), Matchers.equalTo(saved1));
    }

    @Test
    public void savePhotosFindByIncompleteTag() throws SQLException {
        // pictures both users
        final int pict = 5000;
        final Integer user1 = 1;
        final Integer user2 = 2;
        log.debug("start save {} pictures...", pict);
        for (int i = 1; i <= pict; i++) {
            final Picture ph1 =
                new BasePicture(user1, String.valueOf(i), TlgMediaType.PHOTO);
            final Picture ph2 =
                new BasePicture(user2, String.valueOf(i), TlgMediaType.PHOTO);
            final Tags tags = new Tags("tag" + i);
            new RepTags(DsHikari.ds()).save(new MediaTags(ph1, tags));
            new RepTags(DsHikari.ds()).save(new MediaTags(ph2, tags));
        }
        final Tags tags = new Tags("tag");
        log.debug("start search pictures by tags [{}]", tags);
        final long s = System.nanoTime();
        final Collection<Picture> photos = new RepTags(DsHikari.ds())
            .findByTags(tags, user1);
        final long e = System.nanoTime();
        log.debug("search time:{},ms", (e - s) * 1e-9);
        MatcherAssert.assertThat("Result of tags is not correct", photos,
            Matchers.hasSize(pict));

        final Collection<Picture> partOfPhotos = new RepTags(DsHikari.ds())
            .findByTags(new Tags("tag499"), user2);
        MatcherAssert.assertThat("Result of tags is not correct", partOfPhotos,
            Matchers.hasSize(11));
//        H2Console.main();
    }
}
