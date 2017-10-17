package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
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
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser student, ELearningCourse eLearningCourse) {
        Assert.notNull(student, "student cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.debug("Finding current performance information for Student: {} in ELearningCourse: {}", student.getEmail(), eLearningCourse.getCourseName());

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(student, eLearningCourse.getId());
        return caclulateAdaptiveProficiencyRanking(adaptiveLessonQuizStatistics);
    }

    @Override
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser student, QPalXEMicroLesson microLesson) {
        Assert.notNull(student, "student cannot be null");
        Assert.notNull(microLesson, "microLesson cannot be null");

        LOGGER.debug("Finding current performance information for Student: {} in MiroLesson: {}", student.getEmail(), microLesson.getMicroLessonName());

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = iAdaptiveLearningQuizStatisticsService.findMicroLessonStudentQuizStatistics(student, microLesson.getId());
        return caclulateAdaptiveProficiencyRanking(adaptiveLessonQuizStatistics);
    }

    @Override
    public List<AdaptiveLearningQuiz> findPrerequisiteIncompleteQuizzes(QPalXUser student, QPalXEMicroLesson microLesson, AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(student, "student cannot be null");
        Assert.notNull(microLesson, "microLesson cannot be null");
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");

        LOGGER.debug("Finding all incomplete prerequisite quizzes in microLesson: {} for quiz: {}", microLesson.getId(), adaptiveLearningQuiz.getId());

        // Find all quizzes in this microlesson
        List<AdaptiveLearningQuiz> mlAdaptiveQuizzes = iAdaptiveLearningQuizService.findQuizzesForMicroLesson(microLesson);
        LOGGER.info("Found quizzes for MicroLesson: {}", mlAdaptiveQuizzes.size());

        // Using order of creation logic, find all Prerequisite quizzes as quizzes with ID's less than the passed in quiz
        List<AdaptiveLearningQuiz> preRequisiteQuizzes = new ArrayList<>();
        for(AdaptiveLearningQuiz candidateQuiz : mlAdaptiveQuizzes) {
            if(candidateQuiz.getId() < adaptiveLearningQuiz.getId()) {
                LOGGER.info("Adding quiz with ID: {} to prerequisite list", candidateQuiz.getId());
                preRequisiteQuizzes.add(candidateQuiz);
            }
        }


        // Find all the Quizzes missing a Learning Experience, meaning Student hasn't attempted these
        List<AdaptiveLearningQuiz> quizzesWithNoStudentAttempt = findAllQuizzesWitNoStudentAttempt(student, preRequisiteQuizzes);
        Collections.sort(quizzesWithNoStudentAttempt, new QuizPrerequisiteComparator());
        return quizzesWithNoStudentAttempt;
    }


    protected AdaptiveProficiencyRanking caclulateAdaptiveProficiencyRanking(List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics) {
        if(adaptiveLessonQuizStatistics != null && adaptiveLessonQuizStatistics.size() > 0) {
            double mlQuizStatisticsAvg = getQuizStatisticsAverage(adaptiveLessonQuizStatistics);

            //  Calculate ProficiencyScoreRange using the newly calculated ML Quiz averages for this student
            Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(mlQuizStatisticsAvg);
            ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE.get()).get();

            AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                    .proficiencyRankingScaleE(proficiencyRankingScaleE)
                    .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.ON_DEMAND)
                    .proficiencyRankingEffectiveDateTime(DateTime.now())
                    .build();

            LOGGER.info("Students calculated proficiency: {}", adaptiveProficiencyRanking);

            return adaptiveProficiencyRanking;
        }

        // There are on quiz statistics to determine an adequate ranking so default to system default proficiency ranking of ProficiencyRankingScaleE.THREE
        LOGGER.info("No AdaptiveLessonQuizStatistics were found for student, defaulting MicroLesson proficiency ranking to worst possible setting...");
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingScaleE(ProficiencyRankingScaleE.THREE)
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.ON_DEMAND)
                .proficiencyRankingEffectiveDateTime(DateTime.now())
                .build();

        return adaptiveProficiencyRanking;
    }


    protected double getQuizStatisticsAverage(List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics) {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        for(AdaptiveLessonQuizStatistics quizStatistic : adaptiveLessonQuizStatistics) {
            // Handle case where there is no actual Quiz because we just picked up just a Lesson with no quiz records in case someone created a Lesson and ML with no quizzes
            if (quizStatistic.getAdaptiveLearningQuizID() == null || quizStatistic.getAdaptiveLearningQuizID() == 0) {
                LOGGER.info("No valid quiz information found under query result, defaulting Proficiency Ranking to THREE");
                double defaultAdaptiveScore = ProficiencyRankingScaleE.THREE.getProficiencyScoreRangeE().getScoreRange().getMinimum();
                summaryStatistics.addValue(defaultAdaptiveScore);
            } else {
                // We have a real quiz here so use quiz score to caluculate.  Note Student will be penalized here because if they haven't attempted their quiz score will be 0
                LOGGER.info("Using student proficiency score: {} to get average", quizStatistic.getProficiencyScore());
                double actualScore = quizStatistic.getProficiencyScore() == null ? 0.0 : quizStatistic.getProficiencyScore();
                summaryStatistics.addValue(actualScore);
            }
        }

        double averageProficiencyScore = Precision.round(summaryStatistics.getMean(), 2);
        LOGGER.info("Calculated average proficiency score: {}", averageProficiencyScore);
        return averageProficiencyScore;
    }

    protected List<AdaptiveLearningQuiz> findAllQuizzesWitNoStudentAttempt(QPalXUser student, List<AdaptiveLearningQuiz> preRequisiteQuizzes) {
        List<AdaptiveLearningQuiz> filterList = new ArrayList<>(preRequisiteQuizzes);

        for (AdaptiveLearningQuiz preRequisiteQuiz : preRequisiteQuizzes) {
            List<AdaptiveLearningExperience> adaptiveLearningExperiences = iAdaptiveLearningExperienceService.findAllWithScorableActivityID(preRequisiteQuiz.getId(), student);
            if(!CollectionUtils.isEmpty(adaptiveLearningExperiences)) {
                LOGGER.info("Removing from filter list quiz: {}", preRequisiteQuiz.getId());
                filterList.remove(preRequisiteQuiz);
            }
        }

        return filterList;
    }


    private static final class QuizPrerequisiteComparator implements Comparator<AdaptiveLearningQuiz> {
        @Override
        public int compare(AdaptiveLearningQuiz o1, AdaptiveLearningQuiz o2) {
            if(o2.getId().longValue() > o1.getId().longValue()) {
                return -1;
            }
            return 1;
        }
    }

}
