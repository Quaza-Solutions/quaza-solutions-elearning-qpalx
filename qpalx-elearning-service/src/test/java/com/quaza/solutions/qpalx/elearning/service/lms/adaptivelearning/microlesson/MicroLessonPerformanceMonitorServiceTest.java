package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.DefaultAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizStatisticsService;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class MicroLessonPerformanceMonitorServiceTest {



    @Mock
    private IAdaptiveLearningQuizStatisticsService iAdaptiveLearningQuizStatisticsService;


    @Spy
    private DefaultAdaptiveProficiencyRankingService defaultAdaptiveProficiencyRankingService = new DefaultAdaptiveProficiencyRankingService(){
        @Override
        public AdaptiveProficiencyRanking findCurrentStudentAdaptiveProficiencyRankingForCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
            // Student current proficiency in Curriculum will be THREE
            AdaptiveProficiencyRanking curriculumProficiencyRanking = AdaptiveProficiencyRanking.builder()
                    .proficiencyRankingScaleE(ProficiencyRankingScaleE.THREE)
                    .build();
            return curriculumProficiencyRanking;
        }
    };

    @InjectMocks
    private MicroLessonPerformanceMonitorService microLessonPerformanceMonitorService;


    @Test
    public void testCalculateQuizAvgScoreWithMinProficiencyWithNoQuizStatistics() {
        List<AdaptiveLessonQuizStatistics> results = ImmutableList.of();
        double avgScore = microLessonPerformanceMonitorService.calculateQuizAvgScoreWithMinProficiency(results, ProficiencyRankingScaleE.THREE);
        Assert.assertTrue(avgScore > 0);

        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(avgScore);
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());

        Optional<ProficiencyRankingScaleE> computedProficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE.get());
        Assert.assertTrue(computedProficiencyRankingScaleE.isPresent());

        Assert.assertEquals(ProficiencyRankingScaleE.THREE, computedProficiencyRankingScaleE.get());
    }

    @Test
    public void testCalculateAdaptiveProficiencyRankingNoQuizAttempts() {
        // Create 3 Mock AdaptiveLessonQuizStatistics for a Lesson with Proficiency From(One to Three).  Simulate a situation where we have 3 Quizzes but no Quiz attempts have been made.
        // Because we have quizzes with no attempts, Student will be penalized here and average score will be calculated using all 0's
        mockAdaptiveLessonQuizStatisticsWithNoQuizAttempts(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE, 3);

        // In this scenario, Student is underperforming Curriculum as no quizzes have been attempted.  Proficiency ranking will drop to ONE
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = microLessonPerformanceMonitorService.calculateAdaptiveProficiencyRanking(QPalXUser.builder().build(), ELearningCourse.builder().build());
        Assert.assertNotNull(adaptiveProficiencyRanking);
        System.out.println("adaptiveProficiencyRanking = " + adaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertEquals(ProficiencyRankingScaleE.ONE, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
    }

    @Test
    public void testCalculateAdaptiveProficiencyRankingWithQuizScores() {
        // Mock ELearning course that will return AdaptiveLessonQuizStatistics with no proficiency scores.
        // Note that we have to set QuizID on the statistics for method to trigger calculation using real scores
        mockAdaptiveLessonQuizStatisticsWithQuizAttempts(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE, 80, 20, 70);

        // This scenario we have 2 scores 100 and 80 with average of 90 so we expect proficiency to be EIGHT
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = microLessonPerformanceMonitorService.calculateAdaptiveProficiencyRanking(QPalXUser.builder().build(), ELearningCourse.builder().build());
        Assert.assertNotNull(adaptiveProficiencyRanking);
        System.out.println("adaptiveProficiencyRanking = " + adaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertEquals(ProficiencyRankingScaleE.FIVE, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
    }

    @Test
    public void testCalculateAdaptiveProficiencyRankingWithAndWithoughtQuizScores() {
        // Scenario:  We build 3 statistics with quiz scores and attempts and other 3 with no quiz attempts.  Student will get penalized on the 3 items that they havent' attempted in the average calculation
        mockAdaptiveLessonQuizStatisticsWithAndWithoughtQuizAttempts(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE, 80, 20, 70);

        // Average calculation will be(80 + 20 + 70 + 0 + 0 + 0) / 6.  Zeros represent the three quizzes that Student did not attempt
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = microLessonPerformanceMonitorService.calculateAdaptiveProficiencyRanking(QPalXUser.builder().build(), ELearningCourse.builder().build());
        System.out.println("adaptiveProficiencyRanking.getProficiencyRankingScaleE() = " + adaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertNotNull(adaptiveProficiencyRanking);
        Assert.assertEquals(ProficiencyRankingScaleE.TWO, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
    }


    private List<AdaptiveLessonQuizStatistics> mockAdaptiveLessonQuizStatisticsWithNoQuizAttempts(ProficiencyRankingScaleE floor, ProficiencyRankingScaleE ceiling, int numberToBuild) {
        List<AdaptiveLessonQuizStatistics> results = new ArrayList<>();
        for(int i=1; i <= numberToBuild; i++) {
            AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics = buildAdaptiveLessonQuizStatistics(floor, ceiling, 0, NumberUtils.toLong(""+i));
            results.add(adaptiveLessonQuizStatistics);
        }

        Mockito.when(iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(Mockito.any(), Mockito.any())).thenReturn(results);
        return results;
    }

    // Builds only 3 Quiz Attempts
    private void mockAdaptiveLessonQuizStatisticsWithQuizAttempts(ProficiencyRankingScaleE floor, ProficiencyRankingScaleE ceiling, double quizScore1, double quizScore2, double quizScore3) {
        List<AdaptiveLessonQuizStatistics> results = new ArrayList<>();

        double quizScore = 0;
        for(int i=1; i <= 3; i++) {
            if(i == 1) quizScore = quizScore1;
            if(i == 2) quizScore = quizScore2;
            if(i == 3) quizScore = quizScore3;
            AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics = buildAdaptiveLessonQuizStatistics(floor, ceiling, quizScore, NumberUtils.toLong(""+i));
            results.add(adaptiveLessonQuizStatistics);
        }

        Mockito.when(iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(Mockito.any(), Mockito.any())).thenReturn(results);
    }

    // Builds with both Quiz attempts and no attempts all in scope
    private void mockAdaptiveLessonQuizStatisticsWithAndWithoughtQuizAttempts(ProficiencyRankingScaleE floor, ProficiencyRankingScaleE ceiling, double quizScore1, double quizScore2, double quizScore3) {
        List<AdaptiveLessonQuizStatistics> results = new ArrayList<>();

        // First build with quiz attempts
        double quizScore = 0;
        for(int i=1; i <= 3; i++) {
            if(i == 1) quizScore = quizScore1;
            if(i == 2) quizScore = quizScore2;
            if(i == 3) quizScore = quizScore3;
            AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics = buildAdaptiveLessonQuizStatistics(floor, ceiling, quizScore, NumberUtils.toLong(""+i));
            results.add(adaptiveLessonQuizStatistics);
        }

        // now add with no attempts
        for(int i=1; i <= 3; i++) {
            AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics = buildAdaptiveLessonQuizStatistics(floor, ceiling, 0, NumberUtils.toLong(""+i));
            results.add(adaptiveLessonQuizStatistics);
        }

        Mockito.when(iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(Mockito.any(), Mockito.any())).thenReturn(results);
    }


    public AdaptiveLessonQuizStatistics buildAdaptiveLessonQuizStatistics(ProficiencyRankingScaleE floor, ProficiencyRankingScaleE ceiling, double quizScore, long quizID) {
        AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics = new AdaptiveLessonQuizStatistics();
        adaptiveLessonQuizStatistics.setProficiencyRankingScaleFloor(floor);
        adaptiveLessonQuizStatistics.setProficiencyRankingScaleCeiling(ceiling);
        adaptiveLessonQuizStatistics.setProficiencyScore(quizScore);
        adaptiveLessonQuizStatistics.setAdaptiveLearningQuizID(quizID);
        return adaptiveLessonQuizStatistics;
    }


}
