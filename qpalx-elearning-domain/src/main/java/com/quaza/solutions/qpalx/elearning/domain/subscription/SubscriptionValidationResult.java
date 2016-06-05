package com.quaza.solutions.qpalx.elearning.domain.subscription;

import org.joda.time.DateTime;

/**
 * @author manyce400
 */
public class SubscriptionValidationResult {




    private final DateTime purchaseDate;

    private final DateTime expirationDate;

    private final SubscriptionTypeE subscriptionTypeE;

    private final SubscriptionStatusE subscriptionStatusE;



    public SubscriptionValidationResult(SubscriptionStatusE subscriptionStatusE) {
        this(null, null, null, subscriptionStatusE);
    }


    public SubscriptionValidationResult(DateTime purchaseDate, DateTime expirationDate, SubscriptionTypeE subscriptionTypeE, SubscriptionStatusE subscriptionStatusE) {
        this.purchaseDate = purchaseDate;
        this.expirationDate = expirationDate;
        this.subscriptionTypeE = subscriptionTypeE;
        this.subscriptionStatusE = subscriptionStatusE;
    }

    public DateTime getPurchaseDate() {
        return purchaseDate;
    }

    public DateTime getExpirationDate() {
        return expirationDate;
    }

    public SubscriptionTypeE getSubscriptionTypeE() {
        return subscriptionTypeE;
    }

    public SubscriptionStatusE getSubscriptionStatusE() {
        return subscriptionStatusE;
    }

    public String getValidationResultMessage() {
        StringBuffer validationMessage = new StringBuffer()
                .append("SubscriptionStatus:  ").append(subscriptionStatusE)
                .append(" SuscriptionType: ").append(subscriptionTypeE)
                .append(" Purchased Date: ").append(purchaseDate)
                .append(" Expiration Date: ").append(expirationDate);
        return validationMessage.toString();
    }
}
