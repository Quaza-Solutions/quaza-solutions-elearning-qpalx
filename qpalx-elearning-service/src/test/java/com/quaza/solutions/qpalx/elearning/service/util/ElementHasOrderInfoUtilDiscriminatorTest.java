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
public class ElementHasOrderInfoUtilDiscriminatorTest {


    private IEntityHasOrderInfo iEntityHasOrderInfoFirst;

    private IEntityHasOrderInfo iEntityHasOrderInfoSecond;

    private IEntityHasOrderInfo iEntityHasOrderInfoThird;

    private IEntityHasOrderInfo iEntityHasOrderInfoFourth;

    private IEntityHasOrderInfo iEntityHasOrderInfoFifth;

    private IEntityHasOrderInfo iEntityHasOrderInfoSixth;

    private IEntityHasOrderInfo iEntityHasOrderInfoSeventh;

    private IEntityHasOrderInfo iEntityHasOrderInfoEight;

    private IEntityHasOrderInfo iEntityHasOrderInfoNinth;

    private IEntityHasOrderInfo iEntityHasOrderInfoTenth;

    private List<IEntityHasOrderInfo> elementHasOrderInfoList;

    @Mock
    private CrudRepository crudRepository;

    @InjectMocks
    private ElementHasOrderInfoUtil elementHasOrderInfoUtil;

    @Before
    public void beforeTest() {
        elementHasOrderInfoList = new ArrayList<>();
        iEntityHasOrderInfoFirst = DefaultEntityHasOrderInfo.newInstance("Introduction to Sets",1, 1L);
        iEntityHasOrderInfoSecond = DefaultEntityHasOrderInfo.newInstance("Types of Sets",2, 1L);
        iEntityHasOrderInfoThird = DefaultEntityHasOrderInfo.newInstance("SubSets",2, 2L);
        iEntityHasOrderInfoFourth = DefaultEntityHasOrderInfo.newInstance("Venn Diagrams",2, 2L);
        iEntityHasOrderInfoFifth = DefaultEntityHasOrderInfo.newInstance("Universal Sets", 2, 3L);
        iEntityHasOrderInfoSixth = DefaultEntityHasOrderInfo.newInstance("Problem Solving with Venn Diagrams", 2, 3L);
        iEntityHasOrderInfoSeventh = DefaultEntityHasOrderInfo.newInstance("Set Operations", 3, 1L);
        iEntityHasOrderInfoEight = DefaultEntityHasOrderInfo.newInstance("De Morgan's Law", 3, 2L);
        iEntityHasOrderInfoNinth = DefaultEntityHasOrderInfo.newInstance("Properties of Sets", 4, 1L);
        iEntityHasOrderInfoTenth = DefaultEntityHasOrderInfo.newInstance("Test Lesson Name", 5, 1L);

        List<IEntityHasOrderInfo> tempList = ImmutableList.of(
                iEntityHasOrderInfoFirst, iEntityHasOrderInfoSecond, iEntityHasOrderInfoThird, iEntityHasOrderInfoFourth, iEntityHasOrderInfoFifth,
                iEntityHasOrderInfoSixth, iEntityHasOrderInfoSeventh, iEntityHasOrderInfoEight, iEntityHasOrderInfoNinth, iEntityHasOrderInfoTenth
        );

        elementHasOrderInfoList.addAll(tempList);

    }

