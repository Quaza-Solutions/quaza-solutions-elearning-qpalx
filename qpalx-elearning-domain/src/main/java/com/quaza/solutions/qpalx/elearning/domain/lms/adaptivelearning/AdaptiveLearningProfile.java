package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Business object that tracks the adaptive learning profile and experience of each QPalXUserr.
 *
 * This tracks the users adaptive proficiency level historically as well.
 *
 * @author manyce400
 */
@Entity
@Table(name="AdaptiveLearningProfile")
public class AdaptiveLearningProfile {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qpalxUser;

    // DateTime that the Adaptive Learning profile started getting recorded.
    @Column(name="LearningProfileStartDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime learningProfileStartDate;

    // Record of all the AdaptiveLearningExperience completed as part of this Profile.
    // This could potentially be a very big Collection and should be paginated.
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "adaptiveLearningProfile")
    private Set<AdaptiveLearningExperience> adaptiveLearningExperiences = new HashSet<AdaptiveLearningExperience>();

    // Tracking of QPalxUser proficiency rankings by ECurriculum to see how they eveolve
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "adaptiveLearningProfile")
    private Set<AdaptiveProficiencyRanking> adaptiveProficiencyRankings = new HashSet<AdaptiveProficiencyRanking>();

    public AdaptiveLearningProfile() {
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


    public Set<AdaptiveLearningExperience> getAdaptiveLearningExperiences() {
        return ImmutableSet.copyOf(adaptiveLearningExperiences);
    }

    public void addAdaptiveLearningExperience(AdaptiveLearningExperience adaptiveLearningExperience) {
        Assert.notNull(adaptiveLearningExperience, "adaptiveLearningExperience cannot be null");
        adaptiveLearningExperiences.add(adaptiveLearningExperience);
    }

    public Set<AdaptiveProficiencyRanking> getAdaptiveProficiencyRankings() {
        return ImmutableSet.copyOf(adaptiveProficiencyRankings);
    }

    public void addAdaptiveProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        Assert.notNull(adaptiveProficiencyRanking, "adaptiveProficiencyRanking cannot be null");
        adaptiveProficiencyRankings.add(adaptiveProficiencyRanking);
    }

    public DateTime getLearningProfileStartDate() {
        return learningProfileStartDate;
    }

    public void setLearningProfileStartDate(DateTime learningProfileStartDate) {
        this.learningProfileStartDate = learningProfileStartDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdaptiveLearningProfile that = (AdaptiveLearningProfile) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(qpalxUser, that.qpalxUser)
                .append(learningProfileStartDate, that.learningProfileStartDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(qpalxUser)
                .append(learningProfileStartDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("qpalxUser", qpalxUser)
                .append("learningProfileStartDate", learningProfileStartDate)
                .append("adaptiveLearningExperiences", adaptiveLearningExperiences)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final AdaptiveLearningProfile adaptiveLearningProfile = new AdaptiveLearningProfile();

        private Builder() {
        }

        public Builder qpalxUser(final QPalXUser qpalxUser) {
            adaptiveLearningProfile.qpalxUser = qpalxUser;
            return this;
        }

        public Builder learningProfileStartDate(DateTime learningProfileStartDate) {
            adaptiveLearningProfile.learningProfileStartDate = learningProfileStartDate;
            return this;
        }

        public Builder adaptiveLearningExperience(final AdaptiveLearningExperience adaptiveLearningExperience) {
            adaptiveLearningProfile.addAdaptiveLearningExperience(adaptiveLearningExperience);
            return this;
        }

        public Builder adaptiveProficiencyRanking(final AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
            adaptiveLearningProfile.addAdaptiveProficiencyRanking(adaptiveProficiencyRanking);
            return this;
        }

        public AdaptiveLearningProfile build() {
            return adaptiveLearningProfile;
        }
    }
}
