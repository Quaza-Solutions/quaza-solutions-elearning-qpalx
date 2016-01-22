package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Historical profile tracking of how a QPalXUser's proficiency ranking has evolved through time.
 *
 * In order to determine QPalxUser AdaptiveProficiencyRanking at any given time, we need to look at a
 * cumulative set of all user's AdaptiveLearningExperience at the time the ranking is calculated.
 *
 * Reason for looking at the whole entire snapshot everytime, we can use all the data we have availble to
 * really determine if the user is truly getting better or worse and not just by a simple slice of user performance
 * for a short period of time which can misrepresent actual progress.
 *
 * We can easily figure out all the AdaptiveProficiencyRanking that went into each individual ranking based on the
 * ELearningCurriculum.  Simply query to find all ELearning course activities taken that will map to curriculum.
 *
 * @author manyce400
 */
@Entity
@Table(name="AdaptiveProficiencyRanking")
public class AdaptiveProficiencyRanking {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    // Link back to QPalXUser AdaptiveLearningProfile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdaptiveLearningProfileID", nullable = false)
    private AdaptiveLearningProfile adaptiveLearningProfile;

    // Link back to ELearningCurriculum.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELearningCurriculumID", nullable = false)
    private ELearningCurriculum eLearningCurriculum;

    // Record of QPalxUser Proficiency Ranking for ELearningCurriculum at the recorded date
    @Column(name="ProficiencyRankingScale", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private ProficiencyRankingScaleE proficiencyRankingScaleE;

    // DateTime that the AdaptiveProficiencyRanking was recorded.
    @Column(name="ProficiencyRankingRecordDateTime", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime proficiencyRankingRecordDateTime;


    public AdaptiveProficiencyRanking() {

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

    public ELearningCurriculum geteLearningCurriculum() {
        return eLearningCurriculum;
    }

    public void seteLearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        this.eLearningCurriculum = eLearningCurriculum;
    }

    public ProficiencyRankingScaleE getProficiencyRankingScaleE() {
        return proficiencyRankingScaleE;
    }

    public void setProficiencyRankingScaleE(ProficiencyRankingScaleE proficiencyRankingScaleE) {
        this.proficiencyRankingScaleE = proficiencyRankingScaleE;
    }

    public DateTime getProficiencyRankingRecordDateTime() {
        return proficiencyRankingRecordDateTime;
    }

    public void setProficiencyRankingRecordDateTime(DateTime proficiencyRankingRecordDateTime) {
        this.proficiencyRankingRecordDateTime = proficiencyRankingRecordDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdaptiveProficiencyRanking that = (AdaptiveProficiencyRanking) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(adaptiveLearningProfile, that.adaptiveLearningProfile)
                .append(eLearningCurriculum, that.eLearningCurriculum)
                .append(proficiencyRankingScaleE, that.proficiencyRankingScaleE)
                .append(proficiencyRankingRecordDateTime, that.proficiencyRankingRecordDateTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(adaptiveLearningProfile)
                .append(eLearningCurriculum)
                .append(proficiencyRankingScaleE)
                .append(proficiencyRankingRecordDateTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("proficiencyRankingRecordDateTime", proficiencyRankingRecordDateTime)
                .append("adaptiveLearningProfile", adaptiveLearningProfile)
                .append("eLearningCurriculum", eLearningCurriculum)
                .append("proficiencyRankingScaleE", proficiencyRankingScaleE)
                .append("id", id)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private final AdaptiveProficiencyRanking adaptiveProficiencyRanking = new AdaptiveProficiencyRanking();

        private Builder() {
        }

        public Builder adaptiveLearningProfile(final AdaptiveLearningProfile adaptiveLearningProfile) {
            adaptiveProficiencyRanking.adaptiveLearningProfile = adaptiveLearningProfile;
            return this;
        }

        public Builder eLearningCurriculum(final ELearningCurriculum eLearningCurriculum) {
            adaptiveProficiencyRanking.eLearningCurriculum = eLearningCurriculum;
            return this;
        }

        public Builder proficiencyRankingScaleE(final ProficiencyRankingScaleE proficiencyRankingScaleE) {
            adaptiveProficiencyRanking.proficiencyRankingScaleE = proficiencyRankingScaleE;
            return this;
        }

        public Builder proficiencyRankingRecordDateTime(final DateTime proficiencyRankingRecordDateTime) {
            adaptiveProficiencyRanking.proficiencyRankingRecordDateTime = proficiencyRankingRecordDateTime;
            return this;
        }

        public AdaptiveProficiencyRanking build() {
            return adaptiveProficiencyRanking;
        }
    }
}