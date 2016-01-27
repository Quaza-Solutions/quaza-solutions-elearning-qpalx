package com.quaza.solutions.qpalx.elearning.service.mock;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;

/**
 * @author manyce400
 */
public class MockQPalXCountryBuilder {


    // Returns a QPalXCountryBuilder initialized with default data using Ghana as Country
    public static QPalXCountry buildMockGhanaCountryBuilder() {
        QPalXCountry qPalXCountry = QPalXCountry.builder()
                .countryName("Ghana")
                .countryCode("GH")
                .countryCurrency("GH Cedis")
                .build();
        return qPalXCountry;
    }

    // Returns a QPalXCountryBuilder initialized with default data using USA as Country.
    public static QPalXCountry buildMockUSACountryBuilder() {
        QPalXCountry qPalXCountry = QPalXCountry.builder()
                .countryName("United States of America")
                .countryCode("US")
                .countryCurrency("USD")
                .build();
        return qPalXCountry;
    }
}
