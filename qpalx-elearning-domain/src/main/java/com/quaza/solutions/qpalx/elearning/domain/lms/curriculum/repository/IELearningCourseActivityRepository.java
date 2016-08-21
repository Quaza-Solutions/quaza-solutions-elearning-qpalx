package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IELearningCourseActivityRepository extends CrudRepository<ELearningCourseActivity, Long> {


    @Query("Select              eLearningCourseActivity From ELearningCourseActivity eLearningCourseActivity "+
            "INNER JOIN FETCH    eLearningCourseActivity.eLearningCourse eLearningCourse " +
            "Where               eLearningCourse =?1 "
    )
    public List<ELearningCourseActivity> findELearningCourseActivities( ELearningCourse eLearningCourse);

    @Query("Select              eLearningCourseActivity From ELearningCourseActivity eLearningCourseActivity "+
            "INNER JOIN FETCH    eLearningCourseActivity.tutorialLevelCalendar tutorialLevelCalendar " +
            "INNER JOIN FETCH    eLearningCourseActivity.eLearningCourse eLearningCourse " +
            "Where               tutorialLevelCalendar =?1 " +
            "And                 eLearningCourse =?2 "
    )
    public List<ELearningCourseActivity> findELearningCourseActivitiesByTutorialCalendarAndCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse);


    @Query("Select              eLearningCourseActivity From ELearningCourseActivity eLearningCourseActivity "+
           "INNER JOIN FETCH    eLearningCourseActivity.eLearningCourse eLearningCourse " +
           "Where               activityName =?1 " +
           "And                 eLearningCourse =?2 "
    )
    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse);
}
