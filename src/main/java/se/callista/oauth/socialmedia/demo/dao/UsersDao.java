package se.callista.oauth.socialmedia.demo.dao;

import se.callista.oauth.socialmedia.demo.model.UserConnection;
import se.callista.oauth.socialmedia.demo.model.UserProfile;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by magnus on 18/08/14.
 */
@Repository
public class UsersDao {

    private static final Logger LOG = LoggerFactory.getLogger(UsersDao.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersDao(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserProfile getUserProfile(final String userId) {
        LOG.debug("SQL SELECT ON UserProfile: {}", userId);

        return jdbcTemplate.queryForObject("select * from UserProfile where userId = ?",
            new RowMapper<UserProfile>() {
                public UserProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserProfile(
                    userId,
                    rs.getString("name"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("username"));
                }
            }, userId);
    }

    public UserConnection getUserConnection(final String userId) {
        LOG.debug("SQL SELECT ON UserConnection: {}", userId);

        return jdbcTemplate.queryForObject("select * from UserConnection where userId = ?",
            new RowMapper<UserConnection>() {
                public UserConnection mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new UserConnection(
                    userId,
                    rs.getString("providerId"),
                    rs.getString("providerUserId"),
                    rs.getInt("rank"),
                    rs.getString("displayName"),
                    rs.getString("profileUrl"),
                    rs.getString("imageUrl"),
                    rs.getString("accessToken"),
                    rs.getString("secret"),
                    rs.getString("refreshToken"),
                    rs.getLong("expireTime"));
                }
            }, userId);
    }

    public void createUser(String userId, UserProfile profile) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("SQL INSERT ON users, authorities and userProfile: " + userId + " with profile: " +
                profile.getEmail() + ", " +
                profile.getFirstName() + ", " +
                profile.getLastName() + ", " +
                profile.getName() + ", " +
                profile.getUsername());
        }

        jdbcTemplate.update("INSERT into users(username,password,enabled) values(?,?,true)",userId, RandomStringUtils.randomAlphanumeric(8));
        jdbcTemplate.update("INSERT into authorities(username,authority) values(?,?)",userId,"USER");
        jdbcTemplate.update("INSERT into userProfile(userId, email, firstName, lastName, name, username) values(?,?,?,?,?,?)",
            userId,
            profile.getEmail(),
            profile.getFirstName(),
            profile.getLastName(),
            profile.getName(),
            profile.getUsername());
    }
}
