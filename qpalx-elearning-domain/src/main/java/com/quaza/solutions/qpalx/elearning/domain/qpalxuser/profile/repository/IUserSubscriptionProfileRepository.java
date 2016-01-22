package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository;


import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by manyce400 on 11/26/15.
 */
public interface IUserSubscriptionProfileRepository extends CrudRepository<UserSubscriptionProfile, Long> {


    @Query("Select subscriptionProfiles From UserSubscriptionProfile subscriptionProfiles JOIN FETCH subscriptionProfiles.qPalXSubscription Where subscriptionProfiles.qpalxUser = ?1")
    public List<UserSubscriptionProfile> findUserSubcriptionProfileInfo(QPalXUser qPalXUser);
}
