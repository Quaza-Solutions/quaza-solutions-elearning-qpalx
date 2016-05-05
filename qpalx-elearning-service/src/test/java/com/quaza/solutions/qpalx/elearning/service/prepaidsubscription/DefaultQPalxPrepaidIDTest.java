package com.quaza.solutions.qpalx.elearning.service.prepaidsubscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXMunicipalityBuilder;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
/**
 *
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

    @Test
    public void testRedeemCode(){
        Mockito.when(iqPalxPrepaidIDService.redeemCode("uniqueId", qPalXMunicipality)).thenReturn(true);
        Assert.assertEquals(true, iqPalxPrepaidIDService.redeemCode("uniqueId", qPalXMunicipality));
    }

    @Test
    public void testGenerateUniqueId(){
        mockAllUniqueIdsList = iqPalxPrepaidIDService.getAllUniqueIds();
        Mockito.when(iqPalxPrepaidIDService.generateUniqueId(qPalXMunicipality, mockAllUniqueIdsList)).thenReturn("randomString");
        Assert.assertEquals("randomString", iqPalxPrepaidIDService.generateUniqueId(qPalXMunicipality, mockAllUniqueIdsList));
    }

    @Test
    public void testGetAllUniqueIds() {
        mockAllUniqueIdsList = iqPalxPrepaidIDService.getAllUniqueIds();
        Assert.assertEquals(mockAllUniqueIdsList, iqPalxPrepaidIDService.getAllUniqueIds());
        mockAllUniqueIdsList.add(prepaidSubscription);
        Assert.assertNotEquals(mockAllUniqueIdsList, iqPalxPrepaidIDService.getAllUniqueIds());
    }

}
