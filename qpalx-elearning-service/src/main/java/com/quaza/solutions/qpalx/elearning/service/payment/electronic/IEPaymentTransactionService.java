package com.quaza.solutions.qpalx.elearning.service.payment.electronic;

import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.PaymentTransactionRecord;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.PaymentServiceProviderE;

import java.util.List;

/**
 *
 * @author manyce400
 */
public interface IEPaymentTransactionService {

    /**
     * Saves an PaymentTransactionRecord for analytics and tracking purposes.
     *
     * @param paymentTransactionRecord
     */
    public void recordEPaymentTransaction(final PaymentTransactionRecord paymentTransactionRecord);


    /**
     * Find all electronic payments made using specific payment provider.
     *
     * @param paymentServiceProviderE
     */
    public List<PaymentTransactionRecord> findAllEPaymentTransactionByPaymentServiceProvider(final PaymentServiceProviderE paymentServiceProviderE);
}

