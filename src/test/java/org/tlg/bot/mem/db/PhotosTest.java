package org.tlg.bot.mem.db;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.tlg.bot.mem.db.domain.BasePicture;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.TlgMediaType;
import org.tlg.bot.mem.db.init.DbTest;
import org.tlg.bot.mem.db.ds.Ds;
import helper.db.TestDs;

public class PhotosTest {
    private Ds ds;
    @Before
    public void setUp() throws SQLException, IOException, URISyntaxException {
        this.ds = new TestDs();
        new DbTest(this.ds).create();
    }
    @Test
    public void saveRead() throws SQLException {
        final String photoId = "testPhoto";
        final Integer userId = 1;
        final Picture saved =
            new BasePicture(userId, photoId, TlgMediaType.PHOTO);
        new RepPictures(this.ds).save(saved);
        final Optional<Picture> found =
            new RepPictures(this.ds).find(photoId);
        MatcherAssert.assertThat(
            "Photos ain't equals", saved, Matchers.equalTo(found.get())
            );
    }

}
