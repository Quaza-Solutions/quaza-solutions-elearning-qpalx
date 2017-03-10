package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningExperienceRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXUserBuilder;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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


    
    @Mock
    private ELearningCurriculum eLearningCurriculum;
    
    @Mock
    private IELearningCurriculumService ieLearningCurriculumService;

    @Mock
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    @Mock
    private IAdaptiveLearningExperienceRepository iAdaptiveLearningExperienceRepository;
    
    @Mock
    private IAdaptiveLearningExperienceService iAdaptiveLearningExperienceService;

    @InjectMocks
    private CumulativeAdaptiveProficiencyRankingAnalyticsService cumulativeAdaptiveProficiencyRankingAnalyticsService;


    @Test
    public void testAverageAdaptiveLearningExperience() {
        // Build list of AdaptiveLearningExperience
        AdaptiveLearningExperience adaptiveLearningExperience1 = AdaptiveLearningExperience.builder().proficiencyScore(20d).build();
        AdaptiveLearningExperience adaptiveLearningExperience2 = AdaptiveLearningExperience.builder().proficiencyScore(40d).build();
        AdaptiveLearningExperience adaptiveLearningExperience3 = AdaptiveLearningExperience.builder().proficiencyScore(10d).build();
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of(adaptiveLearningExperience1, adaptiveLearningExperience2, adaptiveLearningExperience3);
        
        // Buid a current AdaptiveProficiencyRanking and set ProficiencyRankingScaleE to FIVE(Range: 41d, 50d).
        QPalXUser mockUser = MockQPalXUserBuilder.buildMockGHQPalXUserBuilder();
        AdaptiveProficiencyRanking currentCurriculumAdaptiveProficiencyRanking =  AdaptiveProficiencyRanking.builder()
                .qpalxUser(mockUser)
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.CUMULATIVE)
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingScaleE(ProficiencyRankingScaleE.FIVE)
                .eLearningCurriculum(eLearningCurriculum)
                .build();


        // Algorithm will get the minimum value of ProficiencyRankingScaleE FIVE(Range: 41d, 50d) which will be 41.
        double proficiencyScoreRange = cumulativeAdaptiveProficiencyRankingAnalyticsService.averageAdaptiveLearningExperience(adaptiveLearningExperiences, currentCurriculumAdaptiveProficiencyRanking);
        Assert.assertEquals(new Double(27.75), new Double(proficiencyScoreRange));
        System.out.println("proficiencyScoreRangeE = " + proficiencyScoreRange);
        
        // Average of (41 + 20 + 40 + 10 / 4) = 27.75.  We expect this to be a FUNDAMENTALS_LACKING_PERFORMER
        Optional<ProficiencyScoreRangeE> expectedProficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(proficiencyScoreRange);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, expectedProficiencyScoreRangeE.get());
    }

    @Test
    public void testCalculateStudentAverageCurriculumProficiency() {
        QPalXUser mockUser = MockQPalXUserBuilder.buildMockGHQPalXUserBuilder(); 

        AdaptiveProficiencyRanking currentCurriculumAdaptiveProficiencyRanking =  AdaptiveProficiencyRanking.builder()
                .qpalxUser(mockUser)
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.CUMULATIVE)
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingScaleE(ProficiencyRankingScaleE.FIVE)
                .eLearningCurriculum(eLearningCurriculum)
                .build();

        AdaptiveLearningExperience adaptiveLearningExperience1 = AdaptiveLearningExperience.builder().proficiencyScore(20d).build();
        AdaptiveLearningExperience adaptiveLearningExperience2 = AdaptiveLearningExperience.builder().proficiencyScore(40d).build();
        AdaptiveLearningExperience adaptiveLearningExperience3 = AdaptiveLearningExperience.builder().proficiencyScore(10d).build();
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of(adaptiveLearningExperience1, adaptiveLearningExperience2, adaptiveLearningExperience3);

        Mockito.when(iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(mockUser, eLearningCurriculum)).thenReturn(currentCurriculumAdaptiveProficiencyRanking);
        Mockito.when(iAdaptiveLearningExperienceService.findAllAccrossELearningCurriculum(eLearningCurriculum, mockUser)).thenReturn(adaptiveLearningExperiences);


        Double result = cumulativeAdaptiveProficiencyRankingAnalyticsService.calculateStudentAverageCurriculumProficiency(mockUser, eLearningCurriculum);
        Assert.assertEquals(new Double(27.75), result);

        Optional<ProficiencyScoreRangeE> expectedProficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(result);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, expectedProficiencyScoreRangeE.get());
        System.out.println("result = " + result);
    }

    @Test
    public void testCalculateStudentProficiencyWithProgress() {
        StudentOverallProgressStatistics studentOverallProgressStatistics = new StudentOverallProgressStatistics(
                1L, 1L, "Test Curriculum", "Test Description", "", "CORE",
                10, 20, 20,10,10, 10, 10
        );

        // Expect completion percent to be 60.0%
        double completionPercent = studentOverallProgressStatistics.getTotalCurriculumCompletionPercent();
        Assert.assertEquals(new Double(60.0), new Double(completionPercent));

        QPalXUser mockUser = MockQPalXUserBuilder.buildMockGHQPalXUserBuilder();

        AdaptiveProficiencyRanking currentCurriculumAdaptiveProficiencyRanking =  AdaptiveProficiencyRanking.builder()
                .qpalxUser(mockUser)
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.CUMULATIVE)
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingScaleE(ProficiencyRankingScaleE.FIVE)
                .eLearningCurriculum(eLearningCurriculum)
                .build();

        AdaptiveLearningExperience adaptiveLearningExperience1 = AdaptiveLearningExperience.builder().proficiencyScore(20d).build();
        AdaptiveLearningExperience adaptiveLearningExperience2 = AdaptiveLearningExperience.builder().proficiencyScore(40d).build();
        AdaptiveLearningExperience adaptiveLearningExperience3 = AdaptiveLearningExperience.builder().proficiencyScore(10d).build();
        List<AdaptiveLearningExperience> adaptiveLearningExperiences = ImmutableList.of(adaptiveLearningExperience1, adaptiveLearningExperience2, adaptiveLearningExperience3);

        Mockito.when(ieLearningCurriculumService.findByELearningCurriculumID(Mockito.anyLong())).thenReturn(eLearningCurriculum);
        Mockito.when(iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(mockUser, eLearningCurriculum)).thenReturn(currentCurriculumAdaptiveProficiencyRanking);
        Mockito.when(iAdaptiveLearningExperienceService.findAllAccrossELearningCurriculum(eLearningCurriculum, mockUser)).thenReturn(adaptiveLearningExperiences);

        // New Proficiency score with progress will be 17.0 which should equate to a ProficiencyRankingScaleE.TWO level
        AdaptiveProficiencyRanking newAdaptiveProficiencyRanking = cumulativeAdaptiveProficiencyRankingAnalyticsService.calculateStudentProficiencyWithProgress(mockUser, studentOverallProgressStatistics);
        Assert.assertNotNull(newAdaptiveProficiencyRanking);
        Assert.assertEquals(ProficiencyRankingScaleE.TWO, newAdaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertNotNull(newAdaptiveProficiencyRanking.getProficiencyRankingEffectiveDateTime());
        Assert.assertNull(newAdaptiveProficiencyRanking.getProficiencyRankingEndDateTime()); // ranking date end DateTime has to be null since this just got calculated.
        Assert.assertEquals(ProficiencyRankingTriggerTypeE.CUMULATIVE, newAdaptiveProficiencyRanking.getProficiencyRankingTriggerTypeE());
        Assert.assertEquals(eLearningCurriculum, newAdaptiveProficiencyRanking.geteLearningCurriculum());
        Assert.assertEquals(mockUser, newAdaptiveProficiencyRanking.getQpalxUser());

    }
}
