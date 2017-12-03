package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface ITutorialLevelCalendarService {


    public TutorialLevelCalendar findByID(Long id);

    public Optional<TutorialLevelCalendar> findCurrentCalendarByTutorialLevel(QPalXUser qPalXUser);

    public Optional<TutorialLevelCalendar> findCurrentCalendarByTutorialLevel(StudentTutorialLevel studentTutorialLevel);

    public List<TutorialLevelCalendar> findAllForQPalXUser(QPalXUser qPalXUser);

    public List<TutorialLevelCalendar> findAllByStudentTutorialLevel(StudentTutorialLevel studentTutorialLevel);

    public Map<TutorialLevelCalendar, ELearningCourseActivity> findAllCourseELearningActivities(ELearningCourse eLearningCourse);

}
