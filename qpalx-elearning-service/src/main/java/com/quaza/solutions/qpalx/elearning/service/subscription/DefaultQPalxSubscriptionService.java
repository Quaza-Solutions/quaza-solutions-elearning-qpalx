package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IStudentSubscriptionProfileRepository;
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
    private IStudentSubscriptionProfileRepository iStudentSubscriptionProfileRepository;

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
        List<StudentSubscriptionProfile> studentSubscriptionProfiles = iStudentSubscriptionProfileRepository.findUserSubcriptionProfileInfo(qPalXUser);
        Optional<StudentSubscriptionProfile> activeUserSubscriptionProfile = findActiveUserSubscriptionProfile(qPalXUser);

        if(activeUserSubscriptionProfile.isPresent()) {
            return getActiveSubscriptionValidationResult(activeUserSubscriptionProfile.get());
        } else {
            if(studentSubscriptionProfiles == null || studentSubscriptionProfiles.size() == 0) {
                // No user subscriptions was found for this user, return invalid results. using the most recent subscription profile or none if user never bought a subscription
                LOGGER.info("qPalXUser: {} has never purchased a QPalX subscription: {}", qPalXUser.getEmail());
                return getInvalidSubscriptionValidationResult();
            } else {
                // Sort the studentSubscriptionProfiles by purchased date so we can get the most recent purchased subscription which has expired
                return getExpiredSubscriptionValidationResult(studentSubscriptionProfiles);
            }
        }
    }

    @Override
    public Optional<StudentSubscriptionProfile> findActiveUserSubscriptionProfile(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding currently active UserSubscription profile for qPalXUser:> {}", qPalXUser);

        // Find the current active user subscription profile
        List<StudentSubscriptionProfile> studentSubscriptionProfiles = iStudentSubscriptionProfileRepository.findUserSubcriptionProfileInfo(qPalXUser);
        Optional<StudentSubscriptionProfile> activeUserSubscriptionProfile = studentSubscriptionProfiles.stream()
                .filter((userSubscriptionProfile) -> isActiveUserSubscription(userSubscriptionProfile))
                .findFirst();
        return activeUserSubscriptionProfile;
    }

    @Override
    public List<QPalXSubscription> findAllSubscriptions() {
        Iterable<QPalXSubscription> iterableSubscription = iqPalXSubscriptionRepository.findAll();
        return ImmutableList.copyOf(iterableSubscription);
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

    @Override
    public int calculateNumberOfDaysTillExpiration(StudentSubscriptionProfile studentSubscriptionProfile) {
        Assert.notNull(studentSubscriptionProfile, "studentSubscriptionProfile cannot be null");

        DateTime now = new DateTime();
        QPalXSubscription subscription = studentSubscriptionProfile.getqPalXSubscription();

        // Calculate the number of days left between time purchased and the date it should expire.
        DateTime expiryDate = calculateSubscriptionExpiryDateFromToday(subscription);
        int numberOfDaysTillExpiry = Days.daysBetween(now, expiryDate).getDays();
        return numberOfDaysTillExpiry;
    }

    boolean isActiveUserSubscription(StudentSubscriptionProfile studentSubscriptionProfile) {
        DateTime now = new DateTime();
        QPalXSubscription subscription = studentSubscriptionProfile.getqPalXSubscription();
        DateTime purchaseDate = studentSubscriptionProfile.getSubscriptionPurchasedDate();

        LOGGER.info("purchaseDate=" + purchaseDate + " now = " + now);
        int numberOfDays = Days.daysBetween(purchaseDate, now).getDays();
        LOGGER.info("numberOfDays since subscription was purchased = " + numberOfDays);
        if (numberOfDays > subscription.getSubscriptionType().getNumberOfDays()) {
            LOGGER.debug("StudentSubscriptionProfile: {} is not active", studentSubscriptionProfile);
            return false;
        } else {
            LOGGER.debug("StudentSubscriptionProfile: {} is currently active", studentSubscriptionProfile);
            return true;
        }
    }

    private SubscriptionValidationResult getActiveSubscriptionValidationResult(StudentSubscriptionProfile activeStudentSubscriptionProfile) {
        SubscriptionValidationResult validationResult = new SubscriptionValidationResult(
                activeStudentSubscriptionProfile.getSubscriptionPurchasedDate(),
                activeStudentSubscriptionProfile.getSubscriptionExpirationDate(),
                activeStudentSubscriptionProfile.getqPalXSubscription().getSubscriptionType(),
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

    private SubscriptionValidationResult getExpiredSubscriptionValidationResult(List<StudentSubscriptionProfile> studentSubscriptionProfiles) {
        // Sort the studentSubscriptionProfiles by purchased date so we can get the most recent purchased subscription which has expired
        Collections.sort(studentSubscriptionProfiles, (profile1, profile2) -> profile2.getSubscriptionPurchasedDate().compareTo(profile1.getSubscriptionPurchasedDate()));
        StudentSubscriptionProfile mostRecentPurchasedSubscriptionProfile = studentSubscriptionProfiles.get(0);

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
