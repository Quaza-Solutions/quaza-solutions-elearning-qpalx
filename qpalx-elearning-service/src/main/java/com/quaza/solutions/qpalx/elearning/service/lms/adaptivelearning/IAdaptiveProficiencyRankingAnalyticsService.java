package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * Defines service functionality for logic around QPalxUser Adaptive proficiency ranking
 *
 * @author manyce400
 */
public interface IAdaptiveProficiencyRankingAnalyticsService {


    /**
     * Calculate an AdaptiveProficiencyRanking for Student User across a given E-Learning curriculum.
     *
     * Logic uses only the AdaptiveLearningExperience's instances that match the E-Learning curriculum in order to calcuate
     * new AdaptiveProficiencyRanking
     *
     * @param eLearningCurriculum
     */
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum);


}

