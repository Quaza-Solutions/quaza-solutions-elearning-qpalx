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
@Table(name="QPalXEMicroLessonProgress")
public class QPalXEMicroLessonProgress {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="MicroLessonID", nullable=false, unique = true)
    private Long microLessonID;

    // Number of times Student user has attempted/taken this micro lesson
    @Column(name="NumberOfAttempts", nullable=false)
    private Integer numberOfAttempts;

    // DateTime that the attempt was made
    @Column(name="LastAttemptEntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastAttemptEntryDate;

    @Column(name="QPalxUserID", nullable=false)
    private Long qPalxUserID;


    public QPalXEMicroLessonProgress() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMicroLessonID() {
        return microLessonID;
    }

    public void setMicroLessonID(Long microLessonID) {
        this.microLessonID = microLessonID;
    }

    public Integer getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(Integer numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public synchronized void increaseNumberOfAttempts() {
        numberOfAttempts++;
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

        QPalXEMicroLessonProgress that = (QPalXEMicroLessonProgress) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(microLessonID, that.microLessonID)
                .append(numberOfAttempts, that.numberOfAttempts)
                .append(lastAttemptEntryDate, that.lastAttemptEntryDate)
                .append(qPalxUserID, that.qPalxUserID)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(microLessonID)
                .append(numberOfAttempts)
                .append(lastAttemptEntryDate)
                .append(qPalxUserID)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("microLessonID", microLessonID)
                .append("numberOfAttempts", numberOfAttempts)
                .append("lastAttemptEntryDate", lastAttemptEntryDate)
                .append("qPalxUserID", qPalxUserID)
                .toString();
    }


    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder{

        private final QPalXEMicroLessonProgress qPalXEMicroLessonProgress = new QPalXEMicroLessonProgress();

        public Builder microLessonID(Long microLessonID) {
            qPalXEMicroLessonProgress.microLessonID = microLessonID;
            return this;
        }

        public Builder numberOfAttempts(Integer numberOfAttempts) {
            qPalXEMicroLessonProgress.numberOfAttempts = numberOfAttempts;
            return this;
        }

        public Builder lastAttemptEntryDate(DateTime lastAttemptEntryDate) {
            qPalXEMicroLessonProgress.lastAttemptEntryDate = lastAttemptEntryDate;
            return this;
        }

        public Builder qPalxUserID(Long qPalxUserID) {
            qPalXEMicroLessonProgress.qPalxUserID = qPalxUserID;
            return this;
        }

        public QPalXEMicroLessonProgress build() {
            return qPalXEMicroLessonProgress;
        }
    }
}
