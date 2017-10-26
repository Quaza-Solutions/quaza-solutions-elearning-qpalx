package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveProficiencyRankingQuizStatisticService {


    // Find all AdaptiveLessonQuizStatistics for all Lessons in this ELearningCurriculum for student that matches the proficiencyRankingScaleE passed
    public List<AdaptiveLessonQuizStatistics> findQuizStatisticsInCurriculumWithProficiencyRanking(ELearningCurriculum eLearningCurriculum, ProficiencyRankingScaleE proficiencyRankingScaleE, QPalXUser student);
}
