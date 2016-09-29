package com.quaza.solutions.qpalx.elearning.domain.subscription.repository;

import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Trading_1 on 4/25/2016.
 */
public interface IQPalxPrepaidIDRepository extends CrudRepository<PrepaidSubscription, Long> {

    @Query("SELECT prepaidsubscription FROM PrepaidSubscription prepaidsubscription WHERE prepaidsubscription.uniqueID = ?1")
    public PrepaidSubscription findByUniqueIdRepo(String uniqueid);

    @Query("SELECT prepaidsubscription.uniqueID FROM PrepaidSubscription prepaidsubscription")
    public List<String> getAllUniqueIdsRepo();

    @Query("SELECT prepaidsubscription FROM PrepaidSubscription prepaidsubscription WHERE prepaidsubscription.uniqueID = ?1 and prepaidsubscription.alreadyUsed <> 1")
    public PrepaidSubscription findByUniqueIdRepoNotUsed(String uniqueId);

}
