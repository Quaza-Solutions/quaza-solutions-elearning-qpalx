package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;

import java.util.List;

/**
 * @author manyce400
 */
public interface ISubscriptionCodeBatchSessionService {


    public List<SubscriptionCodeBatchSession> findAllOpenSubscriptionBatches();

}
