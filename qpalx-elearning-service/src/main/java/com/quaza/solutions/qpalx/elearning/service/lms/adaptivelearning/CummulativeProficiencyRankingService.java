package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.FactorAffectingProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository.IFactorAffectingProficiencyRankingRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manyce400
 */
@Service(CummulativeProficiencyRankingService.DEFAULT_SPRING_BEAN)
public class CummulativeProficiencyRankingService implements ICummulativeProficiencyRankingService {




    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService")
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;


    @Autowired
    private IFactorAffectingProficiencyRankingRepository iFactorAffectingProficiencyRankingRepository;

    @Autowired
    @Qualifier(CurriculumCompletionProficiencyRankingScoreModel.SPRING_BEAN_NAME)
    private IProficiencyRankingScoreModel curriculumCompletionProficiencyRankingScoreModel;

    @Autowired
    @Qualifier(AvgAdaptiveLearningExperiencesProficiencyRankingScoreModel.SPRING_BEAN_NAME)
    private IProficiencyRankingScoreModel avgAdaptiveLearningExepriencesProficiencyRankingScoreModel;


    public static final String DEFAULT_SPRING_BEAN = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.CummulativeProficiencyRankingService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CummulativeProficiencyRankingService.class);


    @Transactional
    @Override
    public List<FactorAffectingProficiencyRanking> computeAndSaveStudentAdaptiveProficiencyRankingInCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qpalxUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");

        List<FactorAffectingProficiencyRanking> factorAffectingProficiencyRankings = new ArrayList<>();

        LOGGER.info("Computing new Student ELearning proficiency ranking....");

        AdaptiveProficiencyRanking adaptiveProficiencyRanking = getNewAdaptiveProficiencyRanking(qPalXUser, eLearningCurriculum);

        // Execute and apply the Curriculum completion score for this curriculum
        FactorAffectingProficiencyRanking curriculumCompletionFactorAffectingProficiencyRanking = curriculumCompletionProficiencyRankingScoreModel.computeScore(qPalXUser, eLearningCurriculum, adaptiveProficiencyRanking);

        // Execute and apply the average Adaptive Learning experiences model score
        FactorAffectingProficiencyRanking avgAdaptiveLearningExperiencesFactorAffectingProficiencyRanking = avgAdaptiveLearningExepriencesProficiencyRankingScoreModel.computeScore(qPalXUser, eLearningCurriculum, adaptiveProficiencyRanking);

        if(curriculumCompletionFactorAffectingProficiencyRanking != null || avgAdaptiveLearningExperiencesFactorAffectingProficiencyRanking != null) {
            // IF any of the score models have computed new score details then close out current AdaptiveProficiencyRanking as we will be saving new
            closeOutCurrentAdaptiveProficiencyRanking(qPalXUser, eLearningCurriculum);
            iAdaptiveProficiencyRankingService.save(adaptiveProficiencyRanking);

            if(curriculumCompletionFactorAffectingProficiencyRanking != null) {
                factorAffectingProficiencyRankings.add(curriculumCompletionFactorAffectingProficiencyRanking);
                LOGGER.debug("Saving curriculumCompletionFactorAffectingProficiencyRanking: {}", curriculumCompletionFactorAffectingProficiencyRanking);
                iFactorAffectingProficiencyRankingRepository.save(curriculumCompletionFactorAffectingProficiencyRanking);
            }

            if(avgAdaptiveLearningExperiencesFactorAffectingProficiencyRanking != null) {
                factorAffectingProficiencyRankings.add(avgAdaptiveLearningExperiencesFactorAffectingProficiencyRanking);
                LOGGER.debug("Saving avgAdaptiveLearningExperiencesFactorAffectingProficiencyRanking: {}", avgAdaptiveLearningExperiencesFactorAffectingProficiencyRanking);
                iFactorAffectingProficiencyRankingRepository.save(avgAdaptiveLearningExperiencesFactorAffectingProficiencyRanking);
            }
        }

        LOGGER.info("Cannot calculate a new Cumulative Proficiency Ranking for Student: {}", qPalXUser.getEmail());
        return null;
    }


    AdaptiveProficiencyRanking getNewAdaptiveProficiencyRanking(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingEndDateTime(null) // null as this is just recorded new
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.CUMULATIVE)
                .eLearningCurriculum(eLearningCurriculum)
                .qpalxUser(qPalXUser)
                .build();
        return adaptiveProficiencyRanking;
    }


    void closeOutCurrentAdaptiveProficiencyRanking(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        // Find the current AdaptiveProficiencyRanking for this user in this curriculum and close it out
        LOGGER.info("Closing out current AdaptiveProficiency ranking for Student: {} in Curriculum: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName());
        AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking = iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);
        currentAdaptiveProficiencyRanking.setProficiencyRankingEndDateTime(new DateTime());
        iAdaptiveProficiencyRankingService.save(currentAdaptiveProficiencyRanking);
    }

}
