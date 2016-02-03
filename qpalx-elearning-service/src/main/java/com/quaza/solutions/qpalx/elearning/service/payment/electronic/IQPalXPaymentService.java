package com.quaza.solutions.qpalx.elearning.service.payment.electronic;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;

/**
 * Defines an interface for implementing QPalX payment services
 *
 * @param <R> Payment Service response object
 * @author manyce400
 */
public interface IQPalXPaymentService<R> {

    /**
     * Execute payment and bill given user and based on Users subscription info.
     *
     * @param iqPalXUserVO
     * @param qPalXSubscription
     * @return R Payment
     */
    public R executePaymentForSubscription(IQPalXUserVO iqPalXUserVO, QPalXSubscription qPalXSubscription);
}

