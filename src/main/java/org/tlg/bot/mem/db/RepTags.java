/**
 *
 */
package org.tlg.bot.mem.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tlg.bot.mem.db.domain.BasePicture;
import org.tlg.bot.mem.db.domain.MediaTags;
import org.tlg.bot.mem.db.domain.MediaTagsId;
import org.tlg.bot.mem.db.domain.Picture;
import org.tlg.bot.mem.db.domain.Tags;
import org.tlg.bot.mem.db.domain.TlgMediaType;
import org.tlg.bot.mem.db.ds.Ds;

/**
 * Save tags.
 *
 * @author "Maksim Vakhnik"
 *
 */
public class RepTags {

    public static final int MAX_INLINE_RESP_SIZE = 50;
    private static final Logger log = LoggerFactory
        .getLogger(RepTags.class.getName());

    private final Ds ds;

    public RepTags(final Ds ds) {
        this.ds = ds;
    }

    public void save(final MediaTags tags) throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            conn.setAutoCommit(false);
            save(conn, tags);
            conn.commit();
        }
    }

    public void save(final Connection conn, final MediaTags tags)
        throws SQLException {
        final StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(MediaTags.TABLE)
            .append(" (tag_id, user_id, photo_id, media_type)")
            .append(" VALUES (?, ?, ?, ?)");
        final PreparedStatement ps = conn.prepareStatement(sql.toString());
        for (final String tag : tags.getTags().getTags()) {
            ps.setString(1, tag);
            ps.setInt(2, tags.getPicture().getUserId());
            ps.setString(3, tags.getPicture().getFileId());
            ps.setByte(4, tags.getPicture().getType().asByte());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    public void delete(final Picture picture) throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            delete(conn, picture);
        }
    }

    public void update(final MediaTags tags) throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            conn.setAutoCommit(false);
            // delete current picture
            delete(conn, tags.getPicture());
            // save with new tags
            save(conn, tags);
            conn.commit();
        }
    }

    /**
     * Commit externally this transaction
     *
     * @param media
     * @throws SQLException
     */
    private void delete(final Connection conn, final Picture media)
        throws SQLException {
        final StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(MediaTags.TABLE)
            .append(" WHERE photo_id = ? and user_id = ?");
        final PreparedStatement ps = conn.prepareStatement(sql.toString());
        ps.setString(1, media.getFileId());
        ps.setInt(2, media.getUserId());
        log.debug("DELETE:{}", ps);
        ps.execute();
    }

    public Optional<Tags> findTagsByFileId(final Picture media)
        throws SQLException {
        final Collection<String> result = new ArrayList<>();
        try (final Connection conn = ds.dataSource().getConnection()) {
            final StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT tag_id FROM  ");
            sql.append(MediaTags.TABLE)
                .append(" WHERE user_id = ? AND photo_id = ?");
            final PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, media.getUserId());
            ps.setString(2, media.getFileId());
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("tag_id"));
            }

        }
        return !result.isEmpty() ? Optional.of(new Tags(result))
            : Optional.empty();
    }

    public Collection<Picture> findByTags(final Tags tags, final Integer userId)
        throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            final StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT photo_id, user_id, media_type FROM ");
            sql.append(MediaTags.TABLE).append(" WHERE ");
            final String OR = " OR ";
            final String AND = " AND ";
            tags.getTags().forEach(tag -> {
                sql.append("tag_id LIKE ? ");
                sql.append(OR);
            });
            if (!tags.getTags().isEmpty()) {
                sql.setLength(sql.length() - OR.length());
                sql.append(AND);
            }
            sql.append("user_id = ?");

            final PreparedStatement ps = conn.prepareStatement(sql.toString());
            int index = 1;
            for (final String tag : tags.getTags()) {
                ps.setString(index++, tag + "%");
            }

            ps.setInt(tags.getTags().size() + 1, userId);
            log.debug("findByTags:{}", ps);
            final ResultSet rs = ps.executeQuery();

            return RepTags.pictures(rs);
        }
    }

    public boolean isSaved(final Picture media) throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            final StringBuilder sql = new StringBuilder("SELECT 1 FROM ")
                .append(MediaTags.TABLE)
                .append(" WHERE user_id = ? AND photo_id = ?");
            final PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, media.getUserId());
            ps.setString(2, media.getFileId());
            final ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private static Collection<Picture> pictures(final ResultSet rs)
        throws SQLException {
        final Collection<Picture> result = new ArrayList<>();
        while (rs.next()) {
            result.add(
                new BasePicture(rs.getInt("user_id"), rs.getString("photo_id"),
                    TlgMediaType.get(rs.getByte("media_type"))));
        }
        return result;
    }

    public Collection<MediaTags> findUserMediaTags(final Integer userId)
        throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            final StringBuilder sql = new StringBuilder("SELECT * FROM ")
                .append(MediaTags.TABLE)
                .append(" WHERE user_id = ?");
            final PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, userId);
            final ResultSet rs = ps.executeQuery();
            return RepTags.mediaTags(rs);
        }
    }

    public Optional<MediaTags> findById(final MediaTagsId id)
        throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            final StringBuilder sql = new StringBuilder("SELECT * FROM ")
                .append(MediaTags.TABLE)
                .append(" WHERE user_id = ? AND photo_id = ?");
            final PreparedStatement ps = conn.prepareStatement(sql.toString());
            ps.setInt(1, id.getUserId());
            ps.setString(2, id.getMediaId());
            final ResultSet rs = ps.executeQuery();
            final Collection<MediaTags> mediaTags = RepTags.mediaTags(rs);
            return Optional.ofNullable(
                mediaTags.size() > 0 ? mediaTags.iterator().next() : null);
        }
    }

    private static Collection<MediaTags> mediaTags(final ResultSet rs)
        throws SQLException {
        final Collection<MediaTags> result = new ArrayList<>();
        final Map<Picture, Collection<String>> tags = new HashMap<>();
        while (rs.next()) {
            final Picture picture = new BasePicture(
                rs.getInt("user_id"),
                rs.getString("photo_id"),
                TlgMediaType.get(rs.getByte("media_type")));
            final Collection<String> picTags = tags.getOrDefault(picture,
                new ArrayList<>());
            picTags.add(rs.getString("tag_id"));
            tags.put(
                picture,
                picTags);
        }
        tags.forEach((pic, picTags) -> {
            result.add(new MediaTags(pic, new Tags(picTags)));
        });
        return result;
    }

}
