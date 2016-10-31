package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveMicroLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
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
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveMicroLessonStatisticsService")
public class AdaptiveMicroLessonStatisticsService implements IAdaptiveMicroLessonStatisticsService {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StatisticsExecutorService")
    private ListeningExecutorService listeningExecutorService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveMicroLessonStatisticsService.class);


    @Transactional
    @Override
    public List<AdaptiveMicroLessonStatistics> findAdaptiveMicroLessonStatisticsByLessonAndCourse(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        ELearningCourse eLearningCourse = qPalXELesson.geteLearningCourse();

        LOGGER.info("Finding all AdaptiveMicroLessonStatistics for qPalXELesson: {} and eLearningCourse: {}", qPalXELesson.getLessonName(), eLearningCourse.getCourseName());

        String sql = "Select ml.MicroLessonName, " +
                "       ml.ID As MicroLessonID, " +
                "       ml.ELearningMediaFile, " +
                "       count(quizprog.MicroLessonID) As UniqueQuizzesAttempted,   " +
                "       count(alqz.ID) as TotalNumberOfQuizzes " +
                "From   QPalXEMicroLesson ml  " +
                "Left   Outer Join QPalXELesson qpl on qpl.ID = ml.QPalXELessonID    " +
                "Left   Outer Join  AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = ml.ID  " +
                "Left   Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.MicroLessonID = ml.ID    " +
                "Left   Outer Join TutorialLevelCalendar tlc on tlc.ID = qpl.TutorialLevelCalendarID   " +
                "Where  qpl.ELearningCourseID = ?  " +
                "And    qpl.ID = ?  " +
                "Group  By ml.`MicroLessonName`, ml.ID, ml.ELearningMediaFile  ";

        Long [] uniqueIDs = new Long[] {eLearningCourse.getId(), qPalXELesson.getId()};
        List<AdaptiveMicroLessonStatistics> results = jdbcTemplate.query(sql, uniqueIDs, AdaptiveMicroLessonStatistics.newRowMapper());
        return results;
    }
}
