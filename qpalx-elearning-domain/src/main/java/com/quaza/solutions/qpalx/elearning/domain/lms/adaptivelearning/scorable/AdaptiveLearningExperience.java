package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    // Link back to QPalXUser.  For Optimization reasons this will prevent the need to join back to AdaptiveLearningProfile to find User.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qpalxUser;


    // Provides information on Media Content that is associated with this activity.
    @Embedded
    private EmbedableScorableELearningActivity embedableScorableELearningActivity;

    // Calculated proficiency score that the user got on completion of this proficiencyScore
    @Column(name="ProficiencyScore", nullable=false)
    private Double proficiencyScore;

    // Each completed AdaptiveLearningExperience will have a proficiency ranking associated with it derived from proficiencyScore
    @Column(name="ProficiencyScoreRange", nullable=false, length=100)
    @Enumerated(EnumType.STRING)
    private ProficiencyScoreRangeE proficiencyScoreRangeE;

    // DateTime that the learning experience started.
    @Column(name="LearningExperienceStartDate", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime learningExperienceStartDate;

    // DateTime that the learning experience was completed.  With this information we may be able to factor this
    // metric into the final algorithm that figures out how well a Student did on any AdaptiveLearningExperience
    @Column(name="LearningExperienceCompletedDate", nullable=false)
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

    public QPalXUser getQpalxUser() {
        return qpalxUser;
    }

    public void setQpalxUser(QPalXUser qpalxUser) {
        this.qpalxUser = qpalxUser;
    }


    public EmbedableScorableELearningActivity getEmbedableScorableELearningActivity() {
        return embedableScorableELearningActivity;
    }

    public void setEmbedableScorableELearningActivity(EmbedableScorableELearningActivity embedableScorableELearningActivity) {
        this.embedableScorableELearningActivity = embedableScorableELearningActivity;
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
                .append(embedableScorableELearningActivity, that.embedableScorableELearningActivity)
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
                .append(embedableScorableELearningActivity)
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
                .append("embedabbleScorableELearningActivity", embedableScorableELearningActivity)
                .append("proficiencyScore", proficiencyScore)
                .append("proficiencyScoreRangeE", proficiencyScoreRangeE)
                .append("learningExperienceStartDate", learningExperienceStartDate)
                .append("learningExperienceCompletedDate", learningExperienceCompletedDate)
                .toString();
    }

    public static final class Builder {

        private final AdaptiveLearningExperience instance = new AdaptiveLearningExperience();

        private Builder() {
        }

        public Builder embedableScorableELearningActivity(final EmbedableScorableELearningActivity embedableScorableELearningActivity) {
            instance.embedableScorableELearningActivity = embedableScorableELearningActivity;
            return this;
        }

        public Builder proficiencyScore(final Double proficiencyScore) {
            instance.setProficiencyScore(proficiencyScore);
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

        public Builder qpalxUser(QPalXUser qpalxUser) {
            instance.qpalxUser = qpalxUser;
            return this;
        }

        public AdaptiveLearningExperience build() {
            return instance;
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}
