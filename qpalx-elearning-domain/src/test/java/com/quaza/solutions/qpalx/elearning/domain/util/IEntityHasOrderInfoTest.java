package com.quaza.solutions.qpalx.elearning.domain.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author manyce400
 */
public class IEntityHasOrderInfoTest {


    private IEntityHasOrderInfo iEntityHasOrderInfoFirst;

    private IEntityHasOrderInfo iEntityHasOrderInfoSecond;

    @Before
    public void beforeTest() {
        iEntityHasOrderInfoFirst = DefaultEntityHasOrderInfo.newInstance(1);
        iEntityHasOrderInfoSecond = DefaultEntityHasOrderInfo.newInstance(2);
    }

    @Test
    public void testIsBelow() {
        boolean isBelow = iEntityHasOrderInfoFirst.isBelow(iEntityHasOrderInfoSecond);
        Assert.assertFalse(false);

        isBelow = iEntityHasOrderInfoSecond.isBelow(iEntityHasOrderInfoFirst);
        Assert.assertTrue(isBelow);
    }

    @Test
    public void testIsAbove() {
        boolean isAbove = iEntityHasOrderInfoFirst.isAbove(iEntityHasOrderInfoSecond);
        Assert.assertTrue(isAbove);

        isAbove = iEntityHasOrderInfoSecond.isAbove(iEntityHasOrderInfoFirst);
        Assert.assertFalse(isAbove);

    }


}
