package com.quaza.solutions.qpalx.elearning.ci.prepaidsubscription;

import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.service.prepaidsubscription.IQPalxPrepaidIDService;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
    QPalXMunicipality qPalXMunicipality;

    //@Test
    public void testGenerateMultipleUniqueIds(){ iQpalxPrepaidIDService.generateUniqueIds(1, qPalXMunicipality); }

    //@Test
    public void testFindByIdPrepaidSubscription() {
        PrepaidSubscription prepaidSubscription = iQpalxPrepaidIDService.findByUniqueId("");
        System.out.println("prepaidSubscription = " + prepaidSubscription);
        Assert.assertNotNull(prepaidSubscription);
    }

    //@Test
    public void testSavePrepaidId(){
        PrepaidSubscription prepaidSubscription = new PrepaidSubscription();
        prepaidSubscription.setUniqueID("");
        prepaidSubscription.setAlreadyUsed(false);
        prepaidSubscription.setCityCode("");
        prepaidSubscription.setCountryCode("");
        iQpalxPrepaidIDService.save(prepaidSubscription);
    }

}
