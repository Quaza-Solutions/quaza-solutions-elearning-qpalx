package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXMunicipalityBuilder;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Running with Spring Unit test cos we inject an actual instance of DefaultGeographicalDateTimeFormatter for testing instead of a mock.
 *
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultGeographicalDateTimeFormatterTest {
    //great

    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;


    @Before
    public void beforeTest() {
        // create an instance of the real service here.
        iGeographicalDateTimeFormatter = new DefaultGeographicalDateTimeFormatter();
    }


    @Test
    public void testGetDisplayDateTimeWithTimeZone() {
        // Build a test Municipality
        QPalXMunicipality mockNewYork =  MockQPalXMunicipalityBuilder.buildNewYorkQPalXMunicipalityBuilder();
        String displayDateTime = iGeographicalDateTimeFormatter.getDisplayDateTimeWithTimeZone(new DateTime(), mockNewYork);
        System.out.println("displayDateTime = " + displayDateTime);
        Assert.assertNotNull(displayDateTime);
    }

}
