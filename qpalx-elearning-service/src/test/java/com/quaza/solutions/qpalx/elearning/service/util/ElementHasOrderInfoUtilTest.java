package com.quaza.solutions.qpalx.elearning.service.util;

import com.google.common.collect.ImmutableList;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class ElementHasOrderInfoUtilTest {


    private IEntityHasOrderInfo iEntityHasOrderInfoFirst;

    private IEntityHasOrderInfo iEntityHasOrderInfoSecond;

    private IEntityHasOrderInfo iEntityHasOrderInfoThird;

    private List<IEntityHasOrderInfo> elementHasOrderInfoList;

    @Mock
    private CrudRepository crudRepository;

    @InjectMocks
    private ElementHasOrderInfoUtil elementHasOrderInfoUtil;

    @Before
    public void beforeTest() {
        elementHasOrderInfoList = new ArrayList<>();
        iEntityHasOrderInfoFirst = DefaultEntityHasOrderInfo.newInstance(1);
        iEntityHasOrderInfoSecond = DefaultEntityHasOrderInfo.newInstance(2);
        iEntityHasOrderInfoThird = DefaultEntityHasOrderInfo.newInstance(3);
        List<IEntityHasOrderInfo> tempList = ImmutableList.of(iEntityHasOrderInfoFirst, iEntityHasOrderInfoSecond, iEntityHasOrderInfoThird);
        elementHasOrderInfoList.addAll(tempList);
    }

    @Test
    public void testMoveElementDown() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementDown(iEntityHasOrderInfoFirst, elementHasOrderInfoList, crudRepository);
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
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementUp(iEntityHasOrderInfoSecond, elementHasOrderInfoList, crudRepository);
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
