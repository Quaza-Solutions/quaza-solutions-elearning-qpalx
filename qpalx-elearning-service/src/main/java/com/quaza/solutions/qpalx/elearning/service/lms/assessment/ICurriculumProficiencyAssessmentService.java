package com.quaza.solutions.qpalx.elearning.service.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.ICourseAssessmentFocusAreaVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;

import java.util.Set;

/**
 * @author manyce400
 */
public interface ICurriculumProficiencyAssessmentService {


    public CurriculumProficiencyAssessment findByID(Long id);

    public CurriculumProficiencyAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);

    public CurriculumProficiencyAssessment makeCurriculumProficiencyRankingAssessment(ELearningCurriculum eLearningCurriculum, Set<ICourseAssessmentFocusAreaVO> iCourseAssessmentFocusAreaVOS);

    public void save(CurriculumProficiencyAssessment curriculumProficiencyAssessment);

}
