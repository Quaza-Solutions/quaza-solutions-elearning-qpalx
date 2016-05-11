package com.quaza.solutions.qpalx.elearning.domain.payment.electronic;

/**
 * Enumeration that specifies the various types of Electronic payment services that can be provided by a PaymentServiceProviderE
 *
 * @author manyce400
 */
public enum EPaymentServiceMethodE {


    Mobile_Money("Mobile Money"),

    QPalX_Prepaid("QPalX Prepaid Card"),

    CREDIT_CARDS("Credit Cards"),

    BANK_CHECKS("Bank Checking Account");


    private final String serviceDescription;

    EPaymentServiceMethodE(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }
}
