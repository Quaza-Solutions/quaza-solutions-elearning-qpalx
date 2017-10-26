package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(AdaptiveProficiencyRankingQuizStatisticService.BEAN_NAME)
public class AdaptiveProficiencyRankingQuizStatisticService implements IAdaptiveProficiencyRankingQuizStatisticService {



    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveProficiencyRankingQuizStatisticService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveProficiencyRankingQuizStatisticService.class);


    @Override
    public List<AdaptiveLessonQuizStatistics> findQuizStatisticsInCurriculumWithProficiencyRanking(ELearningCurriculum eLearningCurriculum, ProficiencyRankingScaleE proficiencyRankingScaleE, QPalXUser student) {
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(proficiencyRankingScaleE, "proficiencyRankingScaleE cannot be null");
        Assert.notNull(student, "student cannot be null");

        LOGGER.info("Finding information on all quizzes in curriculum: {}", eLearningCurriculum.getCurriculumName());

        return null;
    }
}
