package com.quaza.solutions.qpalx.elearning.service.mock;



/**
 * Extension on top of QPalXUserBuilder Fluent API to create a mock builder representation only for Unit Testing purposes.
 *
 * Out of the box this builder will initialize some defaults which can easily be overriden based on the needs of the Unit test.
 *
 * @author manyce400
 */
public class MockQPalXUserBuilder {


    protected static final String[] TEST_TUTORIAL_LEVELS = new String[]{"Proficient", "Expert"};


//    public static QPalXUserBuilder buildMockQPalXUserBuilder(final QPalXMunicipality municipality) {
//        QPalXUserBuilder qPalXUserBuilder = new QPalXUserBuilder();
//
//        // Initialize defaults
//        qPalXUserBuilder
//                .addSuccessID("GH-ACC-839393")
//                .addFirstName("Manny")
//                .addLastName("Jones")
//                .addEmail("mjones@gmail.com")
//                .addPassword("fallon12")
//                .addQPalXTutorialLevel(buildDefaultQPalXTutorialLevel())
//                .addMunicipality(municipality)
//                .addQPalxUserSexE(QPalxUserSexE.Male)
//                .addQPalxUserTypeE(QPalxUserTypeE.STUDENT)
//
//                .addAccountLockedStatus(false) // By default always create a new account unlocked
//                .addResetPasswordStatus(false)
//                .addLastLoginDate(new DateTime());
//        return qPalXUserBuilder;
//    }
//
//
//    private static QPalXTutorialLevel buildDefaultQPalXTutorialLevel() {
//        QPalXTutorialLevel tutorialLevel = new QPalXTutorialLevel();
//        tutorialLevel.setTutorialLevel(TEST_TUTORIAL_LEVELS[0]);
//        return tutorialLevel;
//    }
//
//
//    public static void assignActiveUserSubscriptionProfile(final QPalXUserBuilder qPalXUserBuilder) {
//        UserSubscriptionProfileBuilder userSubscriptionProfileBuilder = new UserSubscriptionProfileBuilder()
//                .addSubscriptionStatusE(SubscriptionStatusE.ACTIVE)
//                .addSubscriptionPurchasedDate(new DateTime())
//                .addQPalXSubscription(getMockQPalXSubscription(qPalXUserBuilder))
//                .addQPalXUser(qPalXUserBuilder.get());
//
//        // add to the builder
//        qPalXUserBuilder.addUserSubscriptionProfile(userSubscriptionProfileBuilder.get());
//    }
//
//    private static QPalXSubscription getMockQPalXSubscription(final QPalXUserBuilder qPalXUserBuilder) {
//        QPalXSubscriptionBuilder qPalXSubscriptionBuilder = new QPalXSubscriptionBuilder()
//                .addSubscriptionQPalXCountry(qPalXUserBuilder.get().getQPalXMunicipality().getQPalXCountry())
//                .addSubscriptionType(SubscriptionTypeE.FIVE_DAYS)
//                .addSubscriptionName("Test Susbscription For Five Days")
//                .addSubscriptionCost(10.00);
//
//        // assign subscription to builder
//        return qPalXSubscriptionBuilder.get();
//    }
}
