package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingCompuationResult;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;
import java.util.Optional;

/**
 * Defines service functionality for logic around QPalxUser Adaptive proficiency ranking
 *
 * @author manyce400
 */
public interface IAdaptiveProficiencyRankingAnalyticsService {


    /**
     * Calculate all AdaptiveProficiencyRanking for Student across all E-Learning curriculum.
     *
     * @param qPalXUser
     */
    public List<AdaptiveProficiencyRanking> calculateAdaptiveProficiencyRanking(QPalXUser qPalXUser);


    public ProficiencyRankingCompuationResult calculateStudentProficiencyWithProgress(QPalXUser qPalXUser, StudentOverallProgressStatistics studentOverallProgressStatistics);


    /**
     * Calculate an AdaptiveProficiencyRanking for Student User across a given E-Learning curriculum.
     *
     * Logic uses only the AdaptiveLearningExperience's instances that match the E-Learning curriculum in order to calcuate
     * new AdaptiveProficiencyRanking
     *
     * @param qPalXUser
     * @param eLearningCurriculum
     */
    public Optional<Double> calculateStudentAverageCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum);


}

