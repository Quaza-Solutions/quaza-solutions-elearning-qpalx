package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
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
@Table(name="ProficiencyRankingAssessment")
public class ProficiencyRankingAssessment {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="AssessmentTitle", nullable=false, length=255)
    private String assessmentTitle;

    // Each assessment is tied to a specific Quiz with questions designed to specifically test proficiency.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ELearningCurriculumID", nullable = false)
    private AdaptiveLearningQuiz adaptiveLearningQuiz;

    // ELearningCurriculum that this assessment is
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ELearningCurriculumID", nullable = false)
    private ELearningCurriculum eLearningCurriculum;

    // DateTime that the ProficiencyRankingAssessment was added to the system
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    /// DateTime that the ProficiencyRankingAssessment was last updated
    @Column(name="LastModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModifyDate;


    public ProficiencyRankingAssessment() {

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

    public AdaptiveLearningQuiz getAdaptiveLearningQuiz() {
        return adaptiveLearningQuiz;
    }

    public void setAdaptiveLearningQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        this.adaptiveLearningQuiz = adaptiveLearningQuiz;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProficiencyRankingAssessment that = (ProficiencyRankingAssessment) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(assessmentTitle, that.assessmentTitle)
                .append(adaptiveLearningQuiz, that.adaptiveLearningQuiz)
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
                .append(adaptiveLearningQuiz)
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
                .append("adaptiveLearningQuiz", adaptiveLearningQuiz)
                .append("eLearningCurriculum", eLearningCurriculum)
                .append("entryDate", entryDate)
                .append("lastModifyDate", lastModifyDate)
                .toString();
    }
}
