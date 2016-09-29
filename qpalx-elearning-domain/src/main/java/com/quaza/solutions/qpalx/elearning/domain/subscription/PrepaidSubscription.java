package com.quaza.solutions.qpalx.elearning.domain.subscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Trading_1 on 4/25/2016.
 */
@Entity
@Table(name="PrepaidSubscription")
public class PrepaidSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXMunicipalityID", nullable = false)
    private QPalXMunicipality qPalXMunicipality;

    @Column(name="UniqueID", nullable=false, length=12, unique = true)
    private String uniqueID;

    @Column(name="AlreadyUsed", nullable=false, length = 6)
    private boolean alreadyUsed;

    @Column(name="DateCreated", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime dateCreated;

    @Column(name="RedemptionDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime redemptionDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXSubscriptionTypeID", nullable = false)
    private QPalXSubscription qPalXSubscription;

    public void setqPalXMunicipality(QPalXMunicipality qPalXMunicipality) { this.qPalXMunicipality = qPalXMunicipality; }

    public QPalXMunicipality getqPalXMunicipality(){ return qPalXMunicipality; }

    public void setqPalXSubscription(QPalXSubscription qPalXSubscription) { this.qPalXSubscription = qPalXSubscription; }

    public QPalXSubscription getqPalXSubscription(){ return qPalXSubscription; }

    public long getID(){ return this.id; };

    public String getuniqueId(){ return this.uniqueID; };

    public boolean getAlreadyUsed(){ return this.alreadyUsed; };

    public DateTime getDateCreated(){ return this.dateCreated; };

    public DateTime getRedemptionDate(){ return this.redemptionDate; };

    public void setUniqueID(String e_uniqueID){
        this.uniqueID = e_uniqueID;
    }

    public void setAlreadyUsed(boolean e_alreadyUsed){
        this.alreadyUsed = e_alreadyUsed;
    }

    public void setDateCreated(DateTime e_dateCreated){
        this.dateCreated = e_dateCreated;
    }

    public void setRedemptionDate(DateTime e_redemptionDate){
        this.redemptionDate = e_redemptionDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PrepaidSubscription that = (PrepaidSubscription) o;

        return new EqualsBuilder()
                .append(alreadyUsed, that.alreadyUsed)
                .append(id, that.id)
                .append(uniqueID, that.uniqueID)
                .append(dateCreated, that.dateCreated)
                .append(redemptionDate, that.redemptionDate)
                .append(qPalXSubscription, that.qPalXSubscription)
                .append(qPalXMunicipality, that.qPalXMunicipality)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(uniqueID)
                .append(alreadyUsed)
                .append(dateCreated)
                .append(redemptionDate)
                .append(qPalXSubscription)
                .append(qPalXMunicipality)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("uniqueID", uniqueID)
                .append("alreadyUsed", alreadyUsed)
                .append("dateCreated", dateCreated)
                .append("redemptionDate", redemptionDate)
                .append("qPalXSubscription", qPalXSubscription)
                .append("qPalXMunicipality", qPalXMunicipality)
                .toString();
    }

    public static final class Builder {

        private final PrepaidSubscription prepaidSubscription = new PrepaidSubscription();

        public Builder() {
        }

        public PrepaidSubscription.Builder alreadyUsed(boolean alreadyUsed) {
            prepaidSubscription.alreadyUsed = alreadyUsed;
            return this;
        }

        public PrepaidSubscription.Builder uniqueId(String uniqueID) {
            prepaidSubscription.uniqueID = uniqueID;
            return this;
        }

        public PrepaidSubscription.Builder dateCreated(DateTime dateCreated){
            prepaidSubscription.dateCreated = dateCreated;
            return this;
        }

        public PrepaidSubscription.Builder remptionDate(DateTime redemptionDate){
            prepaidSubscription.redemptionDate = redemptionDate;
            return this;
        }

        public PrepaidSubscription.Builder qpalxSubscription(QPalXSubscription qPalXSubscription){
            prepaidSubscription.qPalXSubscription = qPalXSubscription;
            return this;
        }

        public PrepaidSubscription.Builder qpalxMunicipality(QPalXMunicipality qPalXMunicipality){
            prepaidSubscription.qPalXMunicipality = qPalXMunicipality;
            return this;
        }

        public PrepaidSubscription build(){ return prepaidSubscription; }
    }

    public static PrepaidSubscription.Builder builder(){ return new PrepaidSubscription.Builder(); }
}

