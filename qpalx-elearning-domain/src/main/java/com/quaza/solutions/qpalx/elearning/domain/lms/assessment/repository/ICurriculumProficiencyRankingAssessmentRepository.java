package com.quaza.solutions.qpalx.elearning.domain.lms.assessment.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyRankingAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface ICurriculumProficiencyRankingAssessmentRepository extends CrudRepository<CurriculumProficiencyRankingAssessment, Long> {


    @Query("Select              curriculumProficiencyRankingAssessment From CurriculumProficiencyRankingAssessment curriculumProficiencyRankingAssessment "+
            "Where              curriculumProficiencyRankingAssessment.eLearningCurriculum =?1 "
    )
    public CurriculumProficiencyRankingAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);

}
