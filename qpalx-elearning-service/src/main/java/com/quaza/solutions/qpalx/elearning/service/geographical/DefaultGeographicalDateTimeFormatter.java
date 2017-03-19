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
@Service(DefaultGeographicalDateTimeFormatter.SPRING_BEAN_NAME)
public class DefaultGeographicalDateTimeFormatter implements IGeographicalDateTimeFormatter  {




    public static final DateTimeFormatter DATE_TIME_DISPLAY_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    public static final DateTimeFormatter JAVASCRIPT_DATE_TIME_DISPLAY_FORMATTER = DateTimeFormat.forPattern("yyyy, MM, dd, HH, mm");

    public static final DateTimeFormatter DB_DATE_TIME_DISPLAY_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter SIMPLE_DISPLAY_FORMATTER = DateTimeFormat.forPattern("d MMM, yyyy HH:mm");


    public static final String SPRING_BEAN_NAME = "quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultGeographicalDateTimeFormatter.class);


    @Override
    public String getUserFriendlyDateTime(DateTime dateTime, QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(dateTime, "dateTime cannot be null");
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.info("Creating and returning user frienldy dateTime:> {} and qPalXMunicipality:> {}", dateTime, qPalXMunicipality);
        return dateTime.toString(SIMPLE_DISPLAY_FORMATTER);
    }

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

    @Override
    public String getJavaScriptSafeDisplayDateTimeWithTimeZone(DateTime dateTime, QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(dateTime, "dateTime cannot be null");
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.info("Getting Javascript safe display DateTime for dateTime:> {} and qPalXMunicipality:> {}", dateTime, qPalXMunicipality);


        // Convert passed in Date to User TimeZone Date
        String timeZone = qPalXMunicipality.getTimeZone();
        DateTimeZone dateTimeZone = DateTimeZone.forID(timeZone);
        String timeZoneDateTimeString =  DATE_TIME_DISPLAY_FORMATTER.withZone(dateTimeZone).print(dateTime);

        // Convert User TimeZone date string back to DateTime in order to subtract 1month since Javascript months start with 0
        DateTime timeZoneDateTime = DATE_TIME_DISPLAY_FORMATTER.parseDateTime(timeZoneDateTimeString);
        timeZoneDateTime = timeZoneDateTime.minusMonths(1);

        return timeZoneDateTime.toString(JAVASCRIPT_DATE_TIME_DISPLAY_FORMATTER);
    }

    @Override
    public DateTime getDatabaseStringAsDateTime(String databaseDateTime) {
        Assert.notNull(databaseDateTime, "databaseDateTime cannot be null");
        return DateTime.parse(databaseDateTime, DB_DATE_TIME_DISPLAY_FORMATTER);
    }

    public static void main(String[] args) {
        DateTime now = DateTime.now();
        System.out.println("Now In Plain display = " + now.toString(DATE_TIME_DISPLAY_FORMATTER));

        DateTimeZone dateTimeZone = DateTimeZone.forID("UTC");
        String nowAsGMT = DATE_TIME_DISPLAY_FORMATTER.withZone(dateTimeZone).print(now);
        System.out.println("Now to GMT: "+ nowAsGMT);

        DateTime nowDTGMT = DATE_TIME_DISPLAY_FORMATTER.parseDateTime(nowAsGMT);
        System.out.println("Now to GMT DateTime: "+ nowDTGMT);

        DateTime nowDTGMTMinusOneMonth = DATE_TIME_DISPLAY_FORMATTER.parseDateTime(nowAsGMT).minusMonths(1);
        System.out.println("Simple DateTime - 1M: "+ nowDTGMTMinusOneMonth.toString(SIMPLE_DISPLAY_FORMATTER));

//        DefaultGeographicalDateTimeFormatter d = new DefaultGeographicalDateTimeFormatter();
//        String val = d.getJavaScriptSafeDisplayDateTimeWithTimeZone(now, null);
//
//
//        System.out.println("Val = " + val);
    }

}
