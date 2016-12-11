package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveMicroLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveMicroLessonStatisticsService {

    public List<AdaptiveMicroLessonStatistics> findAdaptiveMicroLessonStatisticsByLessonAndCourse(QPalXELesson qPalXELesson, QPalXUser qPalXUser);

}
