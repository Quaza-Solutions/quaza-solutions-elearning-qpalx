package com.quaza.solutions.qpalx.elearning.domain.subscription.repository;

import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by manyce400 on 12/6/15.
 */
public interface IQPalXSubscriptionRepository extends CrudRepository<QPalXSubscription, Long> {

    /**
     * Find all QPalX subscriptions in a given Country by Country  Code.
     *
     * @param countryCode
     * @return
     */
    @Query("Select subscription From QPalXSubscription subscription Where subscription.subscriptionQPalXCountry.countryCode = ?1 Order by subscription.subscriptionCost asc")
    public List<QPalXSubscription> findAllQPalXSubscriptionsByCountryCode(String countryCode);
}
