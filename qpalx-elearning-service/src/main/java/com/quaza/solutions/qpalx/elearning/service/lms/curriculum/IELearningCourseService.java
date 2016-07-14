package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IELearningCourseService {


    public void saveELearningCourse(ELearningCourse eLearningCourse);

    public ELearningCourse findByCourseID(Long courseID);

    public ELearningCourse findByCourseName(String courseName);

    public List<ELearningCourse> findStudentELearningCourses(QPalXUser qPalXUser);

}
