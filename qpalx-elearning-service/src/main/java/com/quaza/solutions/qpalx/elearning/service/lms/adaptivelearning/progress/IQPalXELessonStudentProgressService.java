package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * @author manyce400
 */
public interface IQPalXELessonStudentProgressService {


    /**
     * Calculates and returns the progress of Student QPalxUser as a percentage value in a given lesson.
     */
    public double calculateLessonProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser);


}
