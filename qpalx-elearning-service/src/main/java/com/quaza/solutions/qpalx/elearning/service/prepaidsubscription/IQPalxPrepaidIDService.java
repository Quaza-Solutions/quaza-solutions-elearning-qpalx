package com.quaza.solutions.qpalx.elearning.service.prepaidsubscription;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.IPrepaidSubscriptionGenVO;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
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

    public boolean redeemCode(String prepaidSubscriptionCode, Long subscriptionID);

    public void updateRedemptionDetails(String uniqueId, QPalXUser qPalXUser);

    public List<PrepaidSubscription> findAllPrepaidSubscriptionForSubscriptionCodeBatchSession(SubscriptionCodeBatchSession subscriptionCodeBatchSession);

}
