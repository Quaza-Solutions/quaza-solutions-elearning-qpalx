package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;

/**
 * @author manyce400
 */
public interface IELearningCourseActivityService {


    public ELearningCourseActivity findByID(Long id);

    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse);

}
