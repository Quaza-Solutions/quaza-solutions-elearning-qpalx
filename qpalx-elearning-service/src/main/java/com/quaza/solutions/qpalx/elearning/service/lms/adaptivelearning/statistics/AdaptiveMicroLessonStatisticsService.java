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

        String sql = "Select    qUser.ID As StudentID, qMell.ID As MicroLessonID, qMell.MicroLessonName, qMell.ELearningMediaFile,  count(quizprog.MicroLessonID) As UniqueQuizzesAttempted,  count(alqz.ID) as TotalNumberOfQuizzes " +
                "From           QPalXUser qUser  " +
                "Join           StudentEnrolmentRecord sErr on sErr.QPalxUserID = qUser.ID  " +
                "Join           ELearningCurriculum eCurr on eCurr.StudentTutorialGradeID = sErr.StudentTutorialGradeID   " +
                "Join           ELearningCourse eCors on eCors.ELearningCurriculumID = eCurr.ID  " +
                "Join           QPalXELesson qPell on qPell.ELearningCourseID = eCors.ID    " +
                "Join           QPalXEMicroLesson qMell on qMell.QPalXELessonID = qPell.ID  " +
                "Left           Outer Join AdaptiveLearningQuiz alqz on alqz.QPalXEMicroLessonID = qMell.ID  " +
                "Left           Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.MicroLessonID = qMell.ID  " +
                "Where          qUser.ID = ?  " +
                "And            eCors.ID = ?  " +
                "And            quizprog.QPalxUserID = ?  " +
                "And            qPell.ID = ?  " +
                "Group          By qUser.ID, qMell.ID,  qMell.MicroLessonName, qMell.ELearningMediaFile  ";

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), eLearningCourse.getId(), qPalXUser.getId(), qPalXELesson.getId()};
        List<AdaptiveMicroLessonStatistics> results = jdbcTemplate.query(sql, uniqueIDs, AdaptiveMicroLessonStatistics.newRowMapper());
        return results;
    }
}
