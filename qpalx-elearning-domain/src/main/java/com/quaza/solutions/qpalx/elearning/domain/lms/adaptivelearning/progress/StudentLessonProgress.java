package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;

/**
 * @author manyce400
 */
public class StudentLessonProgress implements IProgressAware {


    private final double progress;

    private final QPalXELesson qPalXELesson;

    public StudentLessonProgress(double progress, QPalXELesson qPalXELesson) {
        this.progress = progress;
        this.qPalXELesson = qPalXELesson;
    }

    public QPalXELesson getqPalXELesson() {
        return qPalXELesson;
    }

    @Override
    public double getProgress() {
        return 0;
    }
}
