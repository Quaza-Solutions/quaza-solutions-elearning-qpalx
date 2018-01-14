package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.global;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm.ProficiencyAlgorithmExecutionInfo;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.service.councurrent.ApplicationConcurrencyConfig;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.DefaultAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm.IMultiplexAdaptiveProficiencyAlgorithm;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm.MultiplexAdaptiveProficiencyAlgorithm;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.IStudentCurriculumProgressService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.StudentCurriculumProgressService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(GlobalAdaptiveLearningService.BEAN_NAME)
public class GlobalAdaptiveLearningService implements IGlobalAdaptiveLearningService {



    @Autowired
    @Qualifier(MultiplexAdaptiveProficiencyAlgorithm.BEAN_NAME)
    private IMultiplexAdaptiveProficiencyAlgorithm iMultiplexAdaptiveProficiencyAlgorithm;

    @Autowired
    @Qualifier(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    @Autowired
    @Qualifier(StudentCurriculumProgressService.SPRING_BEAN_NAME)
    private IStudentCurriculumProgressService iStudentCurriculumProgressService;

    @Autowired
    @Qualifier(ApplicationConcurrencyConfig.BEAN_NAME)
    private ListeningExecutorService listeningExecutorService;


    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.global.GlobalAdaptiveLearningService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GlobalAdaptiveLearningService.class);


    // Compute global proficiency ranking on a specific curriculum and store results using a non blocking call.  Executed on different thread
    @Override
    public void computeAndTrackGlobalCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        computeAndTrackGlobalCurriculumProficiency(qPalXUser, eLearningCurriculum, ProficiencyRankingTriggerTypeE.ON_DEMAND);
    }

    @Override
    @Transactional
    public void computeAndTrackGlobalCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(proficiencyRankingTriggerTypeE, "proficiencyRankingTriggerTypeE cannot be null");

        Runnable runnable = () -> {
            // Check the students overall completion percent.  IF it falls within completion % thresholds then calculate new curriculum proficiency
            StudentOverallProgressStatistics studentOverallProgressStatistics = iStudentCurriculumProgressService.getStudentOverallProgressStatisticsInCurriculum(qPalXUser, eLearningCurriculum);
            double curriculumCompletionPercent = studentOverallProgressStatistics.getTotalCurriculumCompletionPercent();
            LOGGER.info("Student has currently Completed: {}% of Curriculum", curriculumCompletionPercent, eLearningCurriculum);

            if (curriculumCompletionPercent > 50.0) {
                LOGGER.info("Computing new AdaptiveProficiencyRanking for Student: {} in Curriculum: {} with proficiencyRankingTriggerTypeE: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName(), proficiencyRankingTriggerTypeE);

                // Get the current AdaptiveProficiencyRanking for this user in this Curriculum and then copy over users current proficiency ranking
                AdaptiveProficiencyRanking currentProficiencyRanking = iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);
                AdaptiveProficiencyRanking globalCurriculumProficiencyRanking =  buildAdaptiveProficiencyRanking(qPalXUser, eLearningCurriculum, proficiencyRankingTriggerTypeE);
                globalCurriculumProficiencyRanking.setProficiencyRankingScaleE(currentProficiencyRanking.getProficiencyRankingScaleE());

                List<ProficiencyAlgorithmExecutionInfo> proficiencyAlgorithmExecutionInfoList =  iMultiplexAdaptiveProficiencyAlgorithm.calculateAllAlgorithmScore(qPalXUser, eLearningCurriculum, globalCurriculumProficiencyRanking);
                LOGGER.info("Post Close Out:  After all computations new value of AdaptiveProficiencyRanking  proficiencyRankingScale: {}", globalCurriculumProficiencyRanking.getProficiencyRankingScaleE());
                iAdaptiveProficiencyRankingService.recordNew(globalCurriculumProficiencyRanking, currentProficiencyRanking);
            }
        };

        listeningExecutorService.submit(runnable);
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