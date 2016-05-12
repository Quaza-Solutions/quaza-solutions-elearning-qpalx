package com.quaza.solutions.qpalx.elearning.domain.subscription;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Trading_1 on 4/25/2016.
 */

@Entity
@Table(name="PrepaidSubscription")
public class PrepaidSubscription {
    //date time created, used
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="CountryCode", nullable=false, length=255)
    private String countryCode;

    @Column(name="CityCode", nullable=false, length=255)
    private String cityCode;

    @Column(name="UniqueID", nullable=false, length=10, unique = true)
    private String uniqueID;

    @Column(name="AlreadyUsed", nullable=false, length = 6)
    private boolean alreadyUsed;

    @Column(name="DateCreated", nullable=false, length=255)
    private DateTime dateCreated;

    @Column(name="RedemptionDate", nullable=true, length=255)
    private DateTime redemptionDate;

    public long getID(){ return this.id; };

    public String getuniqueId(){ return this.uniqueID; };

    public String getcountryCode(){ return this.countryCode; };

    public String getcityCode(){ return this.cityCode; };

    public boolean getAlreadyUsed(){ return this.alreadyUsed; };

    public DateTime getDateCreated(){ return this.dateCreated; };

    public DateTime getRedemptionDate(){ return this.redemptionDate; };

    public void setUniqueID(String e_uniqueID){
        this.uniqueID = e_uniqueID;
    }

    public void setCountryCode(String e_countryCode){
        this.countryCode = e_countryCode;
    }

    public void setCityCode(String e_cityCode){
        this.cityCode = e_cityCode;
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


    //Do we need
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PrepaidSubscription p_that = (PrepaidSubscription) o;

        return new EqualsBuilder()
                .append(id, p_that.id)
                .append(countryCode, p_that.countryCode)
                .append(cityCode, p_that.cityCode)
                .append(uniqueID, p_that.uniqueID)
                .append(alreadyUsed, p_that.alreadyUsed)
                .isEquals();
    }
    //Do we need
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(countryCode)
                .append(cityCode)
                .append(uniqueID)
                .append(alreadyUsed)
                .toHashCode();
    }
    //Do we need
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("countryCode", countryCode)
                .append("cityCode", cityCode)
                .append("uniqueID", uniqueID)
                .append("alreadyUsed", alreadyUsed)
                .toString();
    }

    public static final class Builder {

        private final PrepaidSubscription prepaidSubscription = new PrepaidSubscription();

        public Builder() {
        }

        public PrepaidSubscription.Builder countryCode(String countryCode) {
            prepaidSubscription.countryCode = countryCode;
            return this;
        }

        public PrepaidSubscription.Builder cityCode(String cityCode) {
            prepaidSubscription.cityCode =cityCode;
            return this;
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

        public PrepaidSubscription build(){ return prepaidSubscription; }
    }

    public static PrepaidSubscription.Builder builder(){ return new PrepaidSubscription.Builder(); }
}

