package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.QPalXEMicroLessonProgress;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IQPalXEMicroLessonProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLessonStatisticsService")
public class AdaptiveLessonStatisticsService implements IAdaptiveLessonStatisticsService {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IQPalXEMicroLessonProgressRepository iqPalXEMicroLessonProgressRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StatisticsExecutorService")
    private ListeningExecutorService listeningExecutorService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLessonStatisticsService.class);


    @Transactional
    @Override
    public void recordAdaptiveLessonStatistics(QPalXEMicroLesson qPalXEMicroLesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        //Runnable statisticsRecordTask = () -> {
            LOGGER.info("Recording Lesson statistics on MicroLesson with ID: {} for User: {}", qPalXEMicroLesson.getId(), qPalXUser.getEmail());

            QPalXEMicroLessonProgress qPalXEMicroLessonProgress = iqPalXEMicroLessonProgressRepository.findByUserAndMicroLessonID(qPalXUser.getId(), qPalXEMicroLesson.getId());
            if(qPalXEMicroLessonProgress != null) {
                qPalXEMicroLessonProgress.increaseNumberOfAttempts();
                qPalXEMicroLessonProgress.setLastAttemptEntryDate(new DateTime());
            } else {
                // create a mew one and save
                qPalXEMicroLessonProgress = QPalXEMicroLessonProgress.builder()
                        .microLessonID(qPalXEMicroLesson.getId())
                        .qPalxUserID(qPalXUser.getId())
                        .numberOfAttempts(1)
                        .lastAttemptEntryDate(new DateTime())
                        .build();
            }
        iqPalXEMicroLessonProgressRepository.save(qPalXEMicroLessonProgress);
        //};


        //listeningExecutorService.submit(statisticsRecordTask);
    }

    @Override
    public List<AdaptiveLessonStatistics> findAdaptiveLessonStatisticsByCourseIDAndTutorialLevel(QPalXUser qPalXUser, TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(tutorialLevelCalendar, "tutorialLevelCalendar cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding all AdaptiveLessonStatistics for tutorialLevelCalendar: {} and eLearningCourse: {}", tutorialLevelCalendar.getCalendarItemName(), eLearningCourse.getCourseName());

        String sql = "Select StudentLessonsMicroLessonsAndQuizzesInCourse.StudentID, " +
                "       StudentLessonsMicroLessonsAndQuizzesInCourse.LessonID, " +
                "       StudentLessonsMicroLessonsAndQuizzesInCourse.LessonName,  " +
                "       StudentLessonsMicroLessonsAndQuizzesInCourse.LessonIntroVideo, " +
                "       StudentQuestionBankItemsInLesson.TotalNumberOfQuestionBankItems,  " +
                "       IFNULL(StudentQuestionBankItemsAttempted.UniqueQuestionBankItemsAttempted, 0) As UniqueQuestionBankItemsAttempted,  " +
                "       StudentLessonsMicroLessonsAndQuizzesInCourse.TotalNumberOfMicroLessons, " +
                "       IFNULL(AllUserMicroLessonAndQuizAttemptsInCourse.UniqueMicroLessonsAttempted, 0) As UniqueMicroLessonsAttempted,  " +
                "       StudentLessonsMicroLessonsAndQuizzesInCourse.TotalNumberOfQuizzes, " +
                "       IFNULL(AllUserMicroLessonAndQuizAttemptsInCourse.UniqueQuizzesAttempted, 0) As UniqueQuizzesAttempted  " +
                "From   (  " +
                "       Select  qUser.ID As StudentID, qPell.ID As LessonID, qPell.LessonName, qPell.ELearningMediaFile As LessonIntroVideo, count(qMell.ID) As TotalNumberOfMicroLessons, count(alqz.ID) As TotalNumberOfQuizzes  " +
                "       From    QPalXUser qUser " +
                "       Join    StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID  " +
                "       Join    ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID  " +
                "       Join    ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID  " +
                "       Join    QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID   " +
                "       Join    QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID  " +
                "       Left    Outer Join AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID " +
                "       Left    Outer Join TutorialLevelCalendar tlc on tlc.ID = qPell.TutorialLevelCalendarID  " +
                "       Where   qUser.ID = ?  " +
                "       And     eCors.ID = ?  " +
                "       And     tlc.ID = ?  " +
                "       Group   By qUser.ID, qPell.ID, qPell.LessonName, qPell.ELearningMediaFile " +
                "       ) As StudentLessonsMicroLessonsAndQuizzesInCourse " +

                // Retreive total number of Question banks section
                "Left   Outer Join (  " +
                "       Select  qUser.ID As StudentID, qPell.ID As LessonID, qPell.LessonName, count(qBit.ID) As TotalNumberOfQuestionBankItems " +
                "       From    QPalXUser qUser " +
                "       Join    StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID  " +
                "       Join    ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID " +
                "       Join    ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID    " +
                "       Join    QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID    " +
                "       Join    QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID   " +
                "       Left    Outer Join TutorialLevelCalendar tlc on tlc.ID = qPell.TutorialLevelCalendarID   " +
                "       Where   qUser.ID = ?  " +
                "       And     eCors.ID = ?  " +
                "       And     tlc.ID = ?   " +
                "      Group    By  qUser.ID, qPell.ID, qPell.LessonName    " +
                ")  As StudentQuestionBankItemsInLesson  on StudentQuestionBankItemsInLesson.StudentID = StudentLessonsMicroLessonsAndQuizzesInCourse.StudentID   " +

                // Retreive total number of Question banks attempted section
                "Left   Outer Join (  " +
                "       Select  qBpro.QPalxUserID, qPell.ID As LessonID, qPell.LessonName, count(qBpro.ID) as UniqueQuestionBankItemsAttempted " +
                "       From    QPalXELesson qPell " +
                "       Left    Outer Join  QuestionBankItem qBit on qBit.QPalXELessonID = qPell.ID  " +
                "       Left    Outer Join  QuestionBankProgress qBpro on qBpro.QuestionBankItemID = qBit.ID " +
                "       Left    Outer Join TutorialLevelCalendar tlc on tlc.ID = qPell.TutorialLevelCalendarID    " +
                "       Where   qPell.ELearningCourseID =  ?  " +
                "       And     tlc.ID =  ?  " +
                "      Group    By qBpro.QPalxUserID, qPell.ID, qPell.LessonName    " +
                ")  As StudentQuestionBankItemsAttempted on StudentQuestionBankItemsAttempted.QPalxUserID = StudentLessonsMicroLessonsAndQuizzesInCourse.StudentID   " +

                "Left   Outer Join (  " +
                "       Select  mlp.QPalxUserID, qpl.ID As LessonID, qpl.LessonName, qpl.ELearningMediaFile As LessonIntroVideo, count(mlp.MicroLessonID) As UniqueMicroLessonsAttempted, count(quizprog.ID) as UniqueQuizzesAttempted " +
                "       From    QPalXELesson qpl " +
                "       Left    Outer Join  QPalXEMicroLesson ml on ml.QPalXELessonID = qpl.ID  " +
                "       Left    Outer Join  QPalXEMicroLessonProgress mlp on mlp.MicroLessonID = ml.ID    " +
                "       Left    Outer Join AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = ml.ID  " +
                "       Left    Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.MicroLessonID = ml.ID    " +
                "       Left    Outer Join TutorialLevelCalendar tlc on tlc.ID = qpl.TutorialLevelCalendarID  " +
                "       Where   qpl.ELearningCourseID = ?  " +
                "       And     tlc.ID = ?  " +
                "       Group   By mlp.QPalxUserID, qpl.ID, qpl.LessonName, qpl.ELearningMediaFile   " +
                ") As AllUserMicroLessonAndQuizAttemptsInCourse on AllUserMicroLessonAndQuizAttemptsInCourse.QPalxUserID = StudentLessonsMicroLessonsAndQuizzesInCourse.StudentID  ";

        LOGGER.info("Running SQL:=>  {}", sql);

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), eLearningCourse.getId(), tutorialLevelCalendar.getId(), qPalXUser.getId(), eLearningCourse.getId(), tutorialLevelCalendar.getId(), eLearningCourse.getId(), tutorialLevelCalendar.getId(), eLearningCourse.getId(), tutorialLevelCalendar.getId()};
        List<AdaptiveLessonStatistics> results = jdbcTemplate.query(sql, uniqueIDs, AdaptiveLessonStatistics.newRowMapper());
        return results;
    }

}
