package com.quaza.solutions.qpalx.elearning.ci.prepaidsubscription;

import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.service.prepaidsubscription.IQPalxPrepaidIDService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
/**
 * Created by Trading_1 on 4/29/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {QPalXServiceApplicationBootstrapper.class})
public class PrepaidSubscriptionCITest {
    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxPrepaidIDService")
    private IQPalxPrepaidIDService iQpalxPrepaidIDService;

    QPalXMunicipality qPalXMunicipality;

    @Test //- Working -
    public void testCodeRedemption(){
//        iQpalxPrepaidIDService.redeemCode("1H4M6X8Q1W", qPalXMunicipality);
    };

//    @Test //- Working -
    public void testGenerateMultipleUniqueIds(){ iQpalxPrepaidIDService.generateUniqueIds(1, qPalXMunicipality); }

    @Test //- Working -
    public void testGetAllUniqueIds(){
        List<PrepaidSubscription> list1;
        list1 = iQpalxPrepaidIDService.getAllUniqueIds();
        System.out.print("List1 Contains: " + list1);
        Assert.assertNotNull(list1);
    }

//    @Test //- Working -
    public void testFindByUniqueIdPrepaidSubscription() {
        PrepaidSubscription prepaidSubscription = iQpalxPrepaidIDService.findByUniqueId("1H4M6X8Q1W");
        System.out.println("FindByUniqueId prepaidSubscription = " + prepaidSubscription);
        Assert.assertNotNull(prepaidSubscription);
    }

    //@Test //- Working -
    public void testSavePrepaidId(){
        PrepaidSubscription prepaidSubscription = new PrepaidSubscription();
        prepaidSubscription.setUniqueID("newRandom");
        prepaidSubscription.setAlreadyUsed(false);
        prepaidSubscription.setCityCode("ny");
        prepaidSubscription.setCountryCode("usa");
        prepaidSubscription.setDateCreated(DateTime.now());
        iQpalxPrepaidIDService.save(prepaidSubscription);
    }

}
