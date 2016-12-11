package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveMicroLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
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
    public List<AdaptiveMicroLessonStatistics> findAdaptiveMicroLessonStatisticsByLessonAndCourse(QPalXELesson qPalXELesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        ELearningCourse eLearningCourse = qPalXELesson.geteLearningCourse();

        LOGGER.info("Finding all AdaptiveMicroLessonStatistics for qPalXELesson: {} and eLearningCourse: {}", qPalXELesson.getLessonName(), eLearningCourse.getCourseName());

        String sql = "Select    StudentMicroLessonQuizTotals.StudentID, StudentMicroLessonQuizTotals.MicroLessonID, StudentMicroLessonQuizTotals.MicroLessonName, StudentMicroLessonQuizTotals.ELearningMediaFile, StudentMicroLessonQuizTotals.TotalNumberOfQuizzes, IFNULL(StudentMicroLessonQuizAttempts.UniqueQuizzesAttempted, 0) As UniqueQuizzesAttempted " +
                "From           (  " +
                "                   Select    qUser.ID As StudentID, qMell.ID As MicroLessonID, qMell.MicroLessonName, qMell.ELearningMediaFile, count(alqz.ID) as TotalNumberOfQuizzes  " +
                "                   From      QPalXUser qUser   " +
                "                   Join      StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID   " +
                "                   Join      ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID    " +
                "                   Join      ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID  " +
                "                   Join      QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID   " +
                "                   Join      QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID     " +
                "                   Left      Outer Join AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID   " +
                "                   Where     qUser.ID = ?  " +
                "                   And       eCors.ID = ?  " +
                "                   And       qPell.ID = ?  " +
                "                   Group     By qUser.ID, qMell.ID,  qMell.MicroLessonName, qMell.ELearningMediaFile   " +
                "               ) As StudentMicroLessonQuizTotals " +
                "Left Outer Join (  " +
                "                   Select    quizprog.QPalxUserID As StudentID, qMell.ID As MicroLessonID, qMell.MicroLessonName, qMell.ELearningMediaFile, count(quizprog.AdaptiveLearningQuizID) As UniqueQuizzesAttempted  " +
                "                   From      QPalXELesson qPell " +
                "                   Left      Outer Join QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID  " +
                "                   Left      Outer Join AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID   " +
                "                   Left      Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.AdaptiveLearningQuizID = alqz.ID  " +
                "                   Where     qPell.ID = ?   " +
                "                   And       quizprog.QPalxUserID = ?   " +
                "                   Group     By quizprog.QPalxUserID, qMell.ID,  qMell.MicroLessonName, qMell.ELearningMediaFile    " +
                ") As StudentMicroLessonQuizAttempts on StudentMicroLessonQuizAttempts.StudentID = StudentMicroLessonQuizTotals.StudentID ";

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), eLearningCourse.getId(),  qPalXELesson.getId(), qPalXELesson.getId(), qPalXUser.getId()};
        List<AdaptiveMicroLessonStatistics> results = jdbcTemplate.query(sql, uniqueIDs, AdaptiveMicroLessonStatistics.newRowMapper());
        return results;
    }
}
