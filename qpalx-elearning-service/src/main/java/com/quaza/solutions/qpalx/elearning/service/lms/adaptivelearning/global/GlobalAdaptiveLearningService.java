package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.global;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.CurriculumCompletionThresholdE;
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
            LOGGER.info("Running global curriculum tracking statistics task for Student: {} across ELearningCurriculum: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName());

            // Get the current AdaptiveProficiencyRanking for this user in this Curriculum and then copy over users current proficiency ranking
            AdaptiveProficiencyRanking currentProficiencyRanking = iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);

            double curriculumCompletionPercent = calculateStudentCurriculumCompletion(qPalXUser, eLearningCurriculum);
            boolean hasStudentMetCompletionThresholdRequirements = hasStudentMetCompletionThresholdRequirements(curriculumCompletionPercent, currentProficiencyRanking);

            if (hasStudentMetCompletionThresholdRequirements) {
                AdaptiveProficiencyRanking globalCurriculumProficiencyRanking =  buildAdaptiveProficiencyRanking(curriculumCompletionPercent, qPalXUser, eLearningCurriculum, proficiencyRankingTriggerTypeE);
                globalCurriculumProficiencyRanking.setProficiencyRankingScaleE(currentProficiencyRanking.getProficiencyRankingScaleE());

                List<ProficiencyAlgorithmExecutionInfo> proficiencyAlgorithmExecutionInfoList =  iMultiplexAdaptiveProficiencyAlgorithm.calculateAllAlgorithmScore(qPalXUser, eLearningCurriculum, globalCurriculumProficiencyRanking);
                LOGGER.info("Post Close Out:  After all computations new value of AdaptiveProficiencyRanking  proficiencyRankingScale: {}", globalCurriculumProficiencyRanking.getProficiencyRankingScaleE());
                iAdaptiveProficiencyRankingService.recordNew(globalCurriculumProficiencyRanking, currentProficiencyRanking);
            } else {
                LOGGER.info("Student has not met curriculum completion threshold percent targets to compute and calculate AdaptiveProficiencyRanking");
            }
        };

        listeningExecutorService.submit(runnable);
    }

    protected AdaptiveProficiencyRanking buildAdaptiveProficiencyRanking(double curriculumCompletionPercent, QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE) {
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingEndDateTime(null) // null as this is just recorded new
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.COMPLETION_THRESH_HOLD)
                .eLearningCurriculum(eLearningCurriculum)
                .curriculumCompletionPercentage(curriculumCompletionPercent)
                .qpalxUser(qPalXUser)
                .build();
        return adaptiveProficiencyRanking;
    }

    double calculateStudentCurriculumCompletion(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        StudentOverallProgressStatistics studentOverallProgressStatistics = iStudentCurriculumProgressService.getStudentOverallProgressStatisticsInCurriculum(qPalXUser, eLearningCurriculum);
        double curriculumCompletionPercent = studentOverallProgressStatistics.getTotalCurriculumCompletionPercent();
        LOGGER.info("Student has currently completed {}% of ELearningCurriculum: {}", curriculumCompletionPercent, eLearningCurriculum.getCurriculumName());
        return curriculumCompletionPercent;
    }

    boolean hasStudentMetCompletionThresholdRequirements(double computedCurriculumCompletionPercent, AdaptiveProficiencyRanking currentProficiencyRanking) {
        double lastRecordedCompletion = currentProficiencyRanking.getCurriculumCompletionPercentage().doubleValue();
        boolean hasCompletedMoreItems = computedCurriculumCompletionPercent > lastRecordedCompletion;

        if(hasCompletedMoreItems) {
            // Get the next target rate completion based on the last recorded completion percentage
            CurriculumCompletionThresholdE curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(lastRecordedCompletion);
            LOGGER.info("Student's computedCurriculumCompletionPercent: {}  curriculumCompletionThresholdE: {}", computedCurriculumCompletionPercent, curriculumCompletionThresholdE);
            boolean hasMetOrCrossedCurriculumCompletionThresholdE = hasMetOrCrossedCurriculumCompletionThresholdE(computedCurriculumCompletionPercent, curriculumCompletionThresholdE);
            return hasMetOrCrossedCurriculumCompletionThresholdE;
        }

        return false;
    }


    boolean hasMetOrCrossedCurriculumCompletionThresholdE(double computedCurriculumCompletionPercent, CurriculumCompletionThresholdE curriculumCompletionThresholdE) {
        double thresholdPercent = curriculumCompletionThresholdE.getCompletionThreshold();

        if(computedCurriculumCompletionPercent >= thresholdPercent) {
            return true;
        }

        return false;
    }



}