package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXELessonProgressStatisticsService")
public class QPalXELessonProgressStatisticsService implements IAdaptiveLessonProgressStatisticsService {


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizProgressService")
    private IAdaptiveLearningQuizProgressService iAdaptiveLearningQuizProgressService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonProgressService")
    private IQPalXEMicroLessonProgressService iqPalXEMicroLessonProgressService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QuestionBankProgressService")
    private IQuestionBankProgressService iQuestionBankProgressService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXELessonProgressStatisticsService.class);


    @Override
    public List<AdaptiveLessonProgressStatistics> findAdaptiveLessonProgressStatisticsByCourse() {
        return null;
    }

    @Transactional
    @Override
    public double calculateLessonProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser) {
        String sql = "Select qpl.LessonName, " +
                     "       count(mlp.MicroLessonID) As UniqueMicroLessonsAttempted, " +
                     "       (Select count(*) as TotalNumberOfMicroLessons From QPalXELesson qpl Join QPalXEMicroLesson ml on ml.QPalXELessonID = qpl.ID  Where qpl.ID = ? ) As TotalNumberOfLessons, " +
                     "       count(quizprog.ID) as UniqueQuizzesAttempted, " +
                     "       (Select count(*) as TotalNumberOfQuizzes From AdaptiveLearningQuiz quiz Join QPalXEMicroLesson ml on ml.ID =  quiz.QPalXEMicroLessonID Join QPalXELesson qpl on qpl.ID =  ml.QPalXELessonID Where qpl.ID = ?) As TotalNumberOfQuizzes  " +
                     "From   QPalXELesson qpl  " +
                     "Left   Outer Join  QPalXEMicroLesson ml on ml.QPalXELessonID = qpl.ID   " +
                     "Left   Outer Join  QPalXEMicroLessonProgress mlp on mlp.MicroLessonID = ml.ID   " +
                     "Left   Outer Join AdaptiveLearningQuizProgress quizprog on quizprog.MicroLessonID = ml.ID  " +
                     "Where  qpl.ID = ?  " +
                     "Group  By qpl.LessonName";

        AdaptiveLessonProgressStatistics adaptiveLessonProgressStatistics = jdbcTemplate.queryForObject(sql, new Integer[] {Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1)}, AdaptiveLessonProgressStatistics.newRowMapper());
        System.out.println("adaptiveLessonProgressStatistics = " + adaptiveLessonProgressStatistics);
        return adaptiveLessonProgressStatistics.getTotalLessonCompletionRate();
    }
}
