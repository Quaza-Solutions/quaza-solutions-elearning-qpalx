package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSessionStatistic;

import java.util.List;

/**
 * @author manyce400
 */
public interface ISubscriptionCodeBatchSessionService {


    public List<SubscriptionCodeBatchSession> findAllOpenSubscriptionBatches();

    public List<SubscriptionCodeBatchSessionStatistic> findAllSubscriptionCodeBatchSessionStatistic();

}
