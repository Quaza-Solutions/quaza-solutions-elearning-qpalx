package com.quaza.solutions.qpalx.elearning.domain.lms.assessment.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface ICurriculumProficiencyAssessmentRepository extends CrudRepository<CurriculumProficiencyAssessment, Long> {


    @Query("Select              curriculumProficiencyAssessment From CurriculumProficiencyAssessment curriculumProficiencyAssessment "+
            "Where              curriculumProficiencyAssessment.eLearningCurriculum =?1 "
    )
    public CurriculumProficiencyAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);

}
