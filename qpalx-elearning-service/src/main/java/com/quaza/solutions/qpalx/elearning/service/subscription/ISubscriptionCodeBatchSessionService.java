package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.subscription.IPrepaidSubscriptionGenVO;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscriptionStatistic;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSessionStatistic;

import java.util.List;

/**
 * @author manyce400
 */
public interface ISubscriptionCodeBatchSessionService {


    public SubscriptionCodeBatchSession findByID(Long id);

    public List<SubscriptionCodeBatchSession> findAllOpenSubscriptionBatches();

    public List<SubscriptionCodeBatchSessionStatistic> findAllSubscriptionCodeBatchSessionStatistic();

    public List<PrepaidSubscriptionStatistic> findAllSubscriptionBatchPrepaidSubscriptionStatistic(Long subscriptionCodeBatchSessionID);

    public void buildNewSubscriptionBatch(IPrepaidSubscriptionGenVO iPrepaidSubscriptionGenVO);

}
