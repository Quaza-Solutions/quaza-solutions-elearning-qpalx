package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.Precision;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Caculates a total proficiency ranking for a QPalX Student cumulatively looking at the total set of all user's Adaptive Learning
 * Experiences across specific ELearningCurriculum.
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.CumulativeAdaptiveProficiencyRankingAnalyticsService")
public class CumulativeAdaptiveProficiencyRankingAnalyticsService implements IAdaptiveProficiencyRankingAnalyticsService {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningExperienceService")
    private IAdaptiveLearningExperienceService iAdaptiveLearningExperienceService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService")
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService ieLearningCurriculumService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CumulativeAdaptiveProficiencyRankingAnalyticsService.class);


    @Override
    public List<AdaptiveProficiencyRanking> calculateAdaptiveProficiencyRanking(QPalXUser qPalXUser) {
        return null;
    }

    @Override
    public AdaptiveProficiencyRanking calculateStudentProficiencyWithProgress(QPalXUser qPalXUser, StudentOverallProgressStatistics studentOverallProgressStatistics) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(studentOverallProgressStatistics, "studentOverallProgressStatistics cannot be null");

        LOGGER.info("Calculating proficiency ranking for student:> {} with overallProgress:> {}", qPalXUser.getEmail(), studentOverallProgressStatistics);

        // Load the curriculum for studentOverallProgressStatistics
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumID(studentOverallProgressStatistics.getCurriculumID());
        Double averageCurriculumProficiencyScore = calculateStudentAverageCurriculumProficiency(qPalXUser, eLearningCurriculum);

        Object[] avgScoreLogParams = new Object[]{averageCurriculumProficiencyScore, eLearningCurriculum.getCurriculumName(), qPalXUser.getEmail()};
        LOGGER.info("Computed average proficiency score: {} in ELearningCurriculum: {} for student: {}", avgScoreLogParams);

        if(averageCurriculumProficiencyScore != null && averageCurriculumProficiencyScore.doubleValue() > 0) {
            double curriclumCompletionPercent = studentOverallProgressStatistics.getTotalCurriculumCompletionPercent();
            LOGGER.info("Compted completion percent in ELearningCurriculum: {} for student: {}", eLearningCurriculum.getCurriculumName(), qPalXUser.getEmail());

            double weightedProficiencyScore = (averageCurriculumProficiencyScore.doubleValue() / 100) * curriclumCompletionPercent;
            double newProficiencyScoreWithProgress = Precision.round(weightedProficiencyScore, 0);
            Object[] weightedScoreLogParams = new Object[]{newProficiencyScoreWithProgress, eLearningCurriculum.getCurriculumName(), qPalXUser.getEmail()};
            LOGGER.info("Computed new weighted proficiency score: {} in ELearningCurriculum: {} for student: {}", weightedScoreLogParams);
            System.out.println("newProficiencyScoreWithProgress = " + newProficiencyScoreWithProgress);

            Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(newProficiencyScoreWithProgress);
            ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE.get()).get();


            AdaptiveProficiencyRanking newQPalxUserAdaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingScaleE(proficiencyRankingScaleE)
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingEndDateTime(null) // null as this is just recorded new
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.CUMULATIVE)
                .eLearningCurriculum(eLearningCurriculum)
                .qpalxUser(qPalXUser)
                .build();

            return newQPalxUserAdaptiveProficiencyRanking;
        }

        return null;
    }

    @Override
    public Double calculateStudentAverageCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");

        LOGGER.info("Calculating new AdaptiveProficiencyRanking for qPalxUser: {} and eLearningCurriculum: {}", qPalXUser.getEmail(), eLearningCurriculum);

        // Find the current AdaptiveProficiencyRanking this Student has for this Curriculum
        AdaptiveProficiencyRanking currentCurriculumAdaptiveProficiencyRanking = iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);

        // Load all the list of user's adaptive learning for curriculum in order to calculate a new proficiency ranking
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = iAdaptiveLearningExperienceService.findAllAccrossELearningCurriculum(eLearningCurriculum, qPalXUser);
        Double averageCurriculumProficiencyScore = null;

        if (adaptiveLearningExperiences != null && adaptiveLearningExperiences.size() > 0) {
            LOGGER.info("Found a list of {} AdaptiveLearningExperience's, using to calculate new ProficiencyScoreRange...", adaptiveLearningExperiences.size());
            averageCurriculumProficiencyScore = averageAdaptiveLearningExperience(adaptiveLearningExperiences, currentCurriculumAdaptiveProficiencyRanking);
        } else {
            LOGGER.info("No AdaptiveLearningExperience's found for user: {} cannot calculate new ProficiencyScoreRange", qPalXUser.getEmail());
        }

        return averageCurriculumProficiencyScore;
    }

    double averageAdaptiveLearningExperience(List<AdaptiveLearningExperience> adaptiveLearningExperiences, AdaptiveProficiencyRanking currentCurriculumAdaptiveProficiencyRanking) {
        Double defaultExperienceScoreAvg = (Double) ProficiencyScoreRangeE.BELOW_AVERAGE_PERFORMER.getScoreRange().getMinimum();
        SummaryStatistics summaryStatistics = new SummaryStatistics();
        if (currentCurriculumAdaptiveProficiencyRanking != null) {
            Double minScore = (Double) currentCurriculumAdaptiveProficiencyRanking.getProficiencyRankingScaleE().getProficiencyScoreRangeE().getScoreRange().getMinimum();
            summaryStatistics.addValue(minScore);
        }

        // Add up all the proficiency scores in order to calculate the average/mean.
        if(adaptiveLearningExperiences != null && adaptiveLearningExperiences.size() > 0) {
            adaptiveLearningExperiences.forEach(adaptiveLearningExperience -> {
                Double proficiencyScore = adaptiveLearningExperience.getProficiencyScore();
                summaryStatistics.addValue(proficiencyScore);
            });
        }

        double averageProficiencyScore = summaryStatistics.getMean();
        LOGGER.debug("Calculated new Proficiency score:> {}", averageProficiencyScore);
        return averageProficiencyScore;
    }

}
