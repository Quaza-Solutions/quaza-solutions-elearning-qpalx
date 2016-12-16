package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
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
@Table(name="AdaptiveLearningQuizQuestionAnswer")
public class AdaptiveLearningQuizQuestionAnswer {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    // Possible Quiz question answer in text format
    @Column(name="QuizQuestionAnswerText", nullable=true, length=100)
    private String quizQuestionAnswerText;

    // Optional capability to embed multimedia capabilities as a potential answer.  This is dependent on the actual AdaptiveLearningQuizQuestionTypeE
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="eLearningMediaType", column = @Column(name="QuizQuestionAnswerMediaType") ),
            @AttributeOverride(name="qPalXTutorialContentTypeE", column = @Column(name="QuizQuestionAnswerQPalXTutorialContentType")),
            @AttributeOverride(name="eLearningMediaFile", column = @Column(name="QuizQuestionAnswerMediaFile"))
    } )
    private ELearningMediaContent quizQuestionAnswerMultiMedia;

    // From a domain object POV any answer can be correct, its up to Quiz Administration functionality to enforce this property
    @Column(name="IsCorrectAnswer", nullable = false, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isCorrectAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AdaptiveLearningQuizQuestionID", nullable = false)
    private AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion;

    @Column(name="EntryDate", nullable=false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    @Column(name="ModifyDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime modifyDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizQuestionAnswerText() {
        return quizQuestionAnswerText;
    }

    public void setQuizQuestionAnswerText(String quizQuestionAnswerText) {
        this.quizQuestionAnswerText = quizQuestionAnswerText;
    }

    public ELearningMediaContent getQuizQuestionAnswerMultiMedia() {
        return quizQuestionAnswerMultiMedia;
    }

    public void setQuizQuestionAnswerMultiMedia(ELearningMediaContent quizQuestionAnswerMultiMedia) {
        this.quizQuestionAnswerMultiMedia = quizQuestionAnswerMultiMedia;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }

    public AdaptiveLearningQuizQuestion getAdaptiveLearningQuizQuestion() {
        return adaptiveLearningQuizQuestion;
    }

    public void setAdaptiveLearningQuizQuestion(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion) {
        this.adaptiveLearningQuizQuestion = adaptiveLearningQuizQuestion;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdaptiveLearningQuizQuestionAnswer that = (AdaptiveLearningQuizQuestionAnswer) o;

        return new EqualsBuilder()
                .append(isCorrectAnswer, that.isCorrectAnswer)
                .append(id, that.id)
                .append(quizQuestionAnswerText, that.quizQuestionAnswerText)
                .append(quizQuestionAnswerMultiMedia, that.quizQuestionAnswerMultiMedia)
                .append(adaptiveLearningQuizQuestion, that.adaptiveLearningQuizQuestion)
                .append(entryDate, that.entryDate)
                .append(modifyDate, that.modifyDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(quizQuestionAnswerText)
                .append(quizQuestionAnswerMultiMedia)
                .append(isCorrectAnswer)
                .append(adaptiveLearningQuizQuestion)
                .append(entryDate)
                .append(modifyDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("quizQuestionAnswerText", quizQuestionAnswerText)
                .append("quizQuestionAnswerMultiMedia", quizQuestionAnswerMultiMedia)
                .append("isCorrectAnswer", isCorrectAnswer)
                .append("adaptiveLearningQuizQuestion", adaptiveLearningQuizQuestion)
                .append("entryDate", entryDate)
                .append("modifyDate", modifyDate)
                .toString();
    }
}
