package com.quaza.solutions.qpalx.elearning.domain.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author manyce400
 */
public class IElementHasOrderInfoTest {


    private IElementHasOrderInfo iElementHasOrderInfoFirst;

    private IElementHasOrderInfo iElementHasOrderInfoSecond;

    @Before
    public void beforeTest() {
        iElementHasOrderInfoFirst = BaseElementHasOrderInfo.newInstance(1);
        iElementHasOrderInfoSecond = BaseElementHasOrderInfo.newInstance(2);
    }

    @Test
    public void testIsBelow() {
        boolean isBelow = iElementHasOrderInfoFirst.isBelow(iElementHasOrderInfoSecond);
        Assert.assertFalse(false);

        isBelow = iElementHasOrderInfoSecond.isBelow(iElementHasOrderInfoFirst);
        Assert.assertTrue(isBelow);
    }

    @Test
    public void testIsAbove() {
        boolean isAbove = iElementHasOrderInfoFirst.isAbove(iElementHasOrderInfoSecond);
        Assert.assertTrue(isAbove);

        isAbove = iElementHasOrderInfoSecond.isAbove(iElementHasOrderInfoFirst);
        Assert.assertFalse(isAbove);

    }


}
