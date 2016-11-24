package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizStatisticsService {


    public void recordAdaptiveLearningQuizProgress(Long scoreableActivityID, QPalXUser qPalXUser);

    public List<AdaptiveLessonQuizStatistics> findMicroLessonStudentQuizStatistics(QPalXUser qPalXUser, QPalXEMicroLesson qPalXEMicroLesson);

}
