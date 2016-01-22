package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
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
 * Domain object specifying User subscription profile to QPalX application.
 *
 * Created by manyce400 on 11/25/15.
 */
@Entity
@Table(name="UserSubscriptionProfile")
public class UserSubscriptionProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qpalxUser;

    // Fetch this Eager as this points to actual QPalX Subscription attributes
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXSubscriptionID", nullable = false)
    private QPalXSubscription qPalXSubscription;

    // Fetch this Eager, nullable since user does not have to be tied to an EducationalInstitution
    // EducationInstitution has Municipality information as such Service layer needs to enforce that it matches user's selected Municipality
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXEducationalInstitutionID", nullable = true)
    private QPalXEducationalInstitution educationalInstitution;

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

    public UserSubscriptionProfile() {

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

    public QPalXEducationalInstitution getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(QPalXEducationalInstitution educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
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

        UserSubscriptionProfile that = (UserSubscriptionProfile) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(qpalxUser, that.qpalxUser)
                .append(qPalXSubscription, that.qPalXSubscription)
                .append(educationalInstitution, that.educationalInstitution)
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
                .append(educationalInstitution)
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
                .append("educationalInstitution", educationalInstitution)
                .append("subscriptionStatusE", subscriptionStatusE)
                .append("subscriptionPurchasedDate", subscriptionPurchasedDate)
                .append("subscriptionExpirationDate", subscriptionExpirationDate)
                .toString();
    }
}


