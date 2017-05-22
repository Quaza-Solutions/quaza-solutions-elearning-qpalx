package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ProficiencyRankingAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service(ProficiencyRankingAssessmentService.SPRING_BEAN)
public class ProficiencyRankingAssessmentService implements IProficiencyRankingAssessmentService {



    @Autowired
    private IProficiencyRankingAssessmentService iProficiencyRankingAssessmentService;

    public static final String SPRING_BEAN = "com.quaza.solutions.qpalx.elearning.service.lms.curriculum.ProficiencyRankingAssessmentService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ProficiencyRankingAssessmentService.class);


    @Override
    public ProficiencyRankingAssessment findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding ProficiencyRankingAssessment with id: {}", id);
        return iProficiencyRankingAssessmentService.findByID(id);
    }

    @Override
    public ProficiencyRankingAssessment findByELearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        LOGGER.info("Finding ProficiencyRankingAssessment for eLearningCurriculum: {}", eLearningCurriculum);
        return iProficiencyRankingAssessmentService.findByELearningCurriculum(eLearningCurriculum);
    }

    @Override
    public void save(ProficiencyRankingAssessment proficiencyRankingAssessment) {
        Assert.notNull(proficiencyRankingAssessment, "proficiencyRankingAssessment cannot be null");
        LOGGER.info("Saving ProficiencyRankingAssessment: {}", proficiencyRankingAssessment);
        iProficiencyRankingAssessmentService.save(proficiencyRankingAssessment);
    }

}
