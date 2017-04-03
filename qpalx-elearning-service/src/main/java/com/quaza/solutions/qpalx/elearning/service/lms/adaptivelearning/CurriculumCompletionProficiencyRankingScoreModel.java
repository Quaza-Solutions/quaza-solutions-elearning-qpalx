package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.FactorAffectingProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingScoreModelE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingScoreModelRecommendation;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.IStudentCurriculumProgressService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.StudentCurriculumProgressService;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
@Service(CurriculumCompletionProficiencyRankingScoreModel.SPRING_BEAN_NAME)
public class CurriculumCompletionProficiencyRankingScoreModel implements IProficiencyRankingScoreModel {




    @Autowired
    @Qualifier(StudentCurriculumProgressService.SPRING_BEAN_NAME)
    private IStudentCurriculumProgressService iStudentCurriculumProgressService;

    @Autowired
    @Qualifier(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;


    public static final String SPRING_BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.CurriculumCompletionProficiencyRankingScoreModel";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CurriculumCompletionProficiencyRankingScoreModel.class);



    @Override
    public FactorAffectingProficiencyRanking computeScore(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(adaptiveProficiencyRanking, "adaptiveProficiencyRanking cannot be null");

        LOGGER.info("Computing proficiency ranking model score for Student: {} in Curriculum: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName());

        // Get the Curriculum completion details for this student in the ELearningCurriculum
        StudentOverallProgressStatistics curriculumOverallProgress = iStudentCurriculumProgressService.getStudentOverallProgressStatisticsInCurriculum(qPalXUser, eLearningCurriculum);

        if (curriculumOverallProgress != null && curriculumOverallProgress.getTotalCurriculumCompletionPercent() > 0) {
            // Execute score model analysis and generate a textual description of that analysis
            String scoreModelAnalysis = executeScoreModelAnalytics(curriculumOverallProgress);

            // Adjust the currently computed score with Curriculum completion progress information
            double newProficiencyScoreWithProgress = curriculumOverallProgress.getTotalCurriculumCompletionPercent();

            double currentAdaptiveRankingScore = iAdaptiveProficiencyRankingService.getAdaptiveProficiencyRankingMinScore(adaptiveProficiencyRanking);

            if(adaptiveProficiencyRanking.getProficiencyRankingScaleE() != null) {
                LOGGER.info("Found previous score model ProficiencyRankingScaleE: {} defaulting to minimum score range on ranking", adaptiveProficiencyRanking.getProficiencyRankingScaleE());
                double weightedProficiencyScore = (currentAdaptiveRankingScore / 100) * curriculumOverallProgress.getTotalCurriculumCompletionPercent();
                newProficiencyScoreWithProgress = Precision.round(weightedProficiencyScore, 0);
            }

            // Override existing proficiency ranking with weighted value from this model
            Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(newProficiencyScoreWithProgress);
            ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE.get()).get();
            adaptiveProficiencyRanking.setProficiencyRankingScaleE(proficiencyRankingScaleE);

            FactorAffectingProficiencyRanking factorAffectingProficiencyRanking = FactorAffectingProficiencyRanking.builder()
                    .factorScoreModelStrategy(ProficiencyRankingScoreModelE.CurriculumCompletion)
                    .scoreModelPercent(curriculumOverallProgress.getTotalCurriculumCompletionPercent())
                    .scoreModelAnalysis(scoreModelAnalysis)
                    .adaptiveProficiencyRanking(adaptiveProficiencyRanking)
                    .build();

            // Execute score model recommendation analysis which will inform Students on how to improve their scores in relation to this model
            Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations = executeScoreModelAnalyticsRecommendations(curriculumOverallProgress, factorAffectingProficiencyRanking);
            factorAffectingProficiencyRanking.addAllScoreModelAnalyticsRecommendation(proficiencyRankingScoreModelRecommendations);
            return factorAffectingProficiencyRanking;
        } else {
            LOGGER.info("No recorded progress can be found for Student in Curriculum, building factors and recommendations..");
            FactorAffectingProficiencyRanking factorAffectingProficiencyRanking = getFactorAffectingProficiencyRankingWhenNoCurriculumProgressFound(adaptiveProficiencyRanking);
            return factorAffectingProficiencyRanking;
        }

    }


    String executeScoreModelAnalytics(StudentOverallProgressStatistics curriculumOverallProgress) {
        boolean scoreAboveAverage = ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER.getScoreRange().contains(curriculumOverallProgress.getTotalCurriculumCompletionPercent());

        if(scoreAboveAverage) {
            return new StringBuffer()
                    .append("Completion % is currently above average")
                    .toString();
        } else {
            return new StringBuffer()
                    .append("Completion % is currently below average")
                    .toString();
        }
    }


    Set<ProficiencyRankingScoreModelRecommendation> executeScoreModelAnalyticsRecommendations(StudentOverallProgressStatistics studentOverallProgressStatistics, FactorAffectingProficiencyRanking factorAffectingProficiencyRanking) {
        Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations = new HashSet<>();
        addMicroLessonsAttemptRecommendation(studentOverallProgressStatistics, factorAffectingProficiencyRanking, proficiencyRankingScoreModelRecommendations);
        addQuestionBankAttemptRecommendation(studentOverallProgressStatistics, factorAffectingProficiencyRanking, proficiencyRankingScoreModelRecommendations);
        addQuizzesAttemptRecommendation(studentOverallProgressStatistics, factorAffectingProficiencyRanking, proficiencyRankingScoreModelRecommendations);
        return proficiencyRankingScoreModelRecommendations;
    }


    FactorAffectingProficiencyRanking getFactorAffectingProficiencyRankingWhenNoCurriculumProgressFound(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        LOGGER.info("Building F for scenario where no Curriculum progress recorded for Student...");

        String scoreModelAnalysis = "No recorded attempts of QPalX Content Found!!";

        // Default Students ProficiencyRankingScaleE to worst possible value
        adaptiveProficiencyRanking.setProficiencyRankingScaleE(ProficiencyRankingScaleE.ONE);

        FactorAffectingProficiencyRanking factorAffectingProficiencyRanking = FactorAffectingProficiencyRanking.builder()
                .factorScoreModelStrategy(ProficiencyRankingScoreModelE.CurriculumCompletion)
                .scoreModelPercent(0d)
                .scoreModelAnalysis(scoreModelAnalysis)
                .adaptiveProficiencyRanking(adaptiveProficiencyRanking)
                .build();

        // Create a recommendation for Student to view MicroLessons, Quizzes and Question Banks.
        ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = ProficiencyRankingScoreModelRecommendation.builder()
                .recommendation("Attempt QPalX Content. Access MicroLessons, QuestionBanks and Take Quizzes.")
                .factorAffectingProficiencyRanking(factorAffectingProficiencyRanking)
                .build();
        factorAffectingProficiencyRanking.addScoreModelAnalyticsRecommendation(proficiencyRankingScoreModelRecommendation);
        return factorAffectingProficiencyRanking;
    }

    private void addMicroLessonsAttemptRecommendation(StudentOverallProgressStatistics studentOverallProgressStatistics, FactorAffectingProficiencyRanking factorAffectingProficiencyRanking, Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations) {
        ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = new ProficiencyRankingScoreModelRecommendation();

        int attemptedCount = studentOverallProgressStatistics.getUniqueMicroLessonsAttempted();
        int totalCount = studentOverallProgressStatistics.getTotalNumberOfMicroLessons();

        if(attemptedCount < totalCount) {
            StringBuffer sb = new StringBuffer()
                    .append(attemptedCount)
                    .append("/")
                    .append(totalCount)
                    .append(" MicroLessons attempted, complete remaining");
            proficiencyRankingScoreModelRecommendation.setRecommendation(sb.toString());
            proficiencyRankingScoreModelRecommendation.setFactorAffectingProficiencyRanking(factorAffectingProficiencyRanking);
            proficiencyRankingScoreModelRecommendations.add(proficiencyRankingScoreModelRecommendation);
        }
    }

    private void addQuestionBankAttemptRecommendation(StudentOverallProgressStatistics studentOverallProgressStatistics, FactorAffectingProficiencyRanking factorAffectingProficiencyRanking, Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations) {
        ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = new ProficiencyRankingScoreModelRecommendation();

        int attemptedCount = studentOverallProgressStatistics.getUniqueQuestionBankItemsAttempted();
        int totalCount = studentOverallProgressStatistics.getTotalNumberOfQuestionBankItems();

        if(attemptedCount < totalCount) {
            StringBuffer sb = new StringBuffer()
                    .append(attemptedCount)
                    .append("/")
                    .append(totalCount)
                    .append(" QuestionBank items attempted, complete remaining");

            proficiencyRankingScoreModelRecommendation.setRecommendation(sb.toString());
            proficiencyRankingScoreModelRecommendation.setFactorAffectingProficiencyRanking(factorAffectingProficiencyRanking);
            proficiencyRankingScoreModelRecommendations.add(proficiencyRankingScoreModelRecommendation);
        }
    }

    private void addQuizzesAttemptRecommendation(StudentOverallProgressStatistics studentOverallProgressStatistics, FactorAffectingProficiencyRanking factorAffectingProficiencyRanking, Set<ProficiencyRankingScoreModelRecommendation> proficiencyRankingScoreModelRecommendations) {
        ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = new ProficiencyRankingScoreModelRecommendation();

        int attemptedCount = studentOverallProgressStatistics.getUniqueQuizzesAttempted();
        int totalCount = studentOverallProgressStatistics.getTotalNumberOfQuizzes();

        if(attemptedCount < totalCount) {
            StringBuffer sb = new StringBuffer()
                    .append(attemptedCount)
                    .append("/")
                    .append(totalCount)
                    .append(" Quizzes attempted, complete remaining");

            proficiencyRankingScoreModelRecommendation.setRecommendation(sb.toString());
            proficiencyRankingScoreModelRecommendation.setFactorAffectingProficiencyRanking(factorAffectingProficiencyRanking);
            proficiencyRankingScoreModelRecommendations.add(proficiencyRankingScoreModelRecommendation);
        }
    }


}
