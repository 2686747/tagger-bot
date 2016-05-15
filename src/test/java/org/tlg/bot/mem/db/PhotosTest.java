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
import org.tlg.bot.mem.db.ds.DsHikari;
import org.tlg.bot.mem.db.init.DbTest;

public class PhotosTest {
    
    @Before
    public void setUp() throws SQLException, IOException, URISyntaxException {
        new DbTest(DsHikari.ds()).create();
    }
    @Test
    public void saveRead() throws SQLException {
        final String photoId = "testPhoto";
        final Integer userId = 1;
        final Picture saved =
            new BasePicture(userId, photoId, TlgMediaType.PHOTO);
        new RepPictures(DsHikari.ds()).save(saved);
        final Optional<Picture> found =
            new RepPictures(DsHikari.ds()).find(photoId);
        MatcherAssert.assertThat(
            "Photos ain't equals", saved, Matchers.equalTo(found.get())
            );
    }

}
