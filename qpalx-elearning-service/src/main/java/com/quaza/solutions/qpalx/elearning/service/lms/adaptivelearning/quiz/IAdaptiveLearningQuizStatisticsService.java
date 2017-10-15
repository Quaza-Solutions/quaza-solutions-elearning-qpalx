package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizStatisticsService {


    public void recordAdaptiveLearningQuizProgress(Long adaptiveLearningQuizID, QPalXUser qPalXUser);

    public List<AdaptiveLessonQuizStatistics> findStudentQuizzesStatisticsForCourse(QPalXUser qPalXUser, Long courseID);

    public List<AdaptiveLessonQuizStatistics> findStudentQuizzesStatisticsForLesson(QPalXUser qPalXUser, Long eLessonID);

    public List<AdaptiveLessonQuizStatistics> findMicroLessonStudentQuizStatistics(QPalXUser qPalXUser, Long microLessonID);

}
