package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Optional;

/**
 * Historical business object tracking a record of total QPalX learning experience.
 *
 * @author manyce400
 */
@Entity
@Table(name="AdaptiveLearningExperience")
public class AdaptiveLearningExperience {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdaptiveLearningProfileID", nullable = false)
    private AdaptiveLearningProfile adaptiveLearningProfile;

    // Tracks the actual ELearningCourseActivity that was taken as part of this
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELearningCourseActivityID", nullable = false)
    private ELearningCourseActivity eLearningCourseActivity;

    // Calculated proficiency score that the user got on completion of this proficiencyScore
    @Column(name="ProficiencyScore", nullable=false)
    private Double proficiencyScore;

    // Each completed AdaptiveLearningExperience will have a proficiency ranking associated with it derived from proficiencyScore
    @Column(name="ProficiencyScoreRange", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private ProficiencyScoreRangeE proficiencyScoreRangeE;

    // DateTime that the learning experience started.
    @Column(name="LearningExperienceStartDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime learningExperienceStartDate;

    // DateTime that the learning experience was completed.  With this information we may be able to factor this
    // metric into the final algorithm that figures out how well a Student did on any AdaptiveLearningExperience
    @Column(name="LearningExperienceStartDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime learningExperienceCompletedDate;

    public AdaptiveLearningExperience() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdaptiveLearningProfile getAdaptiveLearningProfile() {
        return adaptiveLearningProfile;
    }

    public void setAdaptiveLearningProfile(AdaptiveLearningProfile adaptiveLearningProfile) {
        this.adaptiveLearningProfile = adaptiveLearningProfile;
    }

    public ELearningCourseActivity getELearningCourseActivity() {
        return eLearningCourseActivity;
    }

    public void setELearningCourseActivity(ELearningCourseActivity eLearningCourseActivity) {
        this.eLearningCourseActivity = eLearningCourseActivity;
    }

    public Double getProficiencyScore() {
        return proficiencyScore;
    }

    public void setProficiencyScore(Double proficiencyScore) {
        this.proficiencyScore = proficiencyScore;
        setProficiencyScoreRangeE(proficiencyScore);
    }

    public ProficiencyScoreRangeE getProficiencyScoreRangeE() {
        return proficiencyScoreRangeE;
    }

    private void setProficiencyScoreRangeE(Double proficiencyScore) {
        Optional<ProficiencyScoreRangeE> optionalProficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(proficiencyScore);
        this.proficiencyScoreRangeE = optionalProficiencyScoreRangeE.isPresent() ? optionalProficiencyScoreRangeE.get() : null;
    }

    public DateTime getLearningExperienceStartDate() {
        return learningExperienceStartDate;
    }

    public void setLearningExperienceStartDate(DateTime learningExperienceStartDate) {
        this.learningExperienceStartDate = learningExperienceStartDate;
    }

    public DateTime getLearningExperienceCompletedDate() {
        return learningExperienceCompletedDate;
    }

    public void setLearningExperienceCompletedDate(DateTime learningExperienceCompletedDate) {
        this.learningExperienceCompletedDate = learningExperienceCompletedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdaptiveLearningExperience that = (AdaptiveLearningExperience) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(adaptiveLearningProfile, that.adaptiveLearningProfile)
                .append(eLearningCourseActivity, that.eLearningCourseActivity)
                .append(proficiencyScore, that.proficiencyScore)
                .append(proficiencyScoreRangeE, that.proficiencyScoreRangeE)
                .append(learningExperienceStartDate, that.learningExperienceStartDate)
                .append(learningExperienceCompletedDate, that.learningExperienceCompletedDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(adaptiveLearningProfile)
                .append(eLearningCourseActivity)
                .append(proficiencyScore)
                .append(proficiencyScoreRangeE)
                .append(learningExperienceStartDate)
                .append(learningExperienceCompletedDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("adaptiveLearningProfile", adaptiveLearningProfile)
                .append("eLearningCourseActivity", eLearningCourseActivity)
                .append("proficiencyScore", proficiencyScore)
                .append("proficiencyScoreRangeE", proficiencyScoreRangeE)
                .append("learningExperienceStartDate", learningExperienceStartDate)
                .append("learningExperienceCompletedDate", learningExperienceCompletedDate)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final AdaptiveLearningExperience instance = new AdaptiveLearningExperience();

        private Builder() {
        }

        public Builder adaptiveLearningProfile(final AdaptiveLearningProfile adaptiveLearningProfile) {
            instance.adaptiveLearningProfile = adaptiveLearningProfile;
            return this;
        }

        public Builder eLearningCourseActivity(final ELearningCourseActivity eLearningCourseActivity) {
            instance.eLearningCourseActivity = eLearningCourseActivity;
            return this;
        }

        public Builder proficiencyScore(final Double proficiencyScore) {
            instance.proficiencyScore = proficiencyScore;
            return this;
        }

        public Builder learningExperienceStartDate(final DateTime learningExperienceStartDate) {
            instance.learningExperienceStartDate = learningExperienceStartDate;
            return this;
        }

        public Builder learningExperienceCompletedDate(final DateTime learningExperienceCompletedDate) {
            instance.learningExperienceCompletedDate = learningExperienceCompletedDate;
            return this;
        }

        public AdaptiveLearningExperience build() {
            return instance;
        }

    }
}
