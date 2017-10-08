package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveLearningQuizStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.AdaptiveLearningExperienceService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.Precision;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author manyce400
 */
@Service(MicroLessonPerformanceMonitorService.BEAN_NAME)
public class MicroLessonPerformanceMonitorService implements IMicroLessonPerformanceMonitorService {



    @Autowired
    @Qualifier(AdaptiveLearningQuizService.BEAN_NAME)
    private IAdaptiveLearningQuizService iAdaptiveLearningQuizService;

    @Autowired
    @Qualifier(AdaptiveLearningExperienceService.SPRING_BEAN_NAME)
    private IAdaptiveLearningExperienceService iAdaptiveLearningExperienceService;

    @Autowired
    @Qualifier(AdaptiveLearningQuizStatisticsService.BEAN_NAME)
    private IAdaptiveLearningQuizStatisticsService iAdaptiveLearningQuizStatisticsService;


    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson.MicroLessonPerformanceMonitorService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MicroLessonPerformanceMonitorService.class);


    @Override
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser student, QPalXEMicroLesson microLesson) {
        Assert.notNull(student, "student cannot be null");
        Assert.notNull(microLesson, "microLesson cannot be null");

        LOGGER.debug("Finding current performance information for Student: {} in MiroLesson: {}", student.getEmail(), microLesson.getMicroLessonName());

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = iAdaptiveLearningQuizStatisticsService.findMicroLessonStudentQuizStatistics(student, microLesson.getId());

        if(adaptiveLessonQuizStatistics != null || adaptiveLessonQuizStatistics.size() > 0) {
            double mlQuizStatisticsAvg = getQuizStatisticsAverage(adaptiveLessonQuizStatistics);

            //  Calculate ProficiencyScoreRange using the newly calculated ML Quiz averages for this student
            Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(mlQuizStatisticsAvg);
            ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE.get()).get();

            AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                    .proficiencyRankingScaleE(proficiencyRankingScaleE)
                    .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.ON_DEMAND)
                    .proficiencyRankingEffectiveDateTime(DateTime.now())
                    .build();

            return adaptiveProficiencyRanking;
        }

        // There are on quiz statistics to determine an adequate ranking so default to worst possible ranking
        LOGGER.info("No AdaptiveLessonQuizStatistics were found for student, defaulting MicroLesson proficiency ranking to worst possible setting...");
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingScaleE(ProficiencyRankingScaleE.ONE)
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.ON_DEMAND)
                .proficiencyRankingEffectiveDateTime(DateTime.now())
                .build();

        return adaptiveProficiencyRanking;
    }

    @Override
    public List<AdaptiveLearningQuiz> findPrerequisiteIncompleteQuizzes(QPalXUser student, QPalXEMicroLesson microLesson, AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(student, "student cannot be null");
        Assert.notNull(microLesson, "microLesson cannot be null");
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");

        LOGGER.debug("Finding all incomplete prerequisite quizzes in microLesson: {} for quiz: {}", microLesson.getId(), adaptiveLearningQuiz.getId());

        // Find all quizzes in this microlesson
        List<AdaptiveLearningQuiz> mlAdaptiveQuizzes = iAdaptiveLearningQuizService.findQuizzesForMicroLesson(microLesson);

        // Using order of creation logic, find all Prerequisite quizzes as quizzes with ID's less than the passed in quiz
        List<AdaptiveLearningQuiz> preRequisiteQuizzes = new ArrayList<>();
        for(AdaptiveLearningQuiz candidateQuiz : mlAdaptiveQuizzes) {
            if(candidateQuiz.getId() < adaptiveLearningQuiz.getId()) {
                preRequisiteQuizzes.add(candidateQuiz);
            }
        }


        // Find all the Quizzes missing a Learning Experience, meaning Student hasn't attempted these
        List<AdaptiveLearningQuiz> quizzesWithNoStudentAttempt = findAllQuizzesWitNoStudentAttempt(student, preRequisiteQuizzes);
        Collections.sort(quizzesWithNoStudentAttempt, new QuizPrerequisiteComparator());
        return quizzesWithNoStudentAttempt;
    }

    protected double getQuizStatisticsAverage(List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics) {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        for(AdaptiveLessonQuizStatistics quizStatistic : adaptiveLessonQuizStatistics) {
            summaryStatistics.addValue(quizStatistic.getProficiencyScore());
        }

        double averageProficiencyScore = summaryStatistics.getMean();
        return Precision.round(averageProficiencyScore, 2);
    }

    protected List<AdaptiveLearningQuiz> findAllQuizzesWitNoStudentAttempt(QPalXUser student, List<AdaptiveLearningQuiz> preRequisiteQuizzes) {
        List<AdaptiveLearningQuiz> filterList = new ArrayList<>(preRequisiteQuizzes);

        for (AdaptiveLearningQuiz preRequisiteQuiz : preRequisiteQuizzes) {
            // Find all AdaptiveLearning Quiz Experiences IF present
            List<AdaptiveLearningExperience> adaptiveLearningExperiences = iAdaptiveLearningExperienceService.findAllWithScorableActivityID(preRequisiteQuiz.getId(), student);
            if(CollectionUtils.isEmpty(adaptiveLearningExperiences)) {
                filterList.remove(preRequisiteQuiz);
            }
        }

        return filterList;
    }


    private static final class QuizPrerequisiteComparator implements Comparator<AdaptiveLearningQuiz> {
        @Override
        public int compare(AdaptiveLearningQuiz o1, AdaptiveLearningQuiz o2) {
            if(o1.getId().longValue() < o2.getId().longValue()) {
                return 1;
            }
            return -1;
        }
    }

}
