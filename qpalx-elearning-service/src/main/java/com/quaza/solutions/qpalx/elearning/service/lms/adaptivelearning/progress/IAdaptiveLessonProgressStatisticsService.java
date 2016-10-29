package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLessonProgressStatisticsService {


    public List<AdaptiveLessonProgressStatistics> findAdaptiveLessonProgressStatisticsByCourse();

    /**
     * Calculates and returns the progress of Student QPalxUser as a percentage value in a given lesson.
     */
    public double calculateLessonProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser);


}
