package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizStatisticsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class MicroLessonPerformanceMonitorServiceTest {



    @Mock
    private IAdaptiveLearningQuizStatisticsService iAdaptiveLearningQuizStatisticsService;

    @InjectMocks
    private MicroLessonPerformanceMonitorService microLessonPerformanceMonitorService;


    @Test
    public void testCalculateAdaptiveProficiencyRankingNoQuizScores() {
        // Mock ELearning course that will return AdaptiveLessonQuizStatistics with no proficiency scores.
        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = ImmutableList.of(new AdaptiveLessonQuizStatistics(), new AdaptiveLessonQuizStatistics(), new AdaptiveLessonQuizStatistics());
        Mockito.when(iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(Mockito.any(), Mockito.any())).thenReturn(adaptiveLessonQuizStatistics);

        // This test simulates a situation where we find AdaptiveLessonQuizStatistics but no proficiency scores bcos student has not taken the matching ML quizzes yet, system will then default proficiency to THREE
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = microLessonPerformanceMonitorService.calculateAdaptiveProficiencyRanking(QPalXUser.builder().build(), ELearningCourse.builder().build());
        Assert.assertNotNull(adaptiveProficiencyRanking);
        System.out.println("adaptiveProficiencyRanking = " + adaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertEquals(ProficiencyRankingScaleE.THREE, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
    }

    @Test
    public void testCalculateAdaptiveProficiencyRankingWithQuizScores() {
        // Mock ELearning course that will return AdaptiveLessonQuizStatistics with no proficiency scores.
        // Note that we have to set QuizID on the statistics for method to trigger calculation using real scores
        AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics1 = new AdaptiveLessonQuizStatistics();
        adaptiveLessonQuizStatistics1.setAdaptiveLearningQuizID(1L);
        adaptiveLessonQuizStatistics1.setProficiencyScore(80d);

        AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics2 = new AdaptiveLessonQuizStatistics();
        adaptiveLessonQuizStatistics2.setAdaptiveLearningQuizID(2L);
        adaptiveLessonQuizStatistics2.setProficiencyScore(100d);

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = ImmutableList.of(adaptiveLessonQuizStatistics1, adaptiveLessonQuizStatistics2);
        Mockito.when(iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(Mockito.any(), Mockito.any())).thenReturn(adaptiveLessonQuizStatistics);

        // This scenario we have 2 scores 100 and 80 with average of 90 so we expect proficiency to be EIGHT
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = microLessonPerformanceMonitorService.calculateAdaptiveProficiencyRanking(QPalXUser.builder().build(), ELearningCourse.builder().build());
        Assert.assertNotNull(adaptiveProficiencyRanking);
        System.out.println("adaptiveProficiencyRanking = " + adaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertEquals(ProficiencyRankingScaleE.EIGHT, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
    }

    @Test
    public void testCalculateAdaptiveProficiencyRankingWithQuizScores2() {
        // Mock ELearning course that will return AdaptiveLessonQuizStatistics with no proficiency scores.
        AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics1 = new AdaptiveLessonQuizStatistics();
        adaptiveLessonQuizStatistics1.setAdaptiveLearningQuizID(1L);
        adaptiveLessonQuizStatistics1.setProficiencyScore(80d);

        AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics2 = new AdaptiveLessonQuizStatistics();
        adaptiveLessonQuizStatistics2.setAdaptiveLearningQuizID(2L);
        adaptiveLessonQuizStatistics2.setProficiencyScore(100d);

        AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics3 = new AdaptiveLessonQuizStatistics();
        adaptiveLessonQuizStatistics3.setAdaptiveLearningQuizID(3L);
        adaptiveLessonQuizStatistics3.setProficiencyScore(40d);

        List<AdaptiveLessonQuizStatistics> adaptiveLessonQuizStatistics = ImmutableList.of(adaptiveLessonQuizStatistics1, adaptiveLessonQuizStatistics2, adaptiveLessonQuizStatistics3);
        Mockito.when(iAdaptiveLearningQuizStatisticsService.findStudentQuizzesStatisticsForCourse(Mockito.any(), Mockito.any())).thenReturn(adaptiveLessonQuizStatistics);

        // This scenario we have 2 scores 100 and 80 with average of 73 so we expect proficiency to be SEVEN
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = microLessonPerformanceMonitorService.calculateAdaptiveProficiencyRanking(QPalXUser.builder().build(), ELearningCourse.builder().build());
        System.out.println("adaptiveProficiencyRanking.getProficiencyRankingScaleE() = " + adaptiveProficiencyRanking.getProficiencyRankingScaleE());
        Assert.assertNotNull(adaptiveProficiencyRanking);
        Assert.assertEquals(ProficiencyRankingScaleE.SEVEN, adaptiveProficiencyRanking.getProficiencyRankingScaleE());
    }


}
