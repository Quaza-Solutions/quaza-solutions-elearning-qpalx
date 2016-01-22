package com.quaza.solutions.qpalx.elearning.domain.subscription;

import org.joda.time.DateTime;

/**
 * Created by manyce400 on 11/27/15.
 */
public class SubscriptionValidationResult {



    private final DateTime purchaseDate;

    private final DateTime expirationDate;

    private final SubscriptionTypeE subscriptionTypeE;

    private final SubscriptionStatusE subscriptionStatusE;

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
                .append(" Purchased Date: ").append(purchaseDate.toString())
                .append(" Expiration Date: ").append(expirationDate.toString());
        return validationMessage.toString();
    }
}
