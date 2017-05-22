package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ProficiencyRankingAssessment;

/**
 * @author manyce400
 */
public interface IProficiencyRankingAssessmentService {


    public ProficiencyRankingAssessment findByID(Long id);

    public ProficiencyRankingAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);

    public void save(ProficiencyRankingAssessment proficiencyRankingAssessment);

}
