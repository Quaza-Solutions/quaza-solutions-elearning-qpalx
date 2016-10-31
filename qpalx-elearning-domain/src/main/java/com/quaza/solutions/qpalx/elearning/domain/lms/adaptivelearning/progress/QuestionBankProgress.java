package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress;

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
@Table(name="QuestionBankProgress")
public class QuestionBankProgress {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="QuestionBankItemID", nullable=false)
    private Long questionBankItemID;

    @Column(name="QPalxELessonID", nullable=false)
    private Long qPalxELessonID;

    // Number of times Student user has attempted/taken this micro lesson
    @Column(name="NumberOfAttempts", nullable=false)
    private Long numberOfAttempts;

    // DateTime that the attempt was made
    @Column(name="LastAttemptEntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastAttemptEntryDate;

    @Column(name="QPalxUserID", nullable=false)
    private Long qPalxUserID;

    public QuestionBankProgress() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQPalxELessonID() {
        return qPalxELessonID;
    }

    public void setQPalxELessonID(Long qPalxELessonID) {
        this.qPalxELessonID = qPalxELessonID;
    }

    public Long getQuestionBankItemID() {
        return questionBankItemID;
    }

    public void setQuestionBankItemID(Long questionBankItemID) {
        this.questionBankItemID = questionBankItemID;
    }

    public Long getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(Long numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public DateTime getLastAttemptEntryDate() {
        return lastAttemptEntryDate;
    }

    public void setLastAttemptEntryDate(DateTime lastAttemptEntryDate) {
        this.lastAttemptEntryDate = lastAttemptEntryDate;
    }

    public Long getQPalXUserID() {
        return qPalxUserID;
    }

    public void setQPalXUserID(Long qPalxUserID) {
        this.qPalxUserID = qPalxUserID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QuestionBankProgress that = (QuestionBankProgress) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(questionBankItemID, that.questionBankItemID)
                .append(qPalxELessonID, that.qPalxELessonID)
                .append(numberOfAttempts, that.numberOfAttempts)
                .append(lastAttemptEntryDate, that.lastAttemptEntryDate)
                .append(qPalxUserID, that.qPalxUserID)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(questionBankItemID)
                .append(qPalxELessonID)
                .append(numberOfAttempts)
                .append(lastAttemptEntryDate)
                .append(qPalxUserID)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("questionBankItemID", questionBankItemID)
                .append("qPalxELessonID", qPalxELessonID)
                .append("numberOfAttempts", numberOfAttempts)
                .append("lastAttemptEntryDate", lastAttemptEntryDate)
                .append("qpalxUser", qPalxUserID)
                .toString();
    }
}
