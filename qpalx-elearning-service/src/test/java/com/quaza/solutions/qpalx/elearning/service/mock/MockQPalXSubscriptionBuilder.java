package com.quaza.solutions.qpalx.elearning.service.mock;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionTypeE;

/**
 *
 * @author manyce400
 */
public class MockQPalXSubscriptionBuilder {


    public static QPalXSubscription buildMockTestQPalXSubscription(final QPalXCountry subscriptionCountry) {
        QPalXSubscription qPalXSubscription = QPalXSubscription.builder()
                .subscriptionQPalXCountry(subscriptionCountry)
                .subscriptionName("Test Susbscription For Five Days")
                .subscriptionCost(10.00)
                .subscriptionType(SubscriptionTypeE.FIVE_DAYS)
                .build();
        return qPalXSubscription;
    }
}
