package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionValidationResult;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IQPalxSubscriptionService {



    public QPalXSubscription findQPalXSubscriptionByID(Long id);

    public List<QPalXSubscription> findAllQPalXSubscriptionsByCountryCode(String countryCode);

    public SubscriptionValidationResult validateUserQPalXSubscription(final QPalXUser qPalXUser);

    public Optional<StudentSubscriptionProfile> findActiveUserSubscriptionProfile(final QPalXUser qPalXUser);

    public List<QPalXSubscription> findAllSubscriptions();

    /**
     * Given a QPalXSubscription, assuming that subscription starts from current date calculate and return
     * the expiration date for the given subscription.
     *
     * @param subscription
     * @return DateTime when Given subscription will expire from Today.
     */
    public DateTime calculateSubscriptionExpiryDateFromToday(QPalXSubscription subscription);


    public int calculateNumberOfDaysTillExpiration(StudentSubscriptionProfile studentSubscriptionProfile);

}
