package com.quaza.solutions.qpalx.elearning.domain.subscription;

import com.quaza.solutions.qpalx.elearning.domain.partner.StrategicPlatformPartner;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name="SubscriptionCodeBatchSession")
public class SubscriptionCodeBatchSession {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="SubscriptionCodeBatchSessionUID", nullable=false, length=45, unique = true)
    private String subscriptionCodeBatchSessionUID;

    // Specifies the StrategicPlatformPartner which is the distribution outlet for this subscription batch ession
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StrategicPlatformPartnerID", nullable = false)
    private StrategicPlatformPartner strategicPlatformPartner;

    // Date subscription batch was generated
    @Column(name="BatchGenerationDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime batchGenerationDate;

    // Date the last subscription code in this batch was redeemed.  This signifies that batch is effectively closed.
    @Column(name="BatchRedemptionCompletionDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime batchRedemptionCompletionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriptionCodeBatchSessionUID() {
        return subscriptionCodeBatchSessionUID;
    }

    public void setSubscriptionCodeBatchSessionUID(String subscriptionCodeBatchSessionUID) {
        this.subscriptionCodeBatchSessionUID = subscriptionCodeBatchSessionUID;
    }

    public StrategicPlatformPartner getStrategicPlatformPartner() {
        return strategicPlatformPartner;
    }

    public void setStrategicPlatformPartner(StrategicPlatformPartner strategicPlatformPartner) {
        this.strategicPlatformPartner = strategicPlatformPartner;
    }

    public DateTime getBatchGenerationDate() {
        return batchGenerationDate;
    }

    public void setBatchGenerationDate(DateTime batchGenerationDate) {
        this.batchGenerationDate = batchGenerationDate;
    }

    public DateTime getBatchRedemptionCompletionDate() {
        return batchRedemptionCompletionDate;
    }

    public void setBatchRedemptionCompletionDate(DateTime batchRedemptionCompletionDate) {
        this.batchRedemptionCompletionDate = batchRedemptionCompletionDate;
    }

    public boolean isBatchRedemptionsCompleted() {
        return batchRedemptionCompletionDate != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionCodeBatchSession that = (SubscriptionCodeBatchSession) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(subscriptionCodeBatchSessionUID, that.subscriptionCodeBatchSessionUID)
                .append(strategicPlatformPartner, that.strategicPlatformPartner)
                .append(batchGenerationDate, that.batchGenerationDate)
                .append(batchRedemptionCompletionDate, that.batchRedemptionCompletionDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(subscriptionCodeBatchSessionUID)
                .append(strategicPlatformPartner)
                .append(batchGenerationDate)
                .append(batchRedemptionCompletionDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("subscriptionCodeBatchSessionUID", subscriptionCodeBatchSessionUID)
                .append("strategicPlatformPartner", strategicPlatformPartner)
                .append("batchGenerationDate", batchGenerationDate)
                .append("batchRedemptionCompletionDate", batchRedemptionCompletionDate)
                .toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private SubscriptionCodeBatchSession subscriptionCodeBatchSession = new SubscriptionCodeBatchSession();

        public Builder subscriptionCodeBatchSessionUID(String subscriptionCodeBatchSessionUID) {
            subscriptionCodeBatchSession.subscriptionCodeBatchSessionUID = subscriptionCodeBatchSessionUID;
            return this;
        }

        public Builder strategicPlatformPartner(StrategicPlatformPartner strategicPlatformPartner) {
            subscriptionCodeBatchSession.strategicPlatformPartner = strategicPlatformPartner;
            return this;
        }

        public Builder batchGenerationDate(DateTime batchGenerationDate) {
            subscriptionCodeBatchSession.batchGenerationDate = batchGenerationDate;
            return this;
        }

        public Builder batchRedemptionCompletionDate(DateTime batchRedemptionCompletionDate) {
            subscriptionCodeBatchSession.batchRedemptionCompletionDate = batchRedemptionCompletionDate;
            return this;
        }

        public SubscriptionCodeBatchSession build() {
            return subscriptionCodeBatchSession;
        }

    }

}
