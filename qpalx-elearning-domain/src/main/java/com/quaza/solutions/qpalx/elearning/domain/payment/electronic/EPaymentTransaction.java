package com.quaza.solutions.qpalx.elearning.domain.payment.electronic;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 *
 * @author manyce400
 */
@Entity
@Table(name="EPaymentTransaction")
public class EPaymentTransaction {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    // Transaction ID provided from the Electronic Payment service Merchant. Unique way to identify the transaction.
    @Column(name="TransactionID", nullable=false, length = 255)
    private String transactionID;

    @Column(name="PaymentServiceProvider", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PaymentServiceProviderE paymentServiceProviderE;

    @Column(name="EPaymentServiceMethod", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private EPaymentServiceMethodE ePaymentServiceMethod;

    @Column(name="PaymentStatus", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PaymentStatusE paymentStatus;

    // Populate with QPalXUser that payment charge was made against
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qPalXUser;

    // Populate with the type of Subscription if this transaction is due to a QpalXSubscription request
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXSubscriptionID", nullable = true)
    private QPalXSubscription qPalXSubscription;

    // Date payment was made
    @Column(name="PaymentEntryDateTime", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime paymentEntryDateTime;


    public EPaymentTransaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public PaymentServiceProviderE getPaymentServiceProviderE() {
        return paymentServiceProviderE;
    }

    public void setPaymentServiceProviderE(PaymentServiceProviderE paymentServiceProviderE) {
        this.paymentServiceProviderE = paymentServiceProviderE;
    }

    public EPaymentServiceMethodE getEPaymentServiceMethod() {
        return ePaymentServiceMethod;
    }

    public void setEPaymentServiceMethod(EPaymentServiceMethodE ePaymentServiceMethod) {
        this.ePaymentServiceMethod = ePaymentServiceMethod;
    }

    public PaymentStatusE getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusE paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public QPalXUser getQPalXUser() {
        return qPalXUser;
    }

    public void setQPalXUser(QPalXUser qPalXUser) {
        this.qPalXUser = qPalXUser;
    }

    public QPalXSubscription getQPalXSubscription() {
        return qPalXSubscription;
    }

    public void setQPalXSubscription(QPalXSubscription qPalXSubscription) {
        this.qPalXSubscription = qPalXSubscription;
    }

    public DateTime getPaymentEntryDateTime() {
        return paymentEntryDateTime;
    }

    public void setPaymentEntryDateTime(DateTime paymentEntryDateTime) {
        this.paymentEntryDateTime = paymentEntryDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EPaymentTransaction that = (EPaymentTransaction) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(transactionID, that.transactionID)
                .append(paymentServiceProviderE, that.paymentServiceProviderE)
                .append(ePaymentServiceMethod, that.ePaymentServiceMethod)
                .append(paymentStatus, that.paymentStatus)
                .append(paymentEntryDateTime, that.paymentEntryDateTime)
                .append(qPalXSubscription, that.qPalXSubscription)
                .append(qPalXUser, that.qPalXUser)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(transactionID)
                .append(paymentServiceProviderE)
                .append(ePaymentServiceMethod)
                .append(paymentStatus)
                .append(paymentEntryDateTime)
                .append(qPalXSubscription)
                .append(qPalXUser)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("id", id)
                .append("transactionID", transactionID)
                .append("paymentServiceProviderE", paymentServiceProviderE)
                .append("ePaymentServiceMethod", ePaymentServiceMethod)
                .append("paymentStatus", paymentStatus)
                .append("paymentEntryDateTime", paymentEntryDateTime)
                .append("qPalXSubscription", qPalXSubscription)
                .append("qPalXUser", qPalXUser)
                .toString();
    }
}
