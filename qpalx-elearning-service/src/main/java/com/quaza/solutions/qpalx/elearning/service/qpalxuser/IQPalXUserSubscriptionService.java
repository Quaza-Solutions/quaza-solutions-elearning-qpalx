package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;

import java.util.Optional;

/**
 *
 * @author manyce400
 */
public interface IQPalXUserSubscriptionService {


    // Update QPalXUser from iqPalXUserVO passed in as argument
    public void updateQPalXUserInfo(QPalXUser qPalXUser, IQPalXUserVO iqPalXUserVO);

    public Optional<QPalXUser> createNewQPalXUser(IQPalXUserVO iqPalXUserVO, QPalxUserTypeE qPalxUserTypeE);

    // Create a brand new QPalXUser with a valid active Subscription from today given QPalXUser Value Object.
    public Optional<QPalXUser> createNewQPalXUserWithTutorialSubscription(IQPalXUserVO iqPalXUserVO);

    // Renew QPalX Student user's subscription with the Subscription passed in as argument. Returns a non empty StudentSubscriptionProfile if sucessfull
    public Optional<StudentSubscriptionProfile> renewQPalXUserSubscription(String email, QPalXSubscription subscription);

    // Renew QPalX Student user's subscription with the Subscription passed in as argument. Returns a non empty StudentSubscriptionProfile if sucessfull
    public Optional<StudentSubscriptionProfile> renewQPalXUserSubscription(QPalXUser qPalXUser, QPalXSubscription subscription);

    // Creates a brand new StudentSubscriptionProfile given a subscriptionID for a QPalXUser.
    public Optional<StudentSubscriptionProfile> addQPalXUserTutorialSubscriptionProfile(Long subscriptionID, QPalXUser qPalXUser);

}

