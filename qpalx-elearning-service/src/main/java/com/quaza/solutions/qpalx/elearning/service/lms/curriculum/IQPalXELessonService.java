package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXELessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXELessonService {


    public QPalXELesson findQPalXELessonByID(Long id);

    public List<QPalXELesson> findQPalXELessonByCourse(ELearningCourse eLearningCourse);

    public List<QPalXELesson> findQPalXELessonByCourseWithProficiency(ELearningCourse eLearningCourse, ProficiencyRankingScaleE proficiencyRankingScaleE);

    public List<QPalXELesson> findQPalXELessonByCalendarAndCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse);

    public void saveQPalXELesson(QPalXELesson qPalXELesson);

    public void createAndSaveQPalXELesson(IQPalXELessonVO iqPalXELessonVO);

    public void updateAndSaveQPalXELesson(QPalXELesson qPalXELesson, IQPalXELessonVO iqPalXELessonVO);

    public void deleteQPalXELesson(QPalXELesson qPalXELesson);

    public boolean isELessonDeletable(QPalXELesson qPalXELesson);

    public void moveQPalXELessonUp(QPalXELesson qPalXELesson);

    public void moveQPalXELessonDown(QPalXELesson qPalXELesson);
}
