/**
 *
 */
package org.tlg.bot.mem.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.db.domain.BasePicture;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.TlgMediaType;
import org.tlg.bot.mem.db.ds.Ds;

/**
 * Saves pictures.
 * @author "Maksim Vakhnik"
 *
 */
public class RepPictures {
private static final Logger log = LoggerFactory
    .getLogger(RepPictures.class.getName());

    private final Ds ds;

    public RepPictures(final Ds ds) {
        this.ds = ds;
    }

    public void save(final Picture photo) throws SQLException {

        try (final Connection conn = ds.dataSource().getConnection()) {
            final StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(Picture.TABLE)
                .append(" (photo_id, user_id) VALUES (?, ?)");
            final PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setString(1, photo.getFileId());
            ps.setInt(2, photo.getUserId());
            log.debug("save photo:{}", ps);
            ps.execute();
            conn.commit();
        }

    }

    public Optional<Picture> find(final String photoId) throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            final PreparedStatement ps = conn.prepareStatement(
                new StringBuilder("SELECT * FROM ").append(Picture.TABLE)
                    .append(" WHERE photo_id = ?").toString()
            );
            ps.setString(1, photoId);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new DbPhoto(rs));
            }

        }
        return Optional.empty();
    }

    private static class DbPhoto extends  BasePicture {

        private DbPhoto(final ResultSet rs) throws SQLException {
            super(DbPhoto.userId(rs), DbPhoto.photoId(rs), TlgMediaType.PHOTO);
        }

        private static String photoId(final ResultSet rs) throws SQLException {

            return rs.getString("photo_id");
        }

        private static Integer userId(final ResultSet rs) throws SQLException {
            return rs.getInt("user_id");
        }

    }
}
