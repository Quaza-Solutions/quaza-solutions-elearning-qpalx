package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm.ProficiencyAlgorithmExecutionInfo;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm.ProficiencyAlgorithmResult;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.service.councurrent.ApplicationConcurrencyConfig;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson.IMicroLessonPerformanceMonitorService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson.MicroLessonPerformanceMonitorService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.AdaptiveLearningQuizStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.AdaptiveLessonStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.IAdaptiveLessonStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.DefaultELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.QPalXELessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This IAdaptiveProficiencyAlgorithm implementation looks at all the Quizzes in a given ELearningCurriculum and calculates a score for Student based
 * on the number of quiz items that this Student has completed based on this Student's currently recorded AdaptiveProficiencyRanking in all courses
 * under this Curriculum.
 *
 * This algorithm is a greedy algorithm and will expect that for every quiz that this student has access to, Student would have attempted the quiz at least once.
 *
 * Failure to attempt a quiz which Student has access to will be penalized every time this algorithm runs until this issue has been fixed by the user
 *
 * @author manyce400
 */
@Service(QuizCompletionAdaptiveProficiencyAlgorithm.BEAN_NAME)
public class QuizCompletionAdaptiveProficiencyAlgorithm implements IAdaptiveProficiencyAlgorithm {


    @Autowired
    @Qualifier(DefaultELearningCourseService.BEAN_NAME)
    private IELearningCourseService ieLearningCourseService;

    @Autowired
    @Qualifier(QPalXELessonService.BEAN_NAME)
    private IQPalXELessonService iqPalXELessonService;

    @Autowired
    @Qualifier(AdaptiveLessonStatisticsService.BEAN_NAME)
    private IAdaptiveLessonStatisticsService iAdaptiveLessonStatisticsService;

    @Autowired
    @Qualifier(AdaptiveLearningQuizStatisticsService.BEAN_NAME)
    private IAdaptiveLearningQuizStatisticsService iAdaptiveLearningQuizStatisticsService;

    @Autowired
    @Qualifier(MicroLessonPerformanceMonitorService.BEAN_NAME)
    private IMicroLessonPerformanceMonitorService iMicroLessonPerformanceMonitorService;


    @Autowired
    @Qualifier(ApplicationConcurrencyConfig.BEAN_NAME)
    private ListeningExecutorService listeningExecutorService;

    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm.QuizCompletionAdaptiveProficiencyAlgorithm";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QuizCompletionAdaptiveProficiencyAlgorithm.class);


    @Override
    public ProficiencyAlgorithmExecutionInfo executeAlgorithm(QPalXUser qPalXUser, ELearningCourse eLearningCourse, TutorialLevelCalendar selectedTutorialLevelCalendar) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        Assert.notNull(selectedTutorialLevelCalendar, "selectedTutorialLevelCalendar cannot be null");

        LOGGER.info("Executing Quiz completion analytics algorithm in eLearningCourse: {} for selectedTutorialLevelCalendar: {}", eLearningCourse.getCourseName(), selectedTutorialLevelCalendar);
        ProficiencyAlgorithmExecutionInfo proficiencyAlgorithmExecutionInfo = ProficiencyAlgorithmExecutionInfo.newInstance("Performance On Quizzes");

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(qPalXUser, eLearningCourse.getId());
        findQuizzesBelowAndAboveAvg(adaptiveLessonQuizStatistics, proficiencyAlgorithmExecutionInfo);
        return proficiencyAlgorithmExecutionInfo;
    }

    @Override
    public ProficiencyAlgorithmExecutionInfo executeAlgorithm(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(currentAdaptiveProficiencyRanking, "currentAdaptiveProficiencyRanking cannot be null");

        LOGGER.info("Invoking algorithm for student: {} in curriculum: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName());

        // Find Current proficiency ranking for all Courses under this ELearningCurriculum
        Map<ELearningCourse, AdaptiveProficiencyRanking> courseRankings = findAllCoursesProficiencyRanking(qPalXUser, eLearningCurriculum);

        // For each course find Lessons that the Student's ProficiencyRankings will allow them to access

        return null;
    }


    protected void findQuizzesBelowAndAboveAvg(List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatisticsList, ProficiencyAlgorithmExecutionInfo proficiencyAlgorithmExecutionInfo) {
        for(AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics : adaptiveLessonQuizStatisticsList) {

            // Check that there is an actual quiz here before building
            if (adaptiveLessonQuizStatistics.getAdaptiveLearningQuizID() != null) {

                ProficiencyAlgorithmResult proficiencyAlgorithmResult = ProficiencyAlgorithmResult.newInstance(adaptiveLessonQuizStatistics.getProficiencyScore(), adaptiveLessonQuizStatistics.getAdaptiveLearningQuizTitle());

                if(adaptiveLessonQuizStatistics.isPerformanceAboveAverage()) {
                    // Add positive items that is currently helping Students overall score
                    LOGGER.info("Student is currently performing above avg on quiz: {}", adaptiveLessonQuizStatistics.getAdaptiveLearningQuizTitle());
                    proficiencyAlgorithmExecutionInfo.addPositiveProgressItem(proficiencyAlgorithmResult);
                } else {
                    if (!adaptiveLessonQuizStatistics.hasQuizAttempt()) {
                        LOGGER.info("Student hasn't attempted quiz: {}", adaptiveLessonQuizStatistics.getAdaptiveLearningQuizTitle());
                        proficiencyAlgorithmExecutionInfo.addNegativeProgressItem(proficiencyAlgorithmResult);
                    }
                }
            }
        }
    }


    protected Map<ELearningCourse, AdaptiveProficiencyRanking> findAllCoursesProficiencyRanking(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Map<ELearningCourse, AdaptiveProficiencyRanking> courseRankings = new HashMap<>();
        List<ELearningCourse> eLearningCourses = ieLearningCourseService.findByELearningCurriculum(eLearningCurriculum);

        for(ELearningCourse eLearningCourse : eLearningCourses) {
            AdaptiveProficiencyRanking adaptiveProficiencyRanking = iMicroLessonPerformanceMonitorService.calculateAdaptiveProficiencyRanking(qPalXUser, eLearningCourse);
            courseRankings.put(eLearningCourse, adaptiveProficiencyRanking);
        }

        return courseRankings;
    }

    protected List<QPalXELesson> findAllLessonsInScopeAcrossCourses(Map<ELearningCourse, AdaptiveProficiencyRanking> courseRankings) {
        List<QPalXELesson> lessonsWithCourseRankingUnlocked = new ArrayList<>();

        for(Map.Entry<ELearningCourse, AdaptiveProficiencyRanking> entry : courseRankings.entrySet()) {
            ELearningCourse eLearningCourse = entry.getKey();
            ProficiencyRankingScaleE proficiencyRankingScaleE = entry.getValue().getProficiencyRankingScaleE();
            List<QPalXELesson> courseLessons = iqPalXELessonService.findQPalXELessonByCourseWithProficiency(eLearningCourse, proficiencyRankingScaleE);
            lessonsWithCourseRankingUnlocked.addAll(courseLessons);
        }

        return lessonsWithCourseRankingUnlocked;
    }



}
