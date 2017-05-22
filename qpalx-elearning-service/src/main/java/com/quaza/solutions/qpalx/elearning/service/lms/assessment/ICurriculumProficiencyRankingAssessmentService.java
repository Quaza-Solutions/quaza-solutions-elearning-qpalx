package com.quaza.solutions.qpalx.elearning.service.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyRankingAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.ProficiencyRankingAssessmentFocusArea;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;

import java.util.Set;

/**
 * @author manyce400
 */
public interface ICurriculumProficiencyRankingAssessmentService {


    public CurriculumProficiencyRankingAssessment findByID(Long id);

    public CurriculumProficiencyRankingAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);

    public void buildAndSaveCurriculumProficiencyRankingAssessment(String assessmentTitle, ELearningCurriculum eLearningCurriculum, Set<ProficiencyRankingAssessmentFocusArea> proficiencyRankingAssessmentFocusAreas);

    public void save(CurriculumProficiencyRankingAssessment curriculumProficiencyRankingAssessment);

}
