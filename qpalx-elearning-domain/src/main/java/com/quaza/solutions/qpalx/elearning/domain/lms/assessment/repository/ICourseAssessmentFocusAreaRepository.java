package com.quaza.solutions.qpalx.elearning.domain.lms.assessment.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CourseAssessmentFocusArea;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface ICourseAssessmentFocusAreaRepository extends CrudRepository<CourseAssessmentFocusArea, Long> {


    @Query("Select              courseAssessmentFocusArea From CourseAssessmentFocusArea courseAssessmentFocusArea "+
            "Where              courseAssessmentFocusArea.eLearningCourse =?1 "
    )
    public List<CourseAssessmentFocusArea> findAllByELearningCourse(ELearningCourse eLearningCourse);

}
