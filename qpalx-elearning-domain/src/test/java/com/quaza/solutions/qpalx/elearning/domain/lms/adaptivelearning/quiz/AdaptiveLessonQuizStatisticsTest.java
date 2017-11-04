package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author manyce400
 */
public class AdaptiveLessonQuizStatisticsTest {


    private AdaptiveLessonQuizStatistics adaptiveLessonQuizStatistics;

    @Before
    public void before() {
        adaptiveLessonQuizStatistics = new AdaptiveLessonQuizStatistics();
        adaptiveLessonQuizStatistics.setLearningExperienceStartDate(DateTime.now());
        adaptiveLessonQuizStatistics.setLearningExperienceCompletedDate(DateTime.now());
    }


    @Test
    public void testHasQuizAttempt() {
        boolean hasQuizAttempt = adaptiveLessonQuizStatistics.hasQuizAttempt();
        Assert.assertTrue(hasQuizAttempt);
    }

    @Test
    public void testisPerformanceAboveAverage() {
        adaptiveLessonQuizStatistics.setProficiencyScore(60D);
        boolean isAboveAvg = adaptiveLessonQuizStatistics.isPerformanceAboveAverage();
        Assert.assertFalse(isAboveAvg);

        adaptiveLessonQuizStatistics.setProficiencyScore(70D);
        isAboveAvg = adaptiveLessonQuizStatistics.isPerformanceAboveAverage();
        Assert.assertFalse(isAboveAvg);

        adaptiveLessonQuizStatistics.setProficiencyScore(71D);
        isAboveAvg = adaptiveLessonQuizStatistics.isPerformanceAboveAverage();
        Assert.assertTrue(isAboveAvg);

        adaptiveLessonQuizStatistics.setProficiencyScore(80D);
        isAboveAvg = adaptiveLessonQuizStatistics.isPerformanceAboveAverage();
        Assert.assertTrue(isAboveAvg);

        adaptiveLessonQuizStatistics.setProficiencyScore(90D);
        isAboveAvg = adaptiveLessonQuizStatistics.isPerformanceAboveAverage();
        Assert.assertTrue(isAboveAvg);
    }

}
