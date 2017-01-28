package com.quaza.solutions.qpalx.elearning.domain.api.rowmapper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author manyce400
 */
public abstract class AbstractDomainRowMapper<R> implements RowMapper<R> {



    protected final DateTimeFormatter dateTimeFormatterDB;

    protected final DateTimeFormatter dateTimeFormatterDisplay;

    public AbstractDomainRowMapper(DateTimeFormatter dateTimeFormatterDB, DateTimeFormatter dateTimeFormatterDisplay) {
        this.dateTimeFormatterDB = dateTimeFormatterDB;
        this.dateTimeFormatterDisplay = dateTimeFormatterDisplay;
    }

    public Optional<DateTime> getResultSetDateTime(ResultSet resultSet, String colName) throws SQLException {
        String dateTimeString = resultSet.getString(colName);

        if (dateTimeString != null) {
            int idx = dateTimeString.indexOf(".0");

            if(idx > 0) {
                dateTimeString = dateTimeString.substring(0, idx);
            }

            return Optional.of(DateTime.parse(dateTimeString, dateTimeFormatterDB));
        }

        return Optional.empty();
    }
}
