package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.FactorAffectingProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingScoreModelE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingScoreModelRecommendation;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.AdaptiveLearningExperienceService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
@Service(AvgAdaptiveLearningExperiencesProficiencyRankingScoreModel.SPRING_BEAN_NAME)
public class AvgAdaptiveLearningExperiencesProficiencyRankingScoreModel implements IProficiencyRankingScoreModel {



    @Autowired
    @Qualifier(AdaptiveLearningExperienceService.SPRING_BEAN_NAME)
    private IAdaptiveLearningExperienceService iAdaptiveLearningExperienceService;

    @Autowired
    @Qualifier(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    public static final String SPRING_BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.AvgAdaptiveLearningExperiencesProficiencyRankingScoreModel";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AvgAdaptiveLearningExperiencesProficiencyRankingScoreModel.class);


    @Override
    public FactorAffectingProficiencyRanking computeAndUpdateProficiencyRanking(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(adaptiveProficiencyRanking, "adaptiveProficiencyRanking cannot be null");

        LOGGER.info("Computing Proficiency Ranking score using Model: AvgAdaptiveLearningExperiencesProficiencyRankingScoreModel");

        double currentAdaptiveRankingScore = iAdaptiveProficiencyRankingService.getAdaptiveProficiencyRankingMinScore(adaptiveProficiencyRanking);

        // Load all the list of user's adaptive learning for curriculum in order to calculate a new proficiency ranking
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = iAdaptiveLearningExperienceService.findAllAccrossELearningCurriculum(eLearningCurriculum, qPalXUser);
        if (adaptiveLearningExperiences != null && adaptiveLearningExperiences.size() > 0) {
            LOGGER.info("Found a list of {} AdaptiveLearningExperience's, using to calculate new ProficiencyScoreRange...", adaptiveLearningExperiences.size());
            double averageCurriculumProficiencyScore = averageAdaptiveLearningExperience(adaptiveLearningExperiences);

            // Build out the factors that impact proficiency ranking from this score model
            String scoreModelAnalysis = executeScoreModelAnalytics(averageCurriculumProficiencyScore);
            FactorAffectingProficiencyRanking factorAffectingProficiencyRanking = FactorAffectingProficiencyRanking.builder()
                    .factorScoreModelStrategy(ProficiencyRankingScoreModelE.AvgLearningExperiences)
                    .scoreModelPercent(averageCurriculumProficiencyScore)
                    .scoreModelAnalysis(scoreModelAnalysis)
                    .adaptiveProficiencyRanking(adaptiveProficiencyRanking)
                    .build();

            // Add recommendations that can help Student perform better in regards to this model
            executeScoreModelAnalyticsRecommendations(factorAffectingProficiencyRanking, adaptiveLearningExperiences);


            if(averageCurriculumProficiencyScore  > 0.0) {
                // calculate new weighted average using previous minimum score
                LOGGER.info("Computing new weighted Proficiency Score using averageCurriculumProficiencyScore: {} and currentAdaptiveRankingScore: {}", averageCurriculumProficiencyScore, currentAdaptiveRankingScore);

                double weightedProficiencyScore = (averageCurriculumProficiencyScore / 100) * currentAdaptiveRankingScore;
                double newProficiencyScoreWeightedWithCurrentAdaptiveScore = Precision.round(weightedProficiencyScore, 0);
                LOGGER.info("Weighted Proficiency Score calculated in model result: {}", newProficiencyScoreWeightedWithCurrentAdaptiveScore);

                Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(newProficiencyScoreWeightedWithCurrentAdaptiveScore);
                ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE.get()).get();
                adaptiveProficiencyRanking.setProficiencyRankingScaleE(proficiencyRankingScaleE);
            }

            return factorAffectingProficiencyRanking;
        }

        LOGGER.info("No Adaptive Learning Experiences found to compute an adequate score in this model, returning default factor on empty Learning experiences.");

        FactorAffectingProficiencyRanking factorAffectingProficiencyRankingWhenNoAdaptiveLearningExperiencesFound = getFactorAffectingProficiencyRankingWhenNoAdaptiveLearningExperiencesFound(adaptiveProficiencyRanking);
        return factorAffectingProficiencyRankingWhenNoAdaptiveLearningExperiencesFound;
    }


