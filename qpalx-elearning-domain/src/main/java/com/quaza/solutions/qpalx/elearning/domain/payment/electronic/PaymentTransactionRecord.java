package com.quaza.solutions.qpalx.elearning.domain.payment.electronic;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Entity models payment transaction records for subscribing to and accessing QPalX platform.
 *
 * @author manyce400
 */
@Entity
@Table(name="PaymentTransactionRecord", indexes = {
        @Index(columnList = "transactionID", name = "payment_transactionID_hidx")
    }
)
public class PaymentTransactionRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    // Transaction ID provided from the Electronic Payment service Merchant. Unique way to identify the transaction.
    @Column(name="TransactionID", nullable=false, length = 255, unique = true)
    private String transactionID;

    // Reason payment was executed
    @Column(name="PaymentReason", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PaymentReasonE paymentReason;

    @Column(name="PaymentServiceProvider", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PaymentServiceProviderE paymentServiceProviderE;

    @Column(name="EPaymentServiceMethod", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private EPaymentServiceMethodE ePaymentServiceMethod;

    @Column(name="PaymentStatus", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PaymentStatusE paymentStatus;

    // QPalXUser that payment was processed on behalf of
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qPalXUser;

    // Date payment was made
    @Column(name="PaymentEntryDateTime", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime paymentEntryDateTime;


    public PaymentTransactionRecord() {
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

    public PaymentReasonE getPaymentReason() {
        return paymentReason;
    }

    public void setPaymentReason(PaymentReasonE paymentReason) {
        this.paymentReason = paymentReason;
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

        PaymentTransactionRecord that = (PaymentTransactionRecord) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(transactionID, that.transactionID)
                .append(paymentReason, that.paymentReason)
                .append(paymentServiceProviderE, that.paymentServiceProviderE)
                .append(ePaymentServiceMethod, that.ePaymentServiceMethod)
                .append(paymentStatus, that.paymentStatus)
                .append(qPalXUser, that.qPalXUser)
                .append(paymentEntryDateTime, that.paymentEntryDateTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(transactionID)
                .append(paymentReason)
                .append(paymentServiceProviderE)
                .append(ePaymentServiceMethod)
                .append(paymentStatus)
                .append(qPalXUser)
                .append(paymentEntryDateTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("transactionID", transactionID)
                .append("paymentReason", paymentReason)
                .append("paymentServiceProviderE", paymentServiceProviderE)
                .append("ePaymentServiceMethod", ePaymentServiceMethod)
                .append("paymentStatus", paymentStatus)
                .append("qPalXUser", qPalXUser)
                .append("paymentEntryDateTime", paymentEntryDateTime)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        
        private PaymentTransactionRecord paymentTransactionRecord = new PaymentTransactionRecord();

        public Builder qPalXUser(QPalXUser qPalXUser) {
            paymentTransactionRecord.setQPalXUser(qPalXUser);
            return this;
        }

        public Builder transactionID(String transactionID) {
            paymentTransactionRecord.setTransactionID(transactionID);
            return this;
        }

        public Builder paymentServiceProviderE(PaymentServiceProviderE paymentServiceProviderE) {
            paymentTransactionRecord.setPaymentServiceProviderE(paymentServiceProviderE);
            return this;
        }

        public Builder ePaymentServiceMethodE(EPaymentServiceMethodE ePaymentServiceMethodE) {
            paymentTransactionRecord.setEPaymentServiceMethod(ePaymentServiceMethodE);
            return this;
        }

        public Builder paymentStatusE(PaymentStatusE paymentStatusE) {
            paymentTransactionRecord.setPaymentStatus(paymentStatusE);
            return this;
        }

        public Builder paymentEntryDateTime(DateTime paymentEntryDateTime) {
            paymentTransactionRecord.setPaymentEntryDateTime(paymentEntryDateTime);
            return this;
        }

        public PaymentTransactionRecord build() {
            return paymentTransactionRecord;
        }
    }
}
