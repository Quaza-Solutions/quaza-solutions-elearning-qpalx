package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.Optional;

/**
 *
 * @author manyce400
 */
public interface IAdaptiveLearningProfileService {


    public void save(AdaptiveLearningProfile adaptiveLearningProfile);

    public void delete(AdaptiveLearningProfile adaptiveLearningProfile);

    /**
     *
     * @param qPalXUser
     * @return
     */
    public Optional<AdaptiveLearningProfile> findAdaptiveLearningProfileForQPalxUser(QPalXUser qPalXUser);

    /**
     * Build and create a new AdaptiveLearningProfile for the qPalxUser passed in as argument.
     *
     * This should only be created IF the user currently has no AdaptiveLearningProfile.
     *
     * @param qPalXUser
     * @return Optional<AdaptiveLearningProfile>
     */
    public Optional<AdaptiveLearningProfile> buildNewAdaptiveLearningProfileForQPalxUser(QPalXUser qPalXUser);


}
