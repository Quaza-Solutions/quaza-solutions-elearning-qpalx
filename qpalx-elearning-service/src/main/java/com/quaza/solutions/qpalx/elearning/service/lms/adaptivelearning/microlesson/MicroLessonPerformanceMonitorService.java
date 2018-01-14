package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.councurrent.ApplicationConcurrencyConfig;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.DefaultAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveLearningQuizStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.AdaptiveLearningExperienceService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    @Autowired
    @Qualifier(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    @Autowired
    @Qualifier(ApplicationConcurrencyConfig.BEAN_NAME)
    private ListeningExecutorService listeningExecutorService;


    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson.MicroLessonPerformanceMonitorService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MicroLessonPerformanceMonitorService.class);


    @Override
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser student, ELearningCourse eLearningCourse) {
        Assert.notNull(student, "student cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding current performance information for Student: {} in ELearningCourse: {}", student.getEmail(), eLearningCourse.getCourseName());

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(student, eLearningCourse.getId());

        // Find the Students Current ProficiencyRanking in given Course Curriculum to use that as a BenchMark to find Lessons which Student should have access to now.
        ELearningCurriculum eLearningCurriculum = eLearningCourse.geteLearningCurriculum();
        AdaptiveProficiencyRanking curriculumProficiencyRanking = iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(student, eLearningCurriculum);
        return caclulateAdaptiveProficiencyRanking(adaptiveLessonQuizStatistics, curriculumProficiencyRanking);
    }

    @Override
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser student, QPalXEMicroLesson microLesson) {
        Assert.notNull(student, "student cannot be null");
        Assert.notNull(microLesson, "microLesson cannot be null");

        LOGGER.debug("Finding current performance information for Student: {} in MiroLesson: {}", student.getEmail(), microLesson.getMicroLessonName());

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = iAdaptiveLearningQuizStatisticsService.findMicroLessonStudentQuizStatistics(student, microLesson.getId());

        // Find the Students Current ProficiencyRanking in given MicroLesson Curriculum to use that as a BenchMark to find Lessons which Student should have access to now.
        ELearningCurriculum eLearningCurriculum = microLesson.getQPalXELesson().geteLearningCourse().geteLearningCurriculum();
        AdaptiveProficiencyRanking curriculumProficiencyRanking = iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(student, eLearningCurriculum);
        return caclulateAdaptiveProficiencyRanking(adaptiveLessonQuizStatistics, curriculumProficiencyRanking);
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


    protected AdaptiveProficiencyRanking caclulateAdaptiveProficiencyRanking(List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics, AdaptiveProficiencyRanking curriculumProficiencyRanking) {
        if(adaptiveLessonQuizStatistics != null && adaptiveLessonQuizStatistics.size() > 0) {
            ProficiencyRankingScaleE curriculumProficiencyRankingScale = curriculumProficiencyRanking.getProficiencyRankingScaleE();

            // Calculate the average score that this Student currently has across Quizzes in this Curriculum with a minimum ProficiencyRanking of their Curriculum ranking.
            double studentQuizAverageOnCurriculumRankingQuizzes = calculateQuizAvgScoreWithMinProficiency(adaptiveLessonQuizStatistics, curriculumProficiencyRankingScale);
            LOGGER.info("Average Student score on Curriculum proficiency level: {} and Curriculum Proficiency Ranking Range: {}", studentQuizAverageOnCurriculumRankingQuizzes, curriculumProficiencyRankingScale.getProficiencyScoreRangeE().getScoreRange());

            AdaptiveProficiencyRanking adaptiveProficiencyRanking = iAdaptiveProficiencyRankingService.buildAdaptiveProficiencyRanking(studentQuizAverageOnCurriculumRankingQuizzes, ProficiencyRankingTriggerTypeE.ON_DEMAND);
            return adaptiveProficiencyRanking;
        }

        LOGGER.info("No AdaptiveLessonQuizStatistics were found for student, defaulting proficiency to the currently calculated Proficiency Rating in Curriculum");
        return curriculumProficiencyRanking;
    }


    protected double calculateQuizAvgScoreWithMinProficiency(List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics, ProficiencyRankingScaleE minProficiencyRankingScaleE) {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        for(AdaptiveLessonQuizStatistics quizStatistic : adaptiveLessonQuizStatistics) {
            boolean isProficiencyRankingInQuizScope = minProficiencyRankingScaleE.isProficiencyRankingBetweenFloorAndCeiling(quizStatistic.getProficiencyRankingScaleFloor(), quizStatistic.getProficiencyRankingScaleCeiling());

            if (isProficiencyRankingInQuizScope) {

                // Make sure that this is a valid quiz with a QuizID > 0.  This handles the case where Student has access to a Quiz but hasn't attempted to take the quiz.
                if (quizStatistic.getAdaptiveLearningQuizID() != null && quizStatistic.getAdaptiveLearningQuizID() > 0) {
                    LOGGER.info("Using student proficiency score: {} to get average", quizStatistic.getProficiencyScore());
                    double actualScore = quizStatistic.getProficiencyScore() == null ? 0.0 : quizStatistic.getProficiencyScore();
                    summaryStatistics.addValue(actualScore);
                }
            } else {
                // Student has access to quiz and has attempted quiz so their previous course ranking had allowed them to take this quiz, factor this in avg score
                if (quizStatistic.getAdaptiveLearningQuizID() != null && quizStatistic.getAdaptiveLearningQuizID() > 0 && quizStatistic.getLearningExperienceStartDate() != null) {
                    LOGGER.info("Using student quiz attempt on experienceDate: {} to enhance score", quizStatistic.getLearningExperienceStartDate());
                    double actualScore = quizStatistic.getProficiencyScore() == null ? 0.0 : quizStatistic.getProficiencyScore();
                    summaryStatistics.addValue(actualScore);
                }
            }
        }


        if (!Double.isNaN(summaryStatistics.getMean())) {
            // Only calculate average if there was quiz statistics
            double averageProficiencyScore = Precision.round(summaryStatistics.getMean(), 2);
            LOGGER.info("Calculated average proficiency score: {}", averageProficiencyScore);
            return averageProficiencyScore;
        }

        return minProficiencyRankingScaleE.getProficiencyScoreRangeE().getScoreRange().getMinimum();
    }

//    private boolean isStudentOutPerformingCurriculumProficiency(ProficiencyRankingScaleE curriculumProficiencyRankingScale, double computedAvgScore) {
//        Optional<ProficiencyScoreRangeE> computedScoreRange = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(computedAvgScore);
//        Optional<ProficiencyRankingScaleE> computedRankingScale = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(computedScoreRange.get());
//        return computedRankingScale.get().getProficiencyRanking() > curriculumProficiencyRankingScale.getProficiencyRanking();
//    }

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
