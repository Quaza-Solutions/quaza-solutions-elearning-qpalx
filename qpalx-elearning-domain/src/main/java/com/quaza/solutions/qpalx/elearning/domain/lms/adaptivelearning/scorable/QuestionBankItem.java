package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.HierarchicalLMSContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
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
@Table(name="QuestionBankItem")
public class QuestionBankItem implements IHierarchicalLMSContent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="questionTitle", nullable=false, length=255)
    private String questionTitle;

    @Column(name="QuestionDescription", nullable=false, length=255)
    private String questionDescription;

    // Provides information on Media Content that is associated with this activity.
    @Embedded
    private ELearningMediaContent eLearningMediaContent;

    // Always fetch this Eager as we always need the parent QPalXELesson always avaialable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalXELessonID", nullable = false)
    private QPalXELesson qPalXELesson;

    // DateTime that the ELearning Media Content was uploaded on the QPalX platform.
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    // IF set to true then this QPalXEMicroLesson is currently active
    @Column(name="IsActive", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;


    public QuestionBankItem() {

    }

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

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public ELearningMediaContent geteLearningMediaContent() {
        return eLearningMediaContent;
    }

    public void seteLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    public QPalXELesson getqPalXELesson() {
        return qPalXELesson;
    }

    public void setqPalXELesson(QPalXELesson qPalXELesson) {
        this.qPalXELesson = qPalXELesson;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getHierarchicalLMSContentName() {
        return getQuestionTitle();
    }

    @Override
    public HierarchicalLMSContentTypeE getHierarchicalLMSContentTypeE() {
        return HierarchicalLMSContentTypeE.QuestionBank;
    }

    @Override
    public IHierarchicalLMSContent getIHierarchicalLMSContentParent() {
        return getqPalXELesson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QuestionBankItem that = (QuestionBankItem) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(questionTitle, that.questionTitle)
                .append(questionDescription, that.questionDescription)
                .append(eLearningMediaContent, that.eLearningMediaContent)
                .append(qPalXELesson, that.qPalXELesson)
                .append(entryDate, that.entryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(questionTitle)
                .append(questionDescription)
                .append(eLearningMediaContent)
                .append(qPalXELesson)
                .append(entryDate)
                .append(active)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("questionTitle", questionTitle)
                .append("questionDescription", questionDescription)
                .append("eLearningMediaContent", eLearningMediaContent)
                .append("qPalXELesson", qPalXELesson)
                .append("entryDate", entryDate)
                .append("active", active)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private QuestionBankItem questionBankItem = new QuestionBankItem();

        public Builder questionTitle(String questionTitle) {
            questionBankItem.questionTitle = questionTitle;
            return this;
        }

        public Builder questionDescription(String questionDescription) {
            questionBankItem.questionDescription = questionDescription;
            return this;
        }

        public Builder eLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
            questionBankItem.eLearningMediaContent = eLearningMediaContent;
            return this;
        }

        public Builder qPalXELesson(QPalXELesson qPalXELesson) {
            questionBankItem.qPalXELesson = qPalXELesson;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            questionBankItem.entryDate = entryDate;
            return this;
        }

        public Builder active(boolean active) {
            questionBankItem.active = active;
            return this;
        }

        public QuestionBankItem build() {
            return questionBankItem;
        }
    }

}
