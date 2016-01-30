package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;

/**
 * @author manyce400
 */
public interface IELearningCourseService {


    public void saveELearningCourse(ELearningCourse eLearningCourse);

    public ELearningCourse findByCourseID(Long courseID);

    public ELearningCourse findByCourseName(String courseName);

}
