package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import org.joda.time.DateTime;


/**
 * Employing DSL's and Fluent builder patterns to create a UserSubscriptionProfile Builder object.
 *
 * Unlike traditional builders you can keep modifying properties and call get any time to retrieve the actual
 * object.
 *
 * @author manyce400
 */
public class UserSubscriptionProfileBuilder {


    private final UserSubscriptionProfile userSubscriptionProfile;

    public UserSubscriptionProfileBuilder() {
        this.userSubscriptionProfile = new UserSubscriptionProfile();
    }

    public UserSubscriptionProfileBuilder addQPalXUser(final QPalXUser qPalXUser) {
        userSubscriptionProfile.setQpalxUser(qPalXUser);
        return this;
    }

    public UserSubscriptionProfileBuilder addQPalXSubscription(final QPalXSubscription qPalXSubscription) {
        userSubscriptionProfile.setqPalXSubscription(qPalXSubscription);
        return this;
    }

    public UserSubscriptionProfileBuilder addQPalXEducationalInstitution(final QPalXEducationalInstitution qPalXEducationalInstitution) {
        userSubscriptionProfile.setEducationalInstitution(qPalXEducationalInstitution);
        return this;
    }

    public UserSubscriptionProfileBuilder addSubscriptionStatusE(final SubscriptionStatusE subscriptionStatusE) {
        userSubscriptionProfile.setSubscriptionStatusE(subscriptionStatusE);
        return this;
    }

    public UserSubscriptionProfileBuilder addSubscriptionPurchasedDate(final DateTime subscriptionPurchasedDate) {
        userSubscriptionProfile.setSubscriptionPurchasedDate(subscriptionPurchasedDate);
        return this;
    }

    public UserSubscriptionProfileBuilder addSubscriptionExpirationDate(final DateTime subscriptionExpirationDate) {
        userSubscriptionProfile.setSubscriptionExpirationDate(subscriptionExpirationDate);
        return this;
    }

    public UserSubscriptionProfile get() {
        return userSubscriptionProfile;
    }

}
