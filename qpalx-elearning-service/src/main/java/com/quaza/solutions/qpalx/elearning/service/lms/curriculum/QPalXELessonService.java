package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXELessonRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
public class QPalXELessonService implements IQPalXELessonService {



    @Autowired
    private IQPalXELessonRepository iqPalXELessonRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXELessonService.class);


    @Override
    public QPalXELesson findQPalXELessonByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding QPalXELesson with id:> {}", id);
        return iqPalXELessonRepository.findOne(id);
    }

    @Override
    public List<QPalXELesson> findQPalxELessonByCourse(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.debug("Finding QPalXELesson with eLearningCourse:> {}", eLearningCourse);
        List<QPalXELesson> qPalXELessons = iqPalXELessonRepository.findQPalXELessonForELearningCourse(eLearningCourse);
        return qPalXELessons;
    }

    @Override
    public List<QPalXELesson> findQPalxELessonByCalendarAndCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse) {
        Assert.notNull(tutorialLevelCalendar, "tutorialLevelCalendar cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.debug("Finding QPalxELesson for calendar: {} and eLearningCourse: {}", tutorialLevelCalendar, eLearningCourse);
        List<QPalXELesson> qPalXELessons = iqPalXELessonRepository.findQPalXELessonByTutorialCalendarELearningCourse(tutorialLevelCalendar, eLearningCourse);
        return qPalXELessons;
    }


}
