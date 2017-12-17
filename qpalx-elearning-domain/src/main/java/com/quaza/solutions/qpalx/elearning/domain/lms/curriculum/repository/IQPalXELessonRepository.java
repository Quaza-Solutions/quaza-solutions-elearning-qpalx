package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.domain.util.repository.IEntityHasOrderInfoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXELessonRepository extends IEntityHasOrderInfoRepository<QPalXELesson>, CrudRepository<QPalXELesson, Long> {


    @Query("Select              qPalXELesson From QPalXELesson qPalXELesson "+
            "INNER JOIN FETCH    qPalXELesson.eLearningCourse eLearningCourse " +
            "Where               eLearningCourse =?1 " +
            "Order By            qPalXELesson.elementOrder ASC"
    )
    public List<QPalXELesson> findQPalXELessonForELearningCourse(ELearningCourse eLearningCourse);


    @Query("Select              qPalXELesson From QPalXELesson qPalXELesson "+
            "INNER JOIN FETCH    qPalXELesson.tutorialLevelCalendar tutorialLevelCalendar " +
            "INNER JOIN FETCH    qPalXELesson.eLearningCourse eLearningCourse " +
            "Where               tutorialLevelCalendar =?1 " +
            "And                 eLearningCourse =?2 " +
            "Order By            qPalXELesson.elementOrder ASC"
    )
    public List<QPalXELesson> findQPalXELessonByTutorialCalendarELearningCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse);


    @Override
    public default void saveIEntityHasOrderInfo(QPalXELesson iEntityHasOrderInfo) {
        Assert.notNull(iEntityHasOrderInfo, "iEntityHasOrderInfo cannot be null");
        save(iEntityHasOrderInfo);
    }

    @Override
    public default List<QPalXELesson> findAllInstancesContainingEntity(QPalXELesson iEntityHasOrderInfo) {
        Assert.notNull(iEntityHasOrderInfo, "iEntityHasOrderInfo cannot be null");
        ELearningCourse eLearningCourse = iEntityHasOrderInfo.geteLearningCourse();
        return findQPalXELessonForELearningCourse(eLearningCourse);
    }
}
