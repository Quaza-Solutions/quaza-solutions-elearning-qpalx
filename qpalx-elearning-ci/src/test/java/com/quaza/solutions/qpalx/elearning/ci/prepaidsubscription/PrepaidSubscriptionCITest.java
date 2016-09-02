package com.quaza.solutions.qpalx.elearning.ci.prepaidsubscription;
import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.prepaidsubscription.IQPalxPrepaidIDService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
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

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iQPalxSubscriptionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iQPalXMunicipalityService;

    private QPalXMunicipality qPalXMunicipality;

    private QPalXSubscription qPalXSubscription;

    @Before
    public void testObjectCreation(){
     qPalXMunicipality = iQPalXMunicipalityService.findQPalXMunicipalityByID(1L);
     qPalXSubscription = iQPalxSubscriptionService.findQPalXSubscriptionByID(1L);
    }

    @Test//working
    public void testGenerateAndWriteToExcel(){
       // iQpalxPrepaidIDService.generateAndWritePrepaidIdsToExcel(10, qPalXMunicipality, qPalXSubscription, "PrepaidCodeOutputStreamFile801201699");
    }

     //@Test //- Working -
    public void testCodeRedemption(){ /*System.out.println(iQpalxPrepaidIDService.redeemCode("7D5X4E4QH0", qPalXMunicipality));*/ }

    //@Test //drop table and recreate with auto_increment
    public void testGenerateMultipleUniqueIds(){ iQpalxPrepaidIDService.generateUniqueIds(2, qPalXSubscription, qPalXMunicipality); };

    //@Test //- Working -
    public void testGetAllUniqueIds(){
        List<String> list1;
        list1 = iQpalxPrepaidIDService.getAllUniqueIds();
        System.out.print("List1 Contains: " + list1);
        Assert.assertNotNull(list1);
    }

    //@Test //- Working -
    public void testFindByUniqueIdPrepaidSubscription() {
        PrepaidSubscription prepaidSubscription = iQpalxPrepaidIDService.findByUniqueId("RFWLN5WPDL");
        System.out.println("FindByUniqueId prepaidSubscription = " + prepaidSubscription);
        Assert.assertNotNull(prepaidSubscription);
    }

    @Test //- Working -
    public void testSavePrepaidId(){
//        PrepaidSubscription prepaidSubscription = new PrepaidSubscription();
//        prepaidSubscription.setUniqueID("random");
//        prepaidSubscription.setAlreadyUsed(false);
//        prepaidSubscription.setDateCreated(DateTime.now());
//        prepaidSubscription.setqPalXSubscription(qPalXSubscription);
//        prepaidSubscription.setqPalXMunicipality(qPalXMunicipality);
//        iQpalxPrepaidIDService.save(prepaidSubscription);
    }

}
