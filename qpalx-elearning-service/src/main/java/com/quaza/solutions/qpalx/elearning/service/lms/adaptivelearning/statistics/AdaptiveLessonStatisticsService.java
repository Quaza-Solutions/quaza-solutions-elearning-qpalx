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

        Runnable statisticsRecordTask = () -> {
            LOGGER.info("Recording Lesson statistics on MicroLesson with ID: {} for User: {}", qPalXEMicroLesson.getId(), qPalXUser.getEmail());

            QPalXEMicroLessonProgress qPalXEMicroLessonProgress = iqPalXEMicroLessonProgressRepository.findByUserAndMicroLessonID(qPalXUser.getId(), qPalXEMicroLesson.getId());
            if(qPalXEMicroLessonProgress != null) {
                qPalXEMicroLessonProgress.increaseNumberOfAttempts();
                qPalXEMicroLessonProgress.setLastAttemptEntryDate(new DateTime());
                iqPalXEMicroLessonProgressRepository.save(qPalXEMicroLessonProgress);
            } else {
                // create a mew one and save
                QPalXEMicroLessonProgress qPalXEMicroLessonProgress1 = QPalXEMicroLessonProgress.builder()
                        .microLessonID(qPalXEMicroLesson.getId())
                        .qPalxUserID(qPalXUser.getId())
                        .numberOfAttempts(1)
                        .lastAttemptEntryDate(new DateTime())
                        .build();
                iqPalXEMicroLessonProgressRepository.save(qPalXEMicroLessonProgress);
            }
        };

        listeningExecutorService.submit(statisticsRecordTask);
    }

    @Override
    public List<AdaptiveLessonStatistics> findAdaptiveLessonStatisticsByCourseIDAndTutorialLevel(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse) {
        Assert.notNull(tutorialLevelCalendar, "tutorialLevelCalendar cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding all AdaptiveLessonStatistics for tutorialLevelCalendar: {} and eLearningCourse: {}", tutorialLevelCalendar.getCalendarItemName(), eLearningCourse.getCourseName());

        String sql = "Select qpl.LessonName, " +
                "       qpl.ID As LessonID, " +
                "       qpl.ELearningMediaFile, " +
                "       count(mlp.MicroLessonID) As UniqueMicroLessonsAttempted, " +
                "       count(ml.ID) as TotalNumberOfMicroLessons, " +
                "       count(quizprog.ID) as UniqueQuizzesAttempted,  " +
                "       count(alqz.ID) as TotalNumberOfQuizzes " +
                "From   QPalXELesson qpl  " +
                "Left   Outer Join  QPalXEMicroLesson ml on ml.QPalXELessonID = qpl.ID   " +
                "Left   Outer Join  AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = ml.ID  " +
                "Left   Outer Join  QPalXEMicroLessonProgress mlp on mlp.MicroLessonID = ml.ID    " +
                "Left   Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.MicroLessonID = ml.ID   " +
                "Left   Outer Join TutorialLevelCalendar tlc on tlc.ID = qpl.TutorialLevelCalendarID  " +
                "Where  qpl.ELearningCourseID = ?  " +
                "And    tlc.ID = ?  " +
                "Group  By qpl.LessonName, qpl.ID, qpl.ELearningMediaFile  ";

        Long [] uniqueIDs = new Long[] {eLearningCourse.getId(), tutorialLevelCalendar.getId()};
        List<AdaptiveLessonStatistics> results = jdbcTemplate.query(sql, uniqueIDs, AdaptiveLessonStatistics.newRowMapper());
        return results;
    }

}
