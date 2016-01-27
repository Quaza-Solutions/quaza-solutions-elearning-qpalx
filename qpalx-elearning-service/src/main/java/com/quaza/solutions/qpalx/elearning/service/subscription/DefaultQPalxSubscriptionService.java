package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IUserSubscriptionProfileRepository;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionValidationResult;
import com.quaza.solutions.qpalx.elearning.domain.subscription.repository.IQPalXSubscriptionRepository;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by manyce400 on 11/27/15.
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
public class DefaultQPalxSubscriptionService implements IQPalxSubscriptionService {



    @Autowired
    private IQPalXSubscriptionRepository iqPalXSubscriptionRepository;

    @Autowired
    private IUserSubscriptionProfileRepository iUserSubscriptionProfileRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalxSubscriptionService.class);

    @Override
    public QPalXSubscription findQPalXSubscriptionByID(Long id) {
        Assert.notNull(id, "QPalXSubscription id cannot be null");
        LOGGER.info("finding QPalXSubscription with id:> {}", id);
        return iqPalXSubscriptionRepository.findOne(id);
    }

    @Override
    public List<QPalXSubscription> findAllQPalXSubscriptionsByCountryCode(String countryCode) {
        Assert.notNull(countryCode, "countryCode cannot be null");
        LOGGER.info("finding all QPalx subscription by Country code:> {}", countryCode);
        List<QPalXSubscription> subscriptions = iqPalXSubscriptionRepository.findAllQPalXSubscriptionsByCountryCode(countryCode);
        return subscriptions;
    }

    @Override
    public SubscriptionValidationResult validateUserQPalXSubscription(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("validating QPalX subscription for qPalXUser: {} ...", qPalXUser.getEmail());

        // Find the current active user subscription profile
        List<UserSubscriptionProfile> userSubscriptionProfiles = iUserSubscriptionProfileRepository.findUserSubcriptionProfileInfo(qPalXUser);
        Optional<UserSubscriptionProfile> activeUserSubscriptionProfile = findActiveUserSubscriptionProfile(qPalXUser);

        if(activeUserSubscriptionProfile.isPresent()) {
            return getActiveSubscriptionValidationResult(activeUserSubscriptionProfile.get());
        } else {
            if(userSubscriptionProfiles == null || userSubscriptionProfiles.size() == 0) {
                // No user subscriptions was found for this user, return invalid results. using the most recent subscription profile or none if user never bought a subscription
                LOGGER.info("qPalXUser: {} has never purchased a QPalX subscription: {}", qPalXUser.getEmail());
                return getInvalidSubscriptionValidationResult();
            } else {
                // Sort the userSubscriptionProfiles by purchased date so we can get the most recent purchased subscription which has expired
                return getExpiredSubscriptionValidationResult(userSubscriptionProfiles);
            }
        }
    }

    @Override
    public Optional<UserSubscriptionProfile> findActiveUserSubscriptionProfile(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding currently active UserSubscription profile for qPalXUser:> {}", qPalXUser);

        // Find the current active user subscription profile
        List<UserSubscriptionProfile> userSubscriptionProfiles = iUserSubscriptionProfileRepository.findUserSubcriptionProfileInfo(qPalXUser);
        Optional<UserSubscriptionProfile> activeUserSubscriptionProfile = userSubscriptionProfiles.stream()
                .filter((userSubscriptionProfile) -> isActiveUserSubscription(userSubscriptionProfile))
                .findFirst();
        return activeUserSubscriptionProfile;
    }

    @Override
    public DateTime calculateSubscriptionExpiryDateFromToday(QPalXSubscription subscription) {
        Assert.notNull(subscription, "subscription cannot be null");
        LOGGER.info("Calculating subscription expiry date from Today for subscription:> {}", subscription);

        DateTime today = new DateTime();
        int numberOfSubscriptionDays = subscription.getSubscriptionType().getNumberOfDays();
        DateTime subscriptionExpiryDate = today.plusDays(numberOfSubscriptionDays);
        return subscriptionExpiryDate;
    }

    boolean isActiveUserSubscription(UserSubscriptionProfile userSubscriptionProfile) {
        DateTime now = new DateTime();
        QPalXSubscription subscription = userSubscriptionProfile.getqPalXSubscription();
        DateTime purchaseDate = userSubscriptionProfile.getSubscriptionPurchasedDate();

        LOGGER.info("purchaseDate=" + purchaseDate + " now = " + now);
        int numberOfDays = Days.daysBetween(purchaseDate, now).getDays();
        LOGGER.info("numberOfDays since subscription was purchased = " + numberOfDays);
        if (numberOfDays > subscription.getSubscriptionType().getNumberOfDays()) {
            LOGGER.debug("UserSubscriptionProfile: {} is not active", userSubscriptionProfile);
            return false;
        } else {
            LOGGER.debug("UserSubscriptionProfile: {} is currently active", userSubscriptionProfile);
            return true;
        }
    }

    private SubscriptionValidationResult getActiveSubscriptionValidationResult(UserSubscriptionProfile activeUserSubscriptionProfile) {
        SubscriptionValidationResult validationResult = new SubscriptionValidationResult(
                activeUserSubscriptionProfile.getSubscriptionPurchasedDate(),
                activeUserSubscriptionProfile.getSubscriptionExpirationDate(),
                activeUserSubscriptionProfile.getqPalXSubscription().getSubscriptionType(),
                SubscriptionStatusE.ACTIVE
        );

        return validationResult;
    }

    private SubscriptionValidationResult getInvalidSubscriptionValidationResult() {
        LOGGER.info("Returning an invalid SubscriptionValidationResult");
        SubscriptionValidationResult validationResult = new SubscriptionValidationResult(
                null,
                null,
                null,
                SubscriptionStatusE.INVALID
        );

        return validationResult;
    }

    private SubscriptionValidationResult getExpiredSubscriptionValidationResult(List<UserSubscriptionProfile> userSubscriptionProfiles) {
        // Sort the userSubscriptionProfiles by purchased date so we can get the most recent purchased subscription which has expired
        Collections.sort(userSubscriptionProfiles, (profile1, profile2) -> profile2.getSubscriptionPurchasedDate().compareTo(profile1.getSubscriptionPurchasedDate()));
        UserSubscriptionProfile mostRecentPurchasedSubscriptionProfile = userSubscriptionProfiles.get(0);

        LOGGER.info("Returning an expired SubscriptionValidationResult from mostRecentPurchasedSubscriptionProfile:> {}", mostRecentPurchasedSubscriptionProfile);
        SubscriptionValidationResult validationResult = new SubscriptionValidationResult(
                mostRecentPurchasedSubscriptionProfile.getSubscriptionPurchasedDate(),
                mostRecentPurchasedSubscriptionProfile.getSubscriptionExpirationDate(),
                mostRecentPurchasedSubscriptionProfile.getqPalXSubscription().getSubscriptionType(),
                SubscriptionStatusE.EXPIRED
        );

        return validationResult;
    }
}