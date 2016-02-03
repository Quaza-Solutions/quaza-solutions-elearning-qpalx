package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;

import java.util.Optional;

/**
 *
 * @author manyce400
 */
public interface IQPalXUserSubscriptionService {


    /**
     * Create a brand new QPalXUser with a valid active Subscription from today given QPalXUser Value Object.
     *
     * This represents setting up a brand new user for the first time in the system.
     *
     * @param iqPalXUserVO
     * @return Optional object, empty if implementation is unable to create QPalXUser with registration
     */
    public Optional<QPalXUser> createNewQPalXUserWithTutorialSubscription(IQPalXUserVO iqPalXUserVO);

    /**
     * Creates a brand new UserSubscriptionProfile given a subscriptionID for a QPalXUser.
     *
     * @param subscriptionID
     * @param qPalXUser
     * @return
     */
    public Optional<UserSubscriptionProfile> addQPalXUserTutorialSubscriptionProfile(Long subscriptionID, QPalXUser qPalXUser);

}

