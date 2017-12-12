package com.quaza.solutions.qpalx.elearning.service.util;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.util.DefaultEntityHasOrderInfo;
import com.quaza.solutions.qpalx.elearning.domain.util.ElementOrderingResult;
import com.quaza.solutions.qpalx.elearning.domain.util.IEntityHasOrderInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class ElementHasOrderInfoUtilTest {


    private IEntityHasOrderInfo iEntityHasOrderInfoFirst;

    private IEntityHasOrderInfo iEntityHasOrderInfoSecond;

    private IEntityHasOrderInfo iEntityHasOrderInfoThird;

    private Set<IEntityHasOrderInfo> elementHasOrderInfoSet;

    @Mock
    private CrudRepository crudRepository;

    @InjectMocks
    private ElementHasOrderInfoUtil elementHasOrderInfoUtil;

    @Before
    public void beforeTest() {
        iEntityHasOrderInfoFirst = DefaultEntityHasOrderInfo.newInstance(1);
        iEntityHasOrderInfoSecond = DefaultEntityHasOrderInfo.newInstance(2);
        iEntityHasOrderInfoThird = DefaultEntityHasOrderInfo.newInstance(3);
        elementHasOrderInfoSet = ImmutableSet.of(iEntityHasOrderInfoFirst, iEntityHasOrderInfoSecond, iEntityHasOrderInfoThird);
    }

    @Test
    public void testMoveElementDown() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementDown(iEntityHasOrderInfoFirst, elementHasOrderInfoSet, crudRepository);
        Assert.assertTrue(moveResult.isPresent());

        // Verify ElementOrderingResult returned as expected
        IEntityHasOrderInfo elementToMove = moveResult.get().getElementToMove();
        IEntityHasOrderInfo elementImpactedByMove = moveResult.get().getElementImpactedByMove();
        Assert.assertNotNull(elementToMove);
        Assert.assertNotNull(elementImpactedByMove);

        // Verify new element order of iElementHasOrderInfoFirst and iElementHasOrderInfoSecond
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFirst.getElementOrder());
        Assert.assertEquals(new Integer(1), iEntityHasOrderInfoSecond.getElementOrder());
    }

    @Test
    public void testMoveElementUp() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementUp(iEntityHasOrderInfoSecond, elementHasOrderInfoSet, crudRepository);
        Assert.assertTrue(moveResult.isPresent());

        // Verify ElementOrderingResult returned as expected
        IEntityHasOrderInfo elementToMove = moveResult.get().getElementToMove();
        IEntityHasOrderInfo elementImpactedByMove = moveResult.get().getElementImpactedByMove();
        Assert.assertNotNull(elementToMove);
        Assert.assertNotNull(elementImpactedByMove);

        // Verify new element order of iElementHasOrderInfoFirst and iElementHasOrderInfoSecond
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFirst.getElementOrder());
        Assert.assertEquals(new Integer(1), iEntityHasOrderInfoSecond.getElementOrder());
    }

}
