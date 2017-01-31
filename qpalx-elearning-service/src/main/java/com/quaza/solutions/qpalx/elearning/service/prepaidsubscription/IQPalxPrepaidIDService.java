package com.quaza.solutions.qpalx.elearning.service.prepaidsubscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.IPrepaidSubscriptionGenVO;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;

import java.util.List;

/**
 * Created by Trading_1 on 4/29/2016.
 */
public interface IQPalxPrepaidIDService {


    public void generateUniqueIds(IPrepaidSubscriptionGenVO iPrepaidSubscriptionGenVO, SubscriptionCodeBatchSession subscriptionCodeBatchSession);//get and set countrycode/citycode

    public PrepaidSubscription findByUniqueId(String obj);

    public PrepaidSubscription findById(Long obj);

    public void save(PrepaidSubscription obj);

    public List<String> getAllUniqueIds();

    public void writePrepaidExcelFile(QPalXMunicipality qPalXMunicipality, QPalXSubscription qPalXSubscription, String fileName);

    //upon redemption update table with respective values - pass municipality down for extra verification - return true or false for web end
    public boolean redeemCode(String uniqueId, QPalXMunicipality qPalXMunicipality); //unique idmust match country + city code

    public void updateRedemptionDetails(String uniqueId, QPalXUser qPalXUser);

    public List<PrepaidSubscription> findAllPrepaidSubscriptionForSubscriptionCodeBatchSession(SubscriptionCodeBatchSession subscriptionCodeBatchSession);

}
