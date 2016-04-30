package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import org.joda.time.DateTime;


/**
 * Employing DSL's and Fluent builder patterns to create a StudentSubscriptionProfile Builder object.
 *
 * Unlike traditional builders you can keep modifying properties and call get any time to retrieve the actual
 * object.
 *
 * @author manyce400
 */
public class StudentSubscriptionProfileBuilder {


    private final StudentSubscriptionProfile studentSubscriptionProfile;

    public StudentSubscriptionProfileBuilder() {
        this.studentSubscriptionProfile = new StudentSubscriptionProfile();
    }

    public StudentSubscriptionProfileBuilder addQPalXUser(final QPalXUser qPalXUser) {
        studentSubscriptionProfile.setQpalxUser(qPalXUser);
        return this;
    }

    public StudentSubscriptionProfileBuilder addQPalXSubscription(final QPalXSubscription qPalXSubscription) {
        studentSubscriptionProfile.setqPalXSubscription(qPalXSubscription);
        return this;
    }

    public StudentSubscriptionProfileBuilder addSubscriptionStatusE(final SubscriptionStatusE subscriptionStatusE) {
        studentSubscriptionProfile.setSubscriptionStatusE(subscriptionStatusE);
        return this;
    }

    public StudentSubscriptionProfileBuilder addSubscriptionPurchasedDate(final DateTime subscriptionPurchasedDate) {
        studentSubscriptionProfile.setSubscriptionPurchasedDate(subscriptionPurchasedDate);
        return this;
    }

    public StudentSubscriptionProfileBuilder addSubscriptionExpirationDate(final DateTime subscriptionExpirationDate) {
        studentSubscriptionProfile.setSubscriptionExpirationDate(subscriptionExpirationDate);
        return this;
    }

    public StudentSubscriptionProfile get() {
        return studentSubscriptionProfile;
    }

}
