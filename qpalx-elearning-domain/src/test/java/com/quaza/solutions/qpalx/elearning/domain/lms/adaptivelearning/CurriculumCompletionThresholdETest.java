package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class CurriculumCompletionThresholdETest {



    @Test
    public void testGetTargetCurriculumCompletionThresholdE() {
        CurriculumCompletionThresholdE curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(20);
        Assert.assertEquals(CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(25);
        Assert.assertEquals(CurriculumCompletionThresholdE.FIFTY_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(30);
        Assert.assertEquals(CurriculumCompletionThresholdE.FIFTY_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(40);
        Assert.assertEquals(CurriculumCompletionThresholdE.FIFTY_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(48);
        Assert.assertEquals(CurriculumCompletionThresholdE.FIFTY_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(50);
        Assert.assertEquals(CurriculumCompletionThresholdE.SEVENTY_FIVE_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(70);
        Assert.assertEquals(CurriculumCompletionThresholdE.SEVENTY_FIVE_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(74);
        Assert.assertEquals(CurriculumCompletionThresholdE.SEVENTY_FIVE_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(75);
        Assert.assertEquals(CurriculumCompletionThresholdE.ONE_HUNDRED_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(80);
        Assert.assertEquals(CurriculumCompletionThresholdE.ONE_HUNDRED_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(90);
        Assert.assertEquals(CurriculumCompletionThresholdE.ONE_HUNDRED_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(95);
        Assert.assertEquals(CurriculumCompletionThresholdE.ONE_HUNDRED_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(99);
        Assert.assertEquals(CurriculumCompletionThresholdE.ONE_HUNDRED_PERCENT, curriculumCompletionThresholdE);

        curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(100);
        Assert.assertEquals(CurriculumCompletionThresholdE.ONE_HUNDRED_PERCENT, curriculumCompletionThresholdE);
    }

    @Test
    public void testGetTargetCurriculumCompletionThresholdEAbove100() {
        try {
            CurriculumCompletionThresholdE curriculumCompletionThresholdE = CurriculumCompletionThresholdE.getTargetCurriculumCompletionThresholdE(101);
            Assert.fail("Completion percent cannot be greater that 100%");
        } catch (AssertionError e) {
        }
    }

}
