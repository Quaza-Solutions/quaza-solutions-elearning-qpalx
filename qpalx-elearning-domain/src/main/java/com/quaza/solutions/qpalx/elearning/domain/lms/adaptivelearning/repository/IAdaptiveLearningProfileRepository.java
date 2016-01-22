package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author manyce400
 */
public interface IAdaptiveLearningProfileRepository extends CrudRepository<AdaptiveLearningProfile, Long> {


    @Query("Select adaptiveLearningProfile From AdaptiveLearningProfile adaptiveLearningProfile JOIN FETCH adaptiveLearningProfile.qpalxUser = ?1")
    public AdaptiveLearningProfile findAdaptiveLearningProfileForQPalxUser(final QPalXUser qPalXUser);
}
