package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Domain object specifies all attributes of a Student subscription profile on QPalX platform.
 *
 * Created by manyce400 on 11/25/15.
 */
@Entity
@Table(name="StudentSubscriptionProfile")
public class StudentSubscriptionProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    // QPalXUser object for this subscription
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qpalxUser;

    // Fetch this Eager as this points to actual QPalX Subscription attributes
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXSubscriptionID", nullable = false)
    private QPalXSubscription qPalXSubscription;



    @Column(name="SubscriptionStatusE", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private SubscriptionStatusE subscriptionStatusE;

    // Not nullable since every subscription requires a start date
    @Column(name="SubscriptionPurchasedDate", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime subscriptionPurchasedDate;

    // Nullable.  IF null then the subscription is considered to be still active.
    // Service layer should enforce that only one subscription can be active at any given time.
    @Column(name="SubscriptionExpirationDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime subscriptionExpirationDate;



    public StudentSubscriptionProfile() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QPalXUser getQpalxUser() {
        return qpalxUser;
    }

    public void setQpalxUser(QPalXUser qpalxUser) {
        this.qpalxUser = qpalxUser;
    }

    public QPalXSubscription getqPalXSubscription() {
        return qPalXSubscription;
    }

    public void setqPalXSubscription(QPalXSubscription qPalXSubscription) {
        this.qPalXSubscription = qPalXSubscription;
    }

    public SubscriptionStatusE getSubscriptionStatusE() {
        return subscriptionStatusE;
    }

    public void setSubscriptionStatusE(SubscriptionStatusE subscriptionStatusE) {
        this.subscriptionStatusE = subscriptionStatusE;
    }

    public DateTime getSubscriptionPurchasedDate() {
        return subscriptionPurchasedDate;
    }

    public void setSubscriptionPurchasedDate(DateTime subscriptionPurchasedDate) {
        this.subscriptionPurchasedDate = subscriptionPurchasedDate;
    }

    public DateTime getSubscriptionExpirationDate() {
        return subscriptionExpirationDate;
    }

    public void setSubscriptionExpirationDate(DateTime subscriptionExpirationDate) {
        this.subscriptionExpirationDate = subscriptionExpirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StudentSubscriptionProfile that = (StudentSubscriptionProfile) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(qpalxUser, that.qpalxUser)
                .append(qPalXSubscription, that.qPalXSubscription)
                .append(subscriptionStatusE, that.subscriptionStatusE)
                .append(subscriptionPurchasedDate, that.subscriptionPurchasedDate)
                .append(subscriptionExpirationDate, that.subscriptionExpirationDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(qpalxUser)
                .append(qPalXSubscription)
                .append(subscriptionStatusE)
                .append(subscriptionPurchasedDate)
                .append(subscriptionExpirationDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("qpalxUser", qpalxUser)
                .append("qPalXSubscription", qPalXSubscription)
                .append("subscriptionStatusE", subscriptionStatusE)
                .append("subscriptionPurchasedDate", subscriptionPurchasedDate)
                .append("subscriptionExpirationDate", subscriptionExpirationDate)
                .toString();
    }
}