    @Test
    public void testMoveElementDownTop() {
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
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoThird.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFourth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFifth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSixth.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoSeventh.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoEight.getElementOrder());
        Assert.assertEquals(new Integer(4), iEntityHasOrderInfoNinth.getElementOrder());
        Assert.assertEquals(new Integer(5), iEntityHasOrderInfoTenth.getElementOrder());
    }

    @Test
    public void testMoveElementDownTopAcrossDiscriminator() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementDown(iEntityHasOrderInfoFourth, elementHasOrderInfoList, crudRepository);
        Assert.assertTrue(moveResult.isPresent());

        // Verify ElementOrderingResult returned as expected
        IEntityHasOrderInfo elementToMove = moveResult.get().getElementToMove();
        IEntityHasOrderInfo elementImpactedByMove = moveResult.get().getElementImpactedByMove();
        Assert.assertNotNull(elementToMove);
        Assert.assertNotNull(elementImpactedByMove);

        // Verify new element order of iElementHasOrderInfoFirst and iElementHasOrderInfoSecond
        Assert.assertEquals(new Integer(1), iEntityHasOrderInfoFirst.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSecond.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoThird.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoFourth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFifth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSixth.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoSeventh.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoEight.getElementOrder());
        Assert.assertEquals(new Integer(4), iEntityHasOrderInfoNinth.getElementOrder());
        Assert.assertEquals(new Integer(5), iEntityHasOrderInfoTenth.getElementOrder());
    }

    @Test
    public void testMoveElementDownTopAcrossDiscriminatorNoMatchFound() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementDown(iEntityHasOrderInfoSixth, elementHasOrderInfoList, crudRepository);
        Assert.assertTrue(!moveResult.isPresent());


        // Verify no changes executed by move operation
        Assert.assertEquals(new Integer(1), iEntityHasOrderInfoFirst.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSecond.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoThird.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFourth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFifth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSixth.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoSeventh.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoEight.getElementOrder());
        Assert.assertEquals(new Integer(4), iEntityHasOrderInfoNinth.getElementOrder());
        Assert.assertEquals(new Integer(5), iEntityHasOrderInfoTenth.getElementOrder());
    }

    @Test
    public void testMoveElementUpFromBottom() {
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementUp(iEntityHasOrderInfoTenth, elementHasOrderInfoList, crudRepository);
        Assert.assertTrue(moveResult.isPresent());

        // Verify ElementOrderingResult returned as expected
        IEntityHasOrderInfo elementToMove = moveResult.get().getElementToMove();
        IEntityHasOrderInfo elementImpactedByMove = moveResult.get().getElementImpactedByMove();
        Assert.assertNotNull(elementToMove);
        Assert.assertNotNull(elementImpactedByMove);

        //Verify new element order of iElementHasOrderInfoFirst and iElementHasOrderInfoSecond
        Assert.assertEquals(new Integer(4), iEntityHasOrderInfoTenth.getElementOrder());
        Assert.assertEquals(new Integer(5), iEntityHasOrderInfoNinth.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoEight.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoSeventh.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSixth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFifth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFourth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoThird.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSecond.getElementOrder());
        Assert.assertEquals(new Integer(1), iEntityHasOrderInfoFirst.getElementOrder());
    }

    @Test
    public void testMoveElementUpAcrossDiscriminator() {
        // With this test the element directly above does not match the Ordering Discriminator so this will be a good test that Discriminator is used correctly
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementUp(iEntityHasOrderInfoNinth, elementHasOrderInfoList, crudRepository);
        Assert.assertTrue(moveResult.isPresent());

        // Verify ElementOrderingResult returned as expected
        IEntityHasOrderInfo elementToMove = moveResult.get().getElementToMove();
        IEntityHasOrderInfo elementImpactedByMove = moveResult.get().getElementImpactedByMove();
        Assert.assertNotNull(elementToMove);
        Assert.assertNotNull(elementImpactedByMove);

        //Verify new element order of iElementHasOrderInfoFirst and iElementHasOrderInfoSecond
        Assert.assertEquals(new Integer(5), iEntityHasOrderInfoTenth.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoNinth.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoEight.getElementOrder());
        Assert.assertEquals(new Integer(4), iEntityHasOrderInfoSeventh.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSixth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFifth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFourth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoThird.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSecond.getElementOrder());
        Assert.assertEquals(new Integer(1), iEntityHasOrderInfoFirst.getElementOrder());
    }

    @Test
    public void testMoveElementUpAcrossDiscriminatorNoMatchFound() {
        // With this test the element directly above does not match the Ordering Discriminator, however when moving up no element is found that matches discriminator as such no action is performed
        Optional<ElementOrderingResult> moveResult = elementHasOrderInfoUtil.moveElementUp(iEntityHasOrderInfoThird, elementHasOrderInfoList, crudRepository);
        Assert.assertTrue(!moveResult.isPresent());


        //Verify existing order has not been modified by the move operation
        Assert.assertEquals(new Integer(5), iEntityHasOrderInfoTenth.getElementOrder());
        Assert.assertEquals(new Integer(4), iEntityHasOrderInfoNinth.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoEight.getElementOrder());
        Assert.assertEquals(new Integer(3), iEntityHasOrderInfoSeventh.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSixth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFifth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoFourth.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoThird.getElementOrder());
        Assert.assertEquals(new Integer(2), iEntityHasOrderInfoSecond.getElementOrder());
        Assert.assertEquals(new Integer(1), iEntityHasOrderInfoFirst.getElementOrder());
    }

}