    double averageAdaptiveLearningExperience(List<AdaptiveLearningExperience> adaptiveLearningExperiences) {
        SummaryStatistics summaryStatistics = new SummaryStatistics();

        // Add up all the proficiency scores in order to calculate the average/mean.
        if(adaptiveLearningExperiences != null && adaptiveLearningExperiences.size() > 0) {
            adaptiveLearningExperiences.forEach(adaptiveLearningExperience -> {
                Double proficiencyScore = adaptiveLearningExperience.getProficiencyScore();
                summaryStatistics.addValue(proficiencyScore);
            });
        }

        double averageProficiencyScore = summaryStatistics.getMean();
        LOGGER.info("Calculated new average Learning Experiences Proficiency score:> {}", averageProficiencyScore);
        return Precision.round(averageProficiencyScore, 2);
    }

    String executeScoreModelAnalytics(double averageCurriculumProficiencyScore) {
        // check to see if score is above high range of average performer
        boolean aboveAvgRange = averageCurriculumProficiencyScore > ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER.getScoreRange().getMaximum();
        boolean scoreInAvgRange = ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER.getScoreRange().contains(averageCurriculumProficiencyScore);

        if(aboveAvgRange) {
            return new StringBuffer()
                    .append("Your performance on Quizzes is currently above average")
                    .toString();
        } else if(scoreInAvgRange) {
            return new StringBuffer()
                    .append("Your performance on Quizzes is currently average")
                    .toString();
        } else {
            return new StringBuffer()
                    .append("Your performance on Quizzes is currently below average")
                    .toString();
        }
    }

    Set<ProficiencyRankingScoreModelRecommendation> executeScoreModelAnalyticsRecommendations(FactorAffectingProficiencyRanking factorAffectingProficiencyRanking, List<AdaptiveLearningExperience> adaptiveLearningExperiences) {
        double averageCurriculumProficiencyScore = factorAffectingProficiencyRanking.getScoreModelPercent();
        LOGGER.info("Executing score model analytics recommendation for score: {}", averageCurriculumProficiencyScore);
        Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations = new HashSet<>();

        // Goal is to get the Students current average always above where they currently are
        Optional<ProficiencyScoreRangeE> optionalProficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(averageCurriculumProficiencyScore);

        if(optionalProficiencyScoreRangeE.isPresent()) {
            Optional<ProficiencyScoreRangeE> optionalProficiencyScoreRangeToTarget = optionalProficiencyScoreRangeE.get().getNextProficiencyScoreRangeUp();

            if(optionalProficiencyScoreRangeToTarget.isPresent()) {
                String recommendation = "Improve your current average Quizzes Score: " + averageCurriculumProficiencyScore;
                ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = ProficiencyRankingScoreModelRecommendation.builder()
                        .recommendation(recommendation)
                        .factorAffectingProficiencyRanking(factorAffectingProficiencyRanking)
                        .build();
                proficiencyRankingScoreModelRecommendations.add(proficiencyRankingScoreModelRecommendation);
                factorAffectingProficiencyRanking.addAllProficiencyRankingScoreModelRecommendation(proficiencyRankingScoreModelRecommendations);
            }

        }

        return proficiencyRankingScoreModelRecommendations;
    }


    FactorAffectingProficiencyRanking getFactorAffectingProficiencyRankingWhenNoAdaptiveLearningExperiencesFound(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        String scoreModelAnalysis = "No Learning Experiences found in Curriculum";

        // Default the proficiency ranking scale to lowest possible value
        iAdaptiveProficiencyRankingService.defaultToLowestProficiencyRanking(adaptiveProficiencyRanking);

        FactorAffectingProficiencyRanking factorAffectingProficiencyRanking = FactorAffectingProficiencyRanking.builder()
                .factorScoreModelStrategy(ProficiencyRankingScoreModelE.AvgLearningExperiences)
                .scoreModelPercent(adaptiveProficiencyRanking.getProficiencyRankingScaleE().getProficiencyScoreRangeE().getScoreRange().getMinimum())
                .scoreModelAnalysis(scoreModelAnalysis)
                .adaptiveProficiencyRanking(adaptiveProficiencyRanking)
                .build();

        // Create a recommendation for Student to attempt to take Quizzes in the Curriculum
        ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = ProficiencyRankingScoreModelRecommendation.builder()
                .recommendation("Attempt QPalX Quizzes and Question Bank activities in order to build up your learning experiences.")
                .factorAffectingProficiencyRanking(factorAffectingProficiencyRanking)
                .build();

        factorAffectingProficiencyRanking.addProficiencyRankingScoreModelRecommendation(proficiencyRankingScoreModelRecommendation);
        return factorAffectingProficiencyRanking;
    }


}
