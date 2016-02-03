package com.quaza.solutions.qpalx.elearning.service.mock;


import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserSexE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfileBuilder;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionTypeE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import org.joda.time.DateTime;

/**
 * Extension on top of QPalXUserBuilder Fluent API to create a mock builder representation only for Unit Testing purposes.
 *
 * Out of the box this builder will initialize some defaults which can easily be overriden based on the needs of the Unit test.
 *
 * @author manyce400
 */
public class MockQPalXUserBuilder {


    protected static final String[] TEST_TUTORIAL_LEVELS = new String[]{"Proficient", "Expert"};


    public static QPalXUser buildMockQPalXUserBuilder(final QPalXMunicipality municipality) {
        QPalXUser qPalXUser = QPalXUser.builder()
                .sccessID("GH-ACC-839393")
                .firstName("Manny")
                .lastName("Jones")
                .email("mjones@gmail.com")
                .password("fallon12")
                .qPalXTutorialLevel(buildDefaultQPalXTutorialLevel())
                .municipality(municipality)
                .qPalxUserSexE(QPalxUserSexE.Male)
                .qPalxUserTypeE(QPalxUserTypeE.STUDENT)
                .accountLockedStatus(false) // By default always create a new account unlocked
                .resetPasswordStatus(false)
                .lastLoginDate(new DateTime())
                .build();
        return qPalXUser;
    }


    private static QPalXTutorialLevel buildDefaultQPalXTutorialLevel() {
        QPalXTutorialLevel tutorialLevel = new QPalXTutorialLevel();
        tutorialLevel.setTutorialLevel(TEST_TUTORIAL_LEVELS[0]);
        return tutorialLevel;
    }


    public static void assignActiveUserSubscriptionProfile(final QPalXUser qPalXUser) {
        UserSubscriptionProfileBuilder userSubscriptionProfileBuilder = new UserSubscriptionProfileBuilder()
                .addSubscriptionStatusE(SubscriptionStatusE.ACTIVE)
                .addSubscriptionPurchasedDate(new DateTime())
                .addQPalXSubscription(getMockQPalXSubscription(qPalXUser))
                .addQPalXUser(qPalXUser);

        // add to the builder
        qPalXUser.addUserSubscriptionProfile(userSubscriptionProfileBuilder.get());
    }

    private static QPalXSubscription getMockQPalXSubscription(final QPalXUser qPalXUser) {
        QPalXSubscription qPalXSubscription = QPalXSubscription.builder()
                .subscriptionQPalXCountry(qPalXUser.getQPalXMunicipality().getQPalXCountry())
                .subscriptionType(SubscriptionTypeE.FIVE_DAYS)
                .subscriptionName("Test Susbscription For Five Days")
                .subscriptionCost(10.00)
                .build();

        // assign subscription to builder
        return qPalXSubscription;
    }
}
