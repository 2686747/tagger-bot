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
import org.tlg.bot.mem.db.domain.PageLink;
import org.vmk.db.ds.Ds;

/**
 * @author "Maksim Vakhnik"
 *
 */
public class RepPageLinks {
    private static final Logger log = LoggerFactory
        .getLogger(RepPageLinks.class.getName());

    private final Ds ds;

    public RepPageLinks(final Ds ds) {
        this.ds = ds;
    }

    /**
     * Creates(replace current) url in database for current user
     * 
     * @param userId
     * @throws SQLException 
     */
    public void create(final int userId) throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            final Optional<PageLink> pageUrl = findByUserId(userId);
            if (pageUrl.isPresent()) {
                delete(conn, userId);
            }
            save(conn, userId);
            conn.commit();
        } 
    }

    private void save(final Connection conn, final int userId) throws SQLException {
        final StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(PageLink.TABLE)
            .append(" (user_id, created) VALUES (?, ?)");
        final PreparedStatement ps = conn.prepareStatement(sql.toString());
        ps.setLong(1, userId);
        ps.setLong(2, System.nanoTime());
        log.debug("save page link:{}", ps);
        ps.execute();
        
    }

    private void delete(final Connection conn, final int userId)
        throws SQLException {
        final StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(PageLink.TABLE)
            .append(" WHERE user_id = ?");
        final PreparedStatement ps = conn.prepareStatement(sql.toString());
        ps.setLong(1, userId);
        log.debug("DELETE:{}", ps);
        ps.execute();

    }

    public Optional<PageLink> findByUserId(final int userId)
        throws SQLException {
        try (final Connection conn = ds.dataSource().getConnection()) {
            final PreparedStatement ps = conn.prepareStatement(
                new StringBuilder("SELECT * FROM ").append(PageLink.TABLE)
                    .append(" WHERE user_id = ?").toString());
            ps.setInt(1, userId);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(
                    new PageLink(rs.getInt("user_id"), rs.getLong("created")));
            }
        }
        return Optional.empty();
    }

}
