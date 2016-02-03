package com.quaza.solutions.qpalx.elearning.service.payment.electronic;

import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.EPaymentTransaction;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.PaymentServiceProviderE;

import java.util.List;

/**
 *
 * @author manyce400
 */
public interface IEPaymentTransactionService {

    /**
     * Saves an EPaymentTransaction for analytics and tracking purposes.
     *
     * @param ePaymentTransaction
     */
    public void recordEPaymentTransaction(final EPaymentTransaction ePaymentTransaction);


    /**
     * Find all electronic payments made using specific payment provider.
     *
     * @param paymentServiceProviderE
     */
    public List<EPaymentTransaction> findAllEPaymentTransactionByPaymentServiceProvider(final PaymentServiceProviderE paymentServiceProviderE);
}

