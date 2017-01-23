package com.quaza.solutions.qpalx.elearning.domain.subscription.repository;

import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface ISubscriptionCodeBatchSessionRepository  extends CrudRepository<SubscriptionCodeBatchSession, Long> {


    @Query("Select subscriptionCodeBatchSession From SubscriptionCodeBatchSession subscriptionCodeBatchSession Where subscriptionCodeBatchSession.batchRedemptionCompletionDate is null")
    public List<SubscriptionCodeBatchSession> findAllOpenSubscriptionCodeBatchSessions();

}
