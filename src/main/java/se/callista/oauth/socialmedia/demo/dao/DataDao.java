package se.callista.oauth.socialmedia.demo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by magnus on 18/08/14.
 */
@Repository
public class DataDao {

    private static final Logger LOG = LoggerFactory.getLogger(DataDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getData(String userId) {
        try {
            LOG.debug("SQL SELECT ON: {}", userId);
            return jdbcTemplate.queryForObject("select data from Data where userId = ?",
                new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String data = rs.getString("data");
                    LOG.debug("SQL SELECT RETURN: {}", data);
                    return data;
                    }
                }, userId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void setDate(String userId, String data) {
        if (getData(userId) == null) {
            LOG.debug("SQL INSERT ON: {}, data = {}", userId, data);
            jdbcTemplate.update("INSERT into data (userId, data) VALUES(?,?)", userId, data);
        } else {
            LOG.debug("SQL UPDATE ON: {}, data = {}", userId, data);
            jdbcTemplate.update("UPDATE data SET data = ? WHERE userId = ?", data, userId);
        }
    }
}
