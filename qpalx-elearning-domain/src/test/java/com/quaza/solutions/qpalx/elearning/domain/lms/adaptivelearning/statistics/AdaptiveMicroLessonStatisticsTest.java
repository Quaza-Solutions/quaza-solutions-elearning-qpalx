package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author manyce400
 */
public class AdaptiveMicroLessonStatisticsTest {


    private AdaptiveMicroLessonStatistics adaptiveMicroLessonStatistics;

    @Before
    public void beforeTest() {
        adaptiveMicroLessonStatistics = new AdaptiveMicroLessonStatistics(
                1L, "Test ML Name", 1, 2, null, null, null
        );
    }

    @Test
    public void testGetQuizzesCompletionRate() {
        double completionRate = adaptiveMicroLessonStatistics.getQuizzesCompletionRate();
        Assert.assertEquals(new Double(50.0), new Double(completionRate));
    }

}
