package com.quaza.solutions.qpalx.elearning.domain.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
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
@Table(name="ProficiencyRankingAssessmentFocusArea")
public class ProficiencyRankingAssessmentFocusArea {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;


    // CurriculumProficiencyRankingAssessment object for this focus area
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurriculumProficiencyRankingAssessmentID", nullable = true)
    private CurriculumProficiencyRankingAssessment curriculumProficiencyRankingAssessment;


    // ELearningCourse area that this Proficiency ranking assessment targets.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ELearningCourseID", nullable = false)
    private ELearningCourse eLearningCourse;


    // Each ranking assessment focus area is tied to a specific Quiz.
    // Quiz is created and customized specifically with questions only for a particular focus area
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdaptiveLearningQuizID", nullable = false)
    private AdaptiveLearningQuiz adaptiveLearningQuiz;


    // DateTime that the item was added to the system
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;


    /// DateTime that the item was last updated
    @Column(name="LastModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModifyDate;

    public ProficiencyRankingAssessmentFocusArea() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CurriculumProficiencyRankingAssessment getCurriculumProficiencyRankingAssessment() {
        return curriculumProficiencyRankingAssessment;
    }

    public void setCurriculumProficiencyRankingAssessment(CurriculumProficiencyRankingAssessment curriculumProficiencyRankingAssessment) {
        this.curriculumProficiencyRankingAssessment = curriculumProficiencyRankingAssessment;
    }

    public ELearningCourse getELearningCourse() {
        return eLearningCourse;
    }

    public void setELearningCourse(ELearningCourse eLearningCourse) {
        this.eLearningCourse = eLearningCourse;
    }

    public AdaptiveLearningQuiz getAdaptiveLearningQuiz() {
        return adaptiveLearningQuiz;
    }

    public void setAdaptiveLearningQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        this.adaptiveLearningQuiz = adaptiveLearningQuiz;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProficiencyRankingAssessmentFocusArea that = (ProficiencyRankingAssessmentFocusArea) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(eLearningCourse, that.eLearningCourse)
                .append(adaptiveLearningQuiz, that.adaptiveLearningQuiz)
                .append(entryDate, that.entryDate)
                .append(lastModifyDate, that.lastModifyDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(eLearningCourse)
                .append(adaptiveLearningQuiz)
                .append(entryDate)
                .append(lastModifyDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("eLearningCourse", eLearningCourse)
                .append("adaptiveLearningQuiz", adaptiveLearningQuiz)
                .append("entryDate", entryDate)
                .append("lastModifyDate", lastModifyDate)
                .append("curriculumProficiencyRankingAssessment", curriculumProficiencyRankingAssessment)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private ProficiencyRankingAssessmentFocusArea proficiencyRankingAssessmentFocusArea = new ProficiencyRankingAssessmentFocusArea();

        public Builder eLearningCourse(ELearningCourse eLearningCourse) {
            proficiencyRankingAssessmentFocusArea.eLearningCourse = eLearningCourse;
            return this;
        }

        public Builder adaptiveLearningQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
            proficiencyRankingAssessmentFocusArea.adaptiveLearningQuiz = adaptiveLearningQuiz;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            proficiencyRankingAssessmentFocusArea.entryDate = entryDate;
            return this;
        }

        public Builder lastModifyDate(DateTime lastModifyDate) {
            proficiencyRankingAssessmentFocusArea.lastModifyDate = lastModifyDate;
            return this;
        }

    }
}
