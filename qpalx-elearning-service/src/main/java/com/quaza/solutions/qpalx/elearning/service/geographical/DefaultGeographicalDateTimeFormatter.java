package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Default implementation of IGeographicalDateTimeFormatter
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
public class DefaultGeographicalDateTimeFormatter implements IGeographicalDateTimeFormatter  {




    private static final DateTimeFormatter DATE_TIME_DISPLAY_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultGeographicalDateTimeFormatter.class);

    @Override
    public String getDisplayDateTimeWithTimeZone(DateTime dateTime, QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(dateTime, "dateTime cannot be null");
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.info("Getting display DateTime for dateTime:> {} and qPalXMunicipality:> {}", dateTime, qPalXMunicipality);

        // Get Municipality timezone
        String timeZone = qPalXMunicipality.getTimeZone();
        DateTimeZone dateTimeZone = DateTimeZone.forID(timeZone);
        return DATE_TIME_DISPLAY_FORMATTER.withZone(dateTimeZone).print(dateTime);
    }

}
