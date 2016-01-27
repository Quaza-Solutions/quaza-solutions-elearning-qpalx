package com.quaza.solutions.qpalx.elearning.service.mock;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;

/**
 *
 * @author manyce400
 */
public class MockQPalXMunicipalityBuilder {



    public static QPalXMunicipality buildAccraQPalXMunicipalityBuilder() {
        // For data to make sense set the correct Country
        QPalXCountry ghana = MockQPalXCountryBuilder.buildMockGhanaCountryBuilder();

        QPalXMunicipality qPalXMunicipality = QPalXMunicipality.builder()
                .name("Accra")
                .code("ACC")
                .timeZone("UTC")
                .qPalXCountry(ghana)
                .build();
        return qPalXMunicipality;
    }

    public static QPalXMunicipality buildNewYorkQPalXMunicipalityBuilder() {
        // For data to make sense set the correct Country
        QPalXCountry usa = MockQPalXCountryBuilder.buildMockUSACountryBuilder();

        QPalXMunicipality qPalXMunicipality = QPalXMunicipality.builder()
                .name("New York")
                .code("NYC")
                .timeZone("EST")
                .qPalXCountry(usa)
                .build();
        return qPalXMunicipality;
    }


}
