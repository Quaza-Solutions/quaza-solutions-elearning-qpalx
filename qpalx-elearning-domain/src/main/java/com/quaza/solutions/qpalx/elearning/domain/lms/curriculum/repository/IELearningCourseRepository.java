package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IELearningCourseRepository extends CrudRepository<ELearningCourse, Long> {


    @Query("Select  eLearningCourse From ELearningCourse eLearningCourse Where eLearningCourse.courseName = ?1")
    public ELearningCourse findByCourseName(String courseName);

    @Query("Select  eLearningCourse From ELearningCourse eLearningCourse Where eLearningCourse.eLearningCurriculum = ?1")
    public List<ELearningCourse> findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);


}
