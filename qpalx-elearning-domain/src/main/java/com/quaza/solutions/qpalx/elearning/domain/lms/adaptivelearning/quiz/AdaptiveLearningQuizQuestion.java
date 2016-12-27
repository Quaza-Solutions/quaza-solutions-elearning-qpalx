package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
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
@Table(name="AdaptiveLearningQuizQuestion")
public class AdaptiveLearningQuizQuestion {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="QuestionTitle", nullable=false, length=100)
    private String questionTitle;

    @Column(name="QuestionFeedBack", nullable=false, length=500)
    private String questionFeedBack;

    @Column(name="AdaptiveLearningQuizQuestionType", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private AdaptiveLearningQuizQuestionTypeE adaptiveLearningQuizQuestionTypeE;

    // Optional capability to embed multimedia capabilities as part of defining a quiz question.  This is dependent on the actual AdaptiveLearningQuizQuestionTypeE
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="eLearningMediaType", column = @Column(name="QuizQuestionMediaType") ),
            @AttributeOverride(name="qPalXTutorialContentTypeE", column = @Column(name="QuizQuestionQPalXTutorialContentType")),
            @AttributeOverride(name="eLearningMediaFile", column = @Column(name="QuizQuestionMediaFile"))
    } )
    private ELearningMediaContent quizQuestionAnswerMultiMedia;

    @Column(name="EntryDate", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    @Column(name="ModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modifyDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdaptiveLearningQuizID", nullable = false)
    private AdaptiveLearningQuiz adaptiveLearningQuiz;

    // Collection of all question answers.  LinkedHashSet is used to maintain ordering
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "adaptiveLearningQuizQuestion")
    private Set<AdaptiveLearningQuizQuestionAnswer> adaptiveLearningQuizQuestionAnswers = new LinkedHashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionFeedBack() {
        return questionFeedBack;
    }

    public void setQuestionFeedBack(String questionFeedBack) {
        this.questionFeedBack = questionFeedBack;
    }

    public AdaptiveLearningQuizQuestionTypeE getAdaptiveLearningQuizQuestionTypeE() {
        return adaptiveLearningQuizQuestionTypeE;
    }

    public void setAdaptiveLearningQuizQuestionTypeE(AdaptiveLearningQuizQuestionTypeE adaptiveLearningQuizQuestionTypeE) {
        this.adaptiveLearningQuizQuestionTypeE = adaptiveLearningQuizQuestionTypeE;
    }

    public ELearningMediaContent getQuizQuestionAnswerMultiMedia() {
        return quizQuestionAnswerMultiMedia;
    }

    public void setQuizQuestionAnswerMultiMedia(ELearningMediaContent quizQuestionAnswerMultiMedia) {
        this.quizQuestionAnswerMultiMedia = quizQuestionAnswerMultiMedia;
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

    public AdaptiveLearningQuiz getAdaptiveLearningQuiz() {
        return adaptiveLearningQuiz;
    }

    public void setAdaptiveLearningQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        this.adaptiveLearningQuiz = adaptiveLearningQuiz;
    }

    public void addAdaptiveLearningQuizQuestionAnswer(AdaptiveLearningQuizQuestionAnswer adaptiveLearningQuizQuestionAnswer) {
        Assert.notNull(adaptiveLearningQuizQuestionAnswer, "adaptiveLearningQuizQuestionAnswer cannot be null");
        adaptiveLearningQuizQuestionAnswers.add(adaptiveLearningQuizQuestionAnswer);
    }

    public Set<AdaptiveLearningQuizQuestionAnswer> getAdaptiveLearningQuizQuestionAnswers() {
        return ImmutableSet.copyOf(adaptiveLearningQuizQuestionAnswers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdaptiveLearningQuizQuestion that = (AdaptiveLearningQuizQuestion) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(questionTitle, that.questionTitle)
                .append(questionFeedBack, that.questionFeedBack)
                .append(adaptiveLearningQuizQuestionTypeE, that.adaptiveLearningQuizQuestionTypeE)
                .append(quizQuestionAnswerMultiMedia, that.quizQuestionAnswerMultiMedia)
                .append(entryDate, that.entryDate)
                .append(modifyDate, that.modifyDate)
                .append(adaptiveLearningQuiz, that.adaptiveLearningQuiz)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(questionTitle)
                .append(questionFeedBack)
                .append(adaptiveLearningQuizQuestionTypeE)
                .append(quizQuestionAnswerMultiMedia)
                .append(entryDate)
                .append(modifyDate)
                .append(adaptiveLearningQuiz)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("questionTitle", questionTitle)
                .append("adaptiveLearningQuizQuestionTypeE", adaptiveLearningQuizQuestionTypeE)
                .append("quizQuestionAnswerMultiMedia", quizQuestionAnswerMultiMedia)
                .append("entryDate", entryDate)
                .append("modifyDate", modifyDate)
                .append("adaptiveLearningQuiz", adaptiveLearningQuiz)
                .toString();
    }





    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private  AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion = new AdaptiveLearningQuizQuestion();

        public Builder questionTitle(String questionTitle) {
            adaptiveLearningQuizQuestion.questionTitle = questionTitle;
            return this;
        }

        public Builder adaptiveLearningQuizQuestionTypeE(AdaptiveLearningQuizQuestionTypeE adaptiveLearningQuizQuestionTypeE) {
            adaptiveLearningQuizQuestion.adaptiveLearningQuizQuestionTypeE = adaptiveLearningQuizQuestionTypeE;
            return this;
        }

        public Builder quizQuestionAnswerMultiMedia(ELearningMediaContent quizQuestionAnswerMultiMedia) {
            adaptiveLearningQuizQuestion.quizQuestionAnswerMultiMedia = quizQuestionAnswerMultiMedia;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            adaptiveLearningQuizQuestion.entryDate = entryDate;
            return this;
        }

        public Builder modifyDate(DateTime modifyDate) {
            adaptiveLearningQuizQuestion.modifyDate = modifyDate;
            return this;
        }

        public Builder adaptiveLearningQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
            adaptiveLearningQuizQuestion.adaptiveLearningQuiz = adaptiveLearningQuiz;
            return this;
        }

        public Builder addAdaptiveLearningQuizQuestionAnswer(AdaptiveLearningQuizQuestionAnswer adaptiveLearningQuizQuestionAnswer) {
            adaptiveLearningQuizQuestion.addAdaptiveLearningQuizQuestionAnswer(adaptiveLearningQuizQuestionAnswer);
            return this;
        }

        public AdaptiveLearningQuizQuestion build() {
            return adaptiveLearningQuizQuestion;
        }
    }
}
