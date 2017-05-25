package com.quaza.solutions.qpalx.elearning.service.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CourseAssessmentFocusArea;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;

import java.util.Set;

/**
 * @author manyce400
 */
public interface ICurriculumProficiencyAssessmentService {


    public CurriculumProficiencyAssessment findByID(Long id);

    public CurriculumProficiencyAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);

    public void makeCurriculumProficiencyRankingAssessment(ELearningCurriculum eLearningCurriculum, Set<CourseAssessmentFocusArea> courseAssessmentFocusAreas);

    public void save(CurriculumProficiencyAssessment curriculumProficiencyAssessment);

}
