package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningExperienceRepository;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

/**
 * @RunWith annotation will initialize all Mockito mocks ahead of all test runs
 *
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class CumulativeAdaptiveProficiencyRankingAnalyticsServiceTest {


    @InjectMocks
    private CumulativeAdaptiveProficiencyRankingAnalyticsService cumulativeAdaptiveProficiencyRankingAnalyticsService;

    @Mock
    private IAdaptiveLearningExperienceRepository iAdaptiveLearningExperienceRepository;


    @Test
    public void testAverageAdaptiveLearningExperience() {
        // Build list of AdaptiveLearningExperience
        AdaptiveLearningExperience adaptiveLearningExperience1 = AdaptiveLearningExperience.builder().proficiencyScore(20d).build();
        AdaptiveLearningExperience adaptiveLearningExperience2 = AdaptiveLearningExperience.builder().proficiencyScore(40d).build();
        AdaptiveLearningExperience adaptiveLearningExperience3 = AdaptiveLearningExperience.builder().proficiencyScore(10d).build();
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of(adaptiveLearningExperience1, adaptiveLearningExperience2, adaptiveLearningExperience3);
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = cumulativeAdaptiveProficiencyRankingAnalyticsService.averageAdaptiveLearningExperience(adaptiveLearningExperiences);

        // Average of 20 + 40 + 10/ 2 = 23.333.  We expect this to be a FUNDAMENTALS_LACKING_PERFORMER
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());
    }

    @Test
    public void testAverageAdaptiveLearningExperienceWhenNoUserExperiencePresent() {
        // Validate what the ProficiencyScore range will be IF no Learning experiences present
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of();
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = cumulativeAdaptiveProficiencyRankingAnalyticsService.averageAdaptiveLearningExperience(adaptiveLearningExperiences);

        // IF no learning experience we will expect the user always to default to a BELOW_AVERAGE_PERFORMER
        Assert.assertEquals(ProficiencyScoreRangeE.BELOW_AVERAGE_PERFORMER, proficiencyScoreRangeE.get());
    }

    @Test
    public void testCalculateAdaptiveProficiencyRanking() {
        // Build list of AdaptiveLearningExperience
        AdaptiveLearningExperience adaptiveLearningExperience1 = AdaptiveLearningExperience.builder().proficiencyScore(20d).build();
        AdaptiveLearningExperience adaptiveLearningExperience2 = AdaptiveLearningExperience.builder().proficiencyScore(40d).build();
        AdaptiveLearningExperience adaptiveLearningExperience3 = AdaptiveLearningExperience.builder().proficiencyScore(10d).build();
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of(adaptiveLearningExperience1, adaptiveLearningExperience2, adaptiveLearningExperience3);

        // mock out call to repository object
        //Mockito.when(iAdaptiveLearningExperienceRepository.findAllQPalxUserCurriculumLearningExperiences(Mockito.any(), Mockito.any())).thenReturn(adaptiveLearningExperiences);

        // build test AdaptiveLearningProfile and ELearningCurriculum
//        AdaptiveLearningProfile adaptiveLearningProfile = AdaptiveLearningProfile.builder().qpalxUser(new QPalXUser()).build();
//        ELearningCurriculum eLearningCurriculum = new ELearningCurriculum();
//
//        AdaptiveProficiencyRanking adaptiveProficiencyRanking = cumulativeAdaptiveProficiencyRankingAnalyticsService.calculateAdaptiveProficiencyRanking(adaptiveLearningProfile, eLearningCurriculum);
//        Assert.assertNotNull(adaptiveProficiencyRanking);
//
//        // Average of 20 + 40 + 10/ 2 = 23.333.  We expect this to be a FUNDAMENTALS_LACKING_PERFORMER which will be a THREE on our scale of 1 to 10.
//        Assert.assertEquals(ProficiencyRankingScaleE.THREE, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
    }
}
