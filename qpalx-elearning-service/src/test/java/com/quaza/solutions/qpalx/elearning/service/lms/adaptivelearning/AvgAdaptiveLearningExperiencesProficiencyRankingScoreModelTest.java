package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.FactorAffectingProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingScoreModelE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingScoreModelRecommendation;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class AvgAdaptiveLearningExperiencesProficiencyRankingScoreModelTest {



    @Mock
    private IAdaptiveLearningExperienceService iAdaptiveLearningExperienceService;

    @InjectMocks
    private AvgAdaptiveLearningExperiencesProficiencyRankingScoreModel avgAdaptiveLearningExperiencesProficiencyRankingScoreModel;


    @Test
    public void testAverageAdaptiveLearningExperience() {
        AdaptiveLearningExperience adaptiveLearningExperience1 = AdaptiveLearningExperience.builder().proficiencyScore(20d).build();
        AdaptiveLearningExperience adaptiveLearningExperience2 = AdaptiveLearningExperience.builder().proficiencyScore(40d).build();
        AdaptiveLearningExperience adaptiveLearningExperience3 = AdaptiveLearningExperience.builder().proficiencyScore(10d).build();
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of(adaptiveLearningExperience1, adaptiveLearningExperience2, adaptiveLearningExperience3);

        // Algorithm will get the minimum value of ProficiencyRankingScaleE FIVE(Range: 41d, 50d) which will be 41.
        double proficiencyScoreRange = avgAdaptiveLearningExperiencesProficiencyRankingScoreModel.averageAdaptiveLearningExperience(adaptiveLearningExperiences);
        Assert.assertEquals(new Double(23.33), new Double(proficiencyScoreRange));

        // Average of (20 + 40 + 10 / 3) = 23.33.  We expect this to be a FUNDAMENTALS_LACKING_PERFORMER
        Optional<ProficiencyScoreRangeE> expectedProficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(proficiencyScoreRange);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, expectedProficiencyScoreRangeE.get());
    }

    @Test
    public void testExecuteScoreModelAnalytics() {
        double proficiencyScoreRange = 23.3;
        String analytics = avgAdaptiveLearningExperiencesProficiencyRankingScoreModel.executeScoreModelAnalytics(proficiencyScoreRange);
        Assert.assertEquals("Your performance on Quizzes is currently below average", analytics);
    }
    
    @Test
    public void testExecuteScoreModelAnalyticsRecommendations() {
        // Create learning experiencies
        AdaptiveLearningExperience adaptiveLearningExperience1 = AdaptiveLearningExperience.builder().proficiencyScore(20d).build();
        AdaptiveLearningExperience adaptiveLearningExperience2 = AdaptiveLearningExperience.builder().proficiencyScore(40d).build();
        AdaptiveLearningExperience adaptiveLearningExperience3 = AdaptiveLearningExperience.builder().proficiencyScore(10d).build();
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of(adaptiveLearningExperience1, adaptiveLearningExperience2, adaptiveLearningExperience3);
        
        // Get average score range
        double proficiencyScoreRange = avgAdaptiveLearningExperiencesProficiencyRankingScoreModel.averageAdaptiveLearningExperience(adaptiveLearningExperiences);
        FactorAffectingProficiencyRanking factorAffectingProficiencyRanking = FactorAffectingProficiencyRanking.builder()
                .factorScoreModelStrategy(ProficiencyRankingScoreModelE.CurriculumCompletion)
                .scoreModelPercent(proficiencyScoreRange) 
                .build();

        Set<ProficiencyRankingScoreModelRecommendation> recommendations = avgAdaptiveLearningExperiencesProficiencyRankingScoreModel.executeScoreModelAnalyticsRecommendations(factorAffectingProficiencyRanking, adaptiveLearningExperiences);
        Assert.assertTrue(recommendations.size() == 1);

        ProficiencyRankingScoreModelRecommendation proficiencyRankingScoreModelRecommendation = recommendations.iterator().next();
        Assert.assertEquals("Improve your current average Quizzes Score: 23.33", proficiencyRankingScoreModelRecommendation.getRecommendation());
    }
}
