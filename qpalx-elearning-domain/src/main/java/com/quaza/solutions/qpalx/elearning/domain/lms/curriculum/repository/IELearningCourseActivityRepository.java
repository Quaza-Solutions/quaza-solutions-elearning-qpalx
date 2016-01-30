package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IELearningCourseActivityRepository extends CrudRepository<ELearningCourseActivity, Long> {


    @Query("Select              eLearningCourseActivity From ELearningCourseActivity eLearningCourseActivity "+
           "INNER JOIN FETCH    eLearningCourseActivity.eLearningCourse eLearningCourse " +
           "Where               activityName =?1 " +
           "And                 eLearningCourse =?2 "
    )
    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse);
}
