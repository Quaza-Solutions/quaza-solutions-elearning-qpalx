package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IELearningCourseRepository extends CrudRepository<ELearningCourse, Long> {



    @Query("Select  eLearningCourse From ELearningCourse eLearningCourse Where eLearningCourse.courseName = ?1")
    public ELearningCourse findByCourseName(String courseName);


}
