package com.quaza.solutions.qpalx.elearning.service.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CourseAssessmentFocusArea;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;

import java.util.List;

/**
 * @author manyce400
 */
public interface ICourseAssessmentFocusAreaService {


    public CourseAssessmentFocusArea findByID(Long id);

    public List<CourseAssessmentFocusArea> findCourseAssessmentFocusAreas(ELearningCourse eLearningCourse);

    public void save(CourseAssessmentFocusArea courseAssessmentFocusArea);

    public void delete(CourseAssessmentFocusArea courseAssessmentFocusArea);

    public void makeCourseAssessmentFocusArea(CurriculumProficiencyAssessment curriculumProficiencyAssessment, ELearningCourse eLearningCourse);

    public void modifyCourseAssessmentFocusAreaWithQuiz(CourseAssessmentFocusArea courseAssessmentFocusArea, Long adaptiveLearningQuizID);

    public void modifyCourseAssessmentFocusAreaWithQuiz(CourseAssessmentFocusArea courseAssessmentFocusArea, AdaptiveLearningQuiz adaptiveLearningQuiz);

}
