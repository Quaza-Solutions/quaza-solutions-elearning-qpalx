package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXMunicipalityBuilder;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Running with Spring Unit test cos we inject an actual instance of DefaultGeographicalDateTimeFormatter for testing instead of a mock.
 *
 * @author manyce400
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {QPalXServiceApplicationBootstrapper.class}) // We use the main class Application from Services in order to run this
public class DefaultGeographicalDateTimeFormatterTest {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    @Test
    public void testGetDisplayDateTimeWithTimeZone() {
        // Build a test Municipality
        QPalXMunicipality mockNewYork =  MockQPalXMunicipalityBuilder.buildNewYorkQPalXMunicipalityBuilder();
        String displayDateTime = iGeographicalDateTimeFormatter.getDisplayDateTimeWithTimeZone(new DateTime(), mockNewYork);
        System.out.println("displayDateTime = " + displayDateTime);
        Assert.assertNotNull(displayDateTime);
    }

}
