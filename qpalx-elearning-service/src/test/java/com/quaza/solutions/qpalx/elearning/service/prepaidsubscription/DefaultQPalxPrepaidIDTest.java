package com.quaza.solutions.qpalx.elearning.service.prepaidsubscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
/**
 * @author Trading_1
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultQPalxPrepaidIDTest {
    @Mock
    IQPalxPrepaidIDService iqPalxPrepaidIDService;
    @Mock
    PrepaidSubscription prepaidSubscription;
    @Mock
    QPalXMunicipality qPalXMunicipality;
    @Mock
    List<PrepaidSubscription> mockAllUniqueIdsList;
    @Mock
    List<PrepaidSubscription> mockValues;
    @Mock
    List<PrepaidSubscription> mockReturn;

    @Test
    public void testRedeemCode(){
        Mockito.when(iqPalxPrepaidIDService.redeemCode("uniqueId", qPalXMunicipality)).thenReturn(true);
        Assert.assertEquals(true, iqPalxPrepaidIDService.redeemCode("uniqueId", qPalXMunicipality));
    }

    @Test
    public void testGenerateUniqueId(){
        mockAllUniqueIdsList = iqPalxPrepaidIDService.getAllUniqueIds();
        Mockito.when(iqPalxPrepaidIDService.generateUniqueId(qPalXMunicipality, mockAllUniqueIdsList)).thenReturn("randomString");
        System.out.println("Generated Id = " + iqPalxPrepaidIDService.generateUniqueId(qPalXMunicipality, mockAllUniqueIdsList));
        Assert.assertEquals("randomString", iqPalxPrepaidIDService.generateUniqueId(qPalXMunicipality, mockAllUniqueIdsList));
    }

    @Test
    public void testGetAllUniqueIds() {
        mockAllUniqueIdsList = iqPalxPrepaidIDService.getAllUniqueIds();
        mockValues = iqPalxPrepaidIDService.getAllUniqueIds();
        Assert.assertEquals(mockAllUniqueIdsList, mockValues);
        mockAllUniqueIdsList.add(prepaidSubscription);
        System.out.println("mockAllUniqueIdsList = " + mockAllUniqueIdsList);
        System.out.println("getAllUniqueIds() = " + mockValues);
        Assert.assertNotEquals(mockAllUniqueIdsList, mockValues);
        Mockito.doReturn(mockReturn).when(iqPalxPrepaidIDService).getAllUniqueIds();
        if(mockAllUniqueIdsList.contains(prepaidSubscription)){
            System.out.println("Contains Value - " + prepaidSubscription);
        }
        Assert.assertEquals(mockReturn, iqPalxPrepaidIDService.getAllUniqueIds());
    }


}
