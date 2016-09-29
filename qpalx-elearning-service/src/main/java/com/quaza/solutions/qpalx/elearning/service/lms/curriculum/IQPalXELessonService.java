package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXELessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXELessonService {


    public QPalXELesson findQPalXELessonByID(Long id);

    public List<QPalXELesson> findQPalxELessonByCourse(ELearningCourse eLearningCourse);

    public List<QPalXELesson> findQPalxELessonByCalendarAndCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse);

    public void saveQPalXELesson(QPalXELesson qPalXELesson);

    public void createAndSaveQPalXELesson(IQPalXELessonVO iqPalXELessonVO);
}
