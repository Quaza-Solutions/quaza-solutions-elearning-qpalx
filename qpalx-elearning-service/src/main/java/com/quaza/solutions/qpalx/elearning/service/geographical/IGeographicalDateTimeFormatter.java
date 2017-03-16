package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import org.joda.time.DateTime;

/**
 * Provides DateTime formatting functionality based on a specific Municipality and its matching defined TimeZone
 *
 * @author manyce400
 */
public interface IGeographicalDateTimeFormatter {


    public String getDisplayDateTimeWithTimeZone(DateTime dateTime, QPalXMunicipality qPalXMunicipality);

    public String getJavaScriptSafeDisplayDateTimeWithTimeZone(DateTime dateTime, QPalXMunicipality qPalXMunicipality);

    public DateTime getDatabaseStringAsDateTime(String databaseDateTime);

}