package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository;


import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IStudentSubscriptionProfileRepository extends CrudRepository<StudentSubscriptionProfile, Long> {


    @Query("Select subscriptionProfiles From StudentSubscriptionProfile subscriptionProfiles JOIN FETCH subscriptionProfiles.qPalXSubscription Where subscriptionProfiles.qpalxUser = ?1")
    public List<StudentSubscriptionProfile> findUserSubcriptionProfileInfo(QPalXUser qPalXUser);
}
