package com.quaza.solutions.qpalx.elearning.service.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.CurriculumProficiencyRankingAssessment;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.ProficiencyRankingAssessmentFocusArea;
import com.quaza.solutions.qpalx.elearning.domain.lms.assessment.repository.ICurriculumProficiencyRankingAssessmentRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author manyce400
 */
@Service(CurriculumProficiencyRankingAssessmentService.SPRING_BEAN)
public class CurriculumProficiencyRankingAssessmentService implements ICurriculumProficiencyRankingAssessmentService {



    @Autowired
    private ICurriculumProficiencyRankingAssessmentRepository iCurriculumProficiencyRankingAssessmentRepository;

    public static final String SPRING_BEAN = "com.quaza.solutions.qpalx.elearning.service.lms.assessment.CurriculumProficiencyRankingAssessmentService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CurriculumProficiencyRankingAssessmentService.class);


    @Override
    public CurriculumProficiencyRankingAssessment findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding CurriculumProficiencyRankingAssessment with id: {}", id);
        return iCurriculumProficiencyRankingAssessmentRepository.findOne(id);
    }

    @Override
    public CurriculumProficiencyRankingAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        LOGGER.info("Finding CurriculumProficiencyRankingAssessment for eLearningCurriculum: {}", eLearningCurriculum);
        return iCurriculumProficiencyRankingAssessmentRepository.findByELearningCurriculum(eLearningCurriculum);
    }

    @Override
    public void buildAndSaveCurriculumProficiencyRankingAssessment(String assessmentTitle, ELearningCurriculum eLearningCurriculum, Set<ProficiencyRankingAssessmentFocusArea> proficiencyRankingAssessmentFocusAreas) {
        Assert.notNull(assessmentTitle, "assessmentTitle cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(proficiencyRankingAssessmentFocusAreas, "proficiencyRankingAssessmentFocusAreas cannot be null");

        LOGGER.info("Building and saving new CurriculumProficiencyRankingAssessmentService with assessmentTitle: {} in eLearningCurriculum: {}", assessmentTitle, eLearningCurriculum);

        CurriculumProficiencyRankingAssessment curriculumProficiencyRankingAssessment = CurriculumProficiencyRankingAssessment.builder()
                .assessmentTitle(assessmentTitle)
                .eLearningCurriculum(eLearningCurriculum)
                .entryDate(new DateTime())
                .build();

        for(ProficiencyRankingAssessmentFocusArea proficiencyRankingAssessmentFocusArea : proficiencyRankingAssessmentFocusAreas) {
            curriculumProficiencyRankingAssessment.addProficiencyRankingAssessmentFocusArea(proficiencyRankingAssessmentFocusArea);
        }

        save(curriculumProficiencyRankingAssessment);
    }

    @Override
    public void save(CurriculumProficiencyRankingAssessment curriculumProficiencyRankingAssessment) {
        Assert.notNull(curriculumProficiencyRankingAssessment, "curriculumProficiencyRankingAssessment cannot be null");
        LOGGER.info("Saving CurriculumProficiencyRankingAssessment: {}", curriculumProficiencyRankingAssessment);
        iCurriculumProficiencyRankingAssessmentRepository.save(curriculumProficiencyRankingAssessment);
    }

}
