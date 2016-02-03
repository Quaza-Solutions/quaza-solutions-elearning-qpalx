package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;

/**
 * Defines service functionality for logic around QPalxUser Adaptive proficiency ranking
 *
 * @author manyce400
 */
public interface IAdaptiveProficiencyRankingAnalyticsService {


    /**
     * Calculate an AdaptiveProficiencyRanking for User's AdaptiveLearningProfile across a given E-Learning curriculum based on the Set of
     * AdaptiveLearningExperience passed in as argument.
     *
     * Logic uses only the AdaptiveLearningExperience's instances that match the E-Learning curriculum in order to calcuate
     * new AdaptiveProficiencyRanking
     *
     * @param adaptiveLearningProfile
     * @param eLearningCurriculum
     */
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(final AdaptiveLearningProfile adaptiveLearningProfile, final ELearningCurriculum eLearningCurriculum);


}

