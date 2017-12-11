package com.quaza.solutions.qpalx.elearning.service.util;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.util.BaseElementHasOrderInfo;
import com.quaza.solutions.qpalx.elearning.domain.util.ElementOrderingResult;
import com.quaza.solutions.qpalx.elearning.domain.util.IElementHasOrderInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class ElementHasOrderInfoUtilTest {


    private IElementHasOrderInfo iElementHasOrderInfoFirst;

    private IElementHasOrderInfo iElementHasOrderInfoSecond;

    private IElementHasOrderInfo iElementHasOrderInfoThird;

    private Set<IElementHasOrderInfo> elementHasOrderInfoSet;

    @InjectMocks
    private ElementHasOrderInfoUtil elementHasOrderInfoUtil;

    @Before
    public void beforeTest() {
        iElementHasOrderInfoFirst = BaseElementHasOrderInfo.newInstance(1);
        iElementHasOrderInfoSecond = BaseElementHasOrderInfo.newInstance(2);
        iElementHasOrderInfoThird = BaseElementHasOrderInfo.newInstance(3);
        elementHasOrderInfoSet = ImmutableSet.of(iElementHasOrderInfoFirst, iElementHasOrderInfoSecond, iElementHasOrderInfoThird);
    }

    @Test
    public void testMoveElementDown() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementDown(iElementHasOrderInfoFirst, elementHasOrderInfoSet);
        Assert.assertTrue(moveResult.isPresent());

        // Verify ElementOrderingResult returned as expected
        IElementHasOrderInfo elementToMove = moveResult.get().getElementToMove();
        IElementHasOrderInfo elementImpactedByMove = moveResult.get().getElementImpactedByMove();
        Assert.assertNotNull(elementToMove);
        Assert.assertNotNull(elementImpactedByMove);

        // Verify new element order of iElementHasOrderInfoFirst and iElementHasOrderInfoSecond
        Assert.assertEquals(new Integer(2), iElementHasOrderInfoFirst.getElementOrder());
        Assert.assertEquals(new Integer(1), iElementHasOrderInfoSecond.getElementOrder());
    }

    @Test
    public void testMoveElementUp() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementUp(iElementHasOrderInfoSecond, elementHasOrderInfoSet);
        Assert.assertTrue(moveResult.isPresent());

        // Verify ElementOrderingResult returned as expected
        IElementHasOrderInfo elementToMove = moveResult.get().getElementToMove();
        IElementHasOrderInfo elementImpactedByMove = moveResult.get().getElementImpactedByMove();
        Assert.assertNotNull(elementToMove);
        Assert.assertNotNull(elementImpactedByMove);

        // Verify new element order of iElementHasOrderInfoFirst and iElementHasOrderInfoSecond
        Assert.assertEquals(new Integer(2), iElementHasOrderInfoFirst.getElementOrder());
        Assert.assertEquals(new Integer(1), iElementHasOrderInfoSecond.getElementOrder());
    }

}
