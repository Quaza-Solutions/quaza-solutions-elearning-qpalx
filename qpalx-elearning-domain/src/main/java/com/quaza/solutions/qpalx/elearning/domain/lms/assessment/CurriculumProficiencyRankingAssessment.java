package com.quaza.solutions.qpalx.elearning.domain.lms.assessment;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
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
 * @author manyce400
 */
@Entity
@Table(name="CurriculumProficiencyRankingAssessment")
public class CurriculumProficiencyRankingAssessment {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="AssessmentTitle", nullable=false, length=255)
    private String assessmentTitle;


    // ELearningCurriculum that this assessment is
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ELearningCurriculumID", nullable = false)
    private ELearningCurriculum eLearningCurriculum;


    // DateTime that the CurriculumProficiencyRankingAssessment was added to the system
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;


    /// DateTime that the CurriculumProficiencyRankingAssessment was last updated
    @Column(name="LastModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModifyDate;


    // Provides focus area information on this proficiency ranking
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "curriculumProficiencyRankingAssessment")
    private Set<ProficiencyRankingAssessmentFocusArea> proficiencyRankingAssessmentFocusAreas = new HashSet<>();


    public CurriculumProficiencyRankingAssessment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public ELearningCurriculum geteLearningCurriculum() {
        return eLearningCurriculum;
    }

    public void seteLearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        this.eLearningCurriculum = eLearningCurriculum;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public DateTime getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(DateTime lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public void addProficiencyRankingAssessmentFocusArea(ProficiencyRankingAssessmentFocusArea proficiencyRankingAssessmentFocusArea) {
        Assert.notNull(proficiencyRankingAssessmentFocusArea);
        proficiencyRankingAssessmentFocusAreas.add(proficiencyRankingAssessmentFocusArea);
    }

    public Set<ProficiencyRankingAssessmentFocusArea> getProficiencyRankingAssessmentFocusAreas() {
        return ImmutableSet.copyOf(proficiencyRankingAssessmentFocusAreas);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CurriculumProficiencyRankingAssessment that = (CurriculumProficiencyRankingAssessment) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(assessmentTitle, that.assessmentTitle)
                .append(eLearningCurriculum, that.eLearningCurriculum)
                .append(entryDate, that.entryDate)
                .append(lastModifyDate, that.lastModifyDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(assessmentTitle)
                .append(eLearningCurriculum)
                .append(entryDate)
                .append(lastModifyDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("assessmentTitle", assessmentTitle)
                .append("eLearningCurriculum", eLearningCurriculum)
                .append("entryDate", entryDate)
                .append("lastModifyDate", lastModifyDate)
                .toString();
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CurriculumProficiencyRankingAssessment curriculumProficiencyRankingAssessment = new CurriculumProficiencyRankingAssessment();

        public Builder() {

        }

        public Builder assessmentTitle(String assessmentTitle) {
            curriculumProficiencyRankingAssessment.assessmentTitle = assessmentTitle;
            return this;
        }

        public Builder eLearningCurriculum(ELearningCurriculum eLearningCurriculum) {
            curriculumProficiencyRankingAssessment.eLearningCurriculum = eLearningCurriculum;
            return this;
        }

        public Builder proficiencyRankingAssessmentFocusArea(ProficiencyRankingAssessmentFocusArea proficiencyRankingAssessmentFocusArea) {
            curriculumProficiencyRankingAssessment.addProficiencyRankingAssessmentFocusArea(proficiencyRankingAssessmentFocusArea);
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            curriculumProficiencyRankingAssessment.entryDate = entryDate;
            return this;
        }

        public Builder lastModifyDate(DateTime lastModifyDate) {
            curriculumProficiencyRankingAssessment.lastModifyDate = lastModifyDate;
            return this;
        }

        public CurriculumProficiencyRankingAssessment build() {
            return curriculumProficiencyRankingAssessment;
        }

    }

}
