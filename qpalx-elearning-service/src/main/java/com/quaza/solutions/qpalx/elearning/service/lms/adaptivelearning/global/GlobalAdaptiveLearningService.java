package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.global;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service()
public class GlobalAdaptiveLearningService implements IGlobalAdaptiveLearningService {



    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.global.GlobalAdaptiveLearningService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GlobalAdaptiveLearningService.class);


    @Override
    public void computeAndTrackGlobalCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        computeAndTrackGlobalCurriculumProficiency(qPalXUser, eLearningCurriculum, ProficiencyRankingTriggerTypeE.ON_DEMAND);
    }

    @Override
    public void computeAndTrackGlobalCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(proficiencyRankingTriggerTypeE, "proficiencyRankingTriggerTypeE cannot be null");

        LOGGER.info("Computing new AdaptiveProficiencyRanking for Student: {} in Curriculum: {} with proficiencyRankingTriggerTypeE: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName(), proficiencyRankingTriggerTypeE);

        AdaptiveProficiencyRanking adaptiveProficiencyRanking = buildAdaptiveProficiencyRanking(qPalXUser, eLearningCurriculum, proficiencyRankingTriggerTypeE);
    }

    protected AdaptiveProficiencyRanking buildAdaptiveProficiencyRanking(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE) {
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingEndDateTime(null) // null as this is just recorded new
                .proficiencyRankingTriggerTypeE(proficiencyRankingTriggerTypeE)
                .eLearningCurriculum(eLearningCurriculum)
                .qpalxUser(qPalXUser)
                .build();
        return adaptiveProficiencyRanking;
    }


}
