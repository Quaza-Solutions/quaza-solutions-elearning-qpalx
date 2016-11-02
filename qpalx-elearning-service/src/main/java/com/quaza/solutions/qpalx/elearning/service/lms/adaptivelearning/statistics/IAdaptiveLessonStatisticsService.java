package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLessonStatisticsService {


    public void recordAdaptiveLessonStatistics(QPalXEMicroLesson qPalXEMicroLesson, QPalXUser qPalXUser);

    public List<AdaptiveLessonStatistics> findAdaptiveLessonStatisticsByCourseIDAndTutorialLevel(QPalXUser qPalXUser, TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse);

}
