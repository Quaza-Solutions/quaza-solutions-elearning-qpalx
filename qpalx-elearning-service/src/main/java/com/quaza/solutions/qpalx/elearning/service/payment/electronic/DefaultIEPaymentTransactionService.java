package com.quaza.solutions.qpalx.elearning.service.payment.electronic;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.EPaymentTransaction;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.PaymentServiceProviderE;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.repository.IEPaymentServiceTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Default implementation of IEPaymentTransactionService.
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.services.DefaultIEPaymentTransactionService")
public class DefaultIEPaymentTransactionService implements IEPaymentTransactionService {


    @Autowired
    private IEPaymentServiceTransactionRepository iePaymentServiceTransactionRepository;

    private ListeningExecutorService fixedThreadPoolListeningExecutorService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultIEPaymentTransactionService.class);

    @Override
    public void recordEPaymentTransaction(final EPaymentTransaction ePaymentTransaction) {
        Assert.notNull(ePaymentTransaction, "ePaymentTransaction cannot be null");
        LOGGER.info("Saving ePaymentTransaction: {}", ePaymentTransaction);

        // Execute this on a new Runnable as we dont want saving of data to block main thread
        Runnable persistEPaymentTransactionRunnable = () -> {
            // TODO when an EPayment transaction has failed, send email notification to QPalX support team
            // TODO handle any exception cases here on persisting transaction and report to QPalX support team
            iePaymentServiceTransactionRepository.save(ePaymentTransaction);
        };
        fixedThreadPoolListeningExecutorService.execute(persistEPaymentTransactionRunnable);
    }

    @Override
    public List<EPaymentTransaction> findAllEPaymentTransactionByPaymentServiceProvider(final PaymentServiceProviderE paymentServiceProviderE) {
        Assert.notNull(paymentServiceProviderE, "paymentServiceProviderE cannot be null");
        //TODO implement functionality
        return null;
    }
}
