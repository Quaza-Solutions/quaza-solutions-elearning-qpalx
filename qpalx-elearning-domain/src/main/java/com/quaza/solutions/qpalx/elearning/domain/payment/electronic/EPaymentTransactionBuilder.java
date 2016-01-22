package com.quaza.solutions.qpalx.elearning.domain.payment.electronic;

import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.user.QPalXUser;
import org.joda.time.DateTime;

/**
 *
 *
 * @author manyce400
 */
public class EPaymentTransactionBuilder {



    private EPaymentTransaction ePaymentTransaction;

    public EPaymentTransactionBuilder() {
        ePaymentTransaction = new EPaymentTransaction();
    }

    public EPaymentTransactionBuilder addPaymentQPalXUser(QPalXUser qPalXUser) {
        ePaymentTransaction.setQPalXUser(qPalXUser);
        return this;
    }

    public EPaymentTransactionBuilder addTransactionID(String transactionID) {
        ePaymentTransaction.setTransactionID(transactionID);
        return this;
    }

    public EPaymentTransactionBuilder addPaymentServiceProvider(PaymentServiceProviderE paymentServiceProviderE) {
        ePaymentTransaction.setPaymentServiceProviderE(paymentServiceProviderE);
        return this;
    }

    public EPaymentTransactionBuilder addEPaymentServiceMethodE(EPaymentServiceMethodE ePaymentServiceMethodE) {
        ePaymentTransaction.setEPaymentServiceMethod(ePaymentServiceMethodE);
        return this;
    }

    public EPaymentTransactionBuilder addPaymentStatus(PaymentStatusE paymentStatusE) {
        ePaymentTransaction.setPaymentStatus(paymentStatusE);
        return this;
    }

    public EPaymentTransactionBuilder addPaymentQPalXSubscription(QPalXSubscription qPalXSubscription) {
        ePaymentTransaction.setQPalXSubscription(qPalXSubscription);
        return this;
    }

    public EPaymentTransactionBuilder addPaymentEntryDateTime(DateTime paymentEntryDateTime) {
        ePaymentTransaction.setPaymentEntryDateTime(paymentEntryDateTime);
        return this;
    }

    public EPaymentTransaction get() {
        return ePaymentTransaction;
    }

}
