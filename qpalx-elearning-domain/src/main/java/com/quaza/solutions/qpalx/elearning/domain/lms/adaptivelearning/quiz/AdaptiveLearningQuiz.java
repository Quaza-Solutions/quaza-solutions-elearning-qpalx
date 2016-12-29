package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.HierarchicalLMSContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="AdaptiveLearningQuiz")
public class AdaptiveLearningQuiz implements IHierarchicalLMSContent {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="TimeToCompleteActivity", nullable=true)
    private Long timeToCompleteActivity;

    @Column(name="QuizTitle", nullable=false, length=100)
    private String quizTitle;

    @Column(name="QuizDescription", nullable=false, length=455)
    private String quizDescription;

    @Column(name="MaxPossibleActivityScore", nullable=false)
    private Double maxPossibleActivityScore;

    @Column(name="MinimumPassingActivityScore", nullable=false)
    private Double minimumPassingActivityScore;

    @Column(name="EntryDate", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    @Column(name="ModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modifyDate;

    // IF set to true then this Quiz is currently active
    @Column(name="Active", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXEMicroLessonID", nullable = false)
    private QPalXEMicroLesson qPalXEMicroLesson;

    // Collection of all questions for this quiz.  LinkedHashSet used to maintain ordering
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "adaptiveLearningQuiz")
    private Set<AdaptiveLearningQuizQuestion> adaptiveLearningQuizQuestions = new LinkedHashSet<>();

    public AdaptiveLearningQuiz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimeToCompleteActivity() {
        return timeToCompleteActivity;
    }

    public void setTimeToCompleteActivity(Long timeToCompleteActivity) {
        this.timeToCompleteActivity = timeToCompleteActivity;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public Double getMaxPossibleActivityScore() {
        return maxPossibleActivityScore;
    }

    public void setMaxPossibleActivityScore(Double maxPossibleActivityScore) {
        this.maxPossibleActivityScore = maxPossibleActivityScore;
    }

    public Double getMinimumPassingActivityScore() {
        return minimumPassingActivityScore;
    }

    public void setMinimumPassingActivityScore(Double minimumPassingActivityScore) {
        this.minimumPassingActivityScore = minimumPassingActivityScore;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public DateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(DateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public QPalXEMicroLesson getQPalXEMicroLesson() {
        return qPalXEMicroLesson;
    }

    public void setQPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
        this.qPalXEMicroLesson = qPalXEMicroLesson;
    }

    public void addAdaptiveLearningQuizQuestion(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion) {
        Assert.notNull(adaptiveLearningQuizQuestion, "adaptiveLearningQuizQuestion cannot be null");
        adaptiveLearningQuizQuestions.add(adaptiveLearningQuizQuestion);
    }

    public Set<AdaptiveLearningQuizQuestion> getAdaptiveLearningQuizQuestions() {
        return ImmutableSet.copyOf(adaptiveLearningQuizQuestions);
    }

    @Override
    public String getHierarchicalLMSContentName() {
        return getQuizTitle();
    }

    @Override
    public HierarchicalLMSContentTypeE getHierarchicalLMSContentTypeE() {
        return HierarchicalLMSContentTypeE.AdaptiveQuiz;
    }

    @Override
    public IHierarchicalLMSContent getIHierarchicalLMSContentParent() {
        return getQPalXEMicroLesson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdaptiveLearningQuiz that = (AdaptiveLearningQuiz) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(timeToCompleteActivity, that.timeToCompleteActivity)
                .append(quizTitle, that.quizTitle)
                .append(quizDescription, that.quizDescription)
                .append(maxPossibleActivityScore, that.maxPossibleActivityScore)
                .append(minimumPassingActivityScore, that.minimumPassingActivityScore)
                .append(entryDate, that.entryDate)
                .append(modifyDate, that.modifyDate)
                .append(qPalXEMicroLesson, that.qPalXEMicroLesson)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(timeToCompleteActivity)
                .append(quizTitle)
                .append(quizDescription)
                .append(maxPossibleActivityScore)
                .append(minimumPassingActivityScore)
                .append(entryDate)
                .append(modifyDate)
                .append(active)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("timeToCompleteActivity", timeToCompleteActivity)
                .append("quizTitle", quizTitle)
                .append("quizDescription", quizDescription)
                .append("maxPossibleActivityScore", maxPossibleActivityScore)
                .append("minimumPassingActivityScore", minimumPassingActivityScore)
                .append("entryDate", entryDate)
                .append("modifyDate", modifyDate)
                .append("active", active)
                .append("qPalXEMicroLesson", qPalXEMicroLesson)
                .toString();
    }


    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private AdaptiveLearningQuiz adaptiveLearningQuiz = new AdaptiveLearningQuiz();

        public Builder timeToCompleteActivity(Long timeToCompleteActivity) {
            adaptiveLearningQuiz.timeToCompleteActivity = timeToCompleteActivity;
            return this;
        }

        public Builder quizTitle(String quizTitle) {
            adaptiveLearningQuiz.quizTitle = quizTitle;
            return this;
        }

        public Builder quizDescription(String quizDescription) {
            adaptiveLearningQuiz.quizDescription = quizDescription;
            return this;
        }

        public Builder maxPossibleActivityScore(Double maxPossibleActivityScore) {
            adaptiveLearningQuiz.maxPossibleActivityScore = maxPossibleActivityScore;
            return this;
        }

        public Builder minimumPassingActivityScore(Double minimumPassingActivityScore) {
            adaptiveLearningQuiz.minimumPassingActivityScore = minimumPassingActivityScore;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            adaptiveLearningQuiz.entryDate = entryDate;
            return this;
        }

        public Builder modifyDate(DateTime modifyDate) {
            adaptiveLearningQuiz.modifyDate = modifyDate;
            return this;
        }

        public Builder active(boolean active) {
            adaptiveLearningQuiz.active = active;
            return this;
        }

        public Builder qPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
            adaptiveLearningQuiz.qPalXEMicroLesson = qPalXEMicroLesson;
            return this;
        }

        public Builder adaptiveLearningQuizQuestion(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion) {
            adaptiveLearningQuiz.addAdaptiveLearningQuizQuestion(adaptiveLearningQuizQuestion);
            return this;
        }

        public AdaptiveLearningQuiz build() {
            return adaptiveLearningQuiz;
        }

    }
}
