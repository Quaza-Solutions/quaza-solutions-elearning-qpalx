package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name="AdaptiveLearningQuizProgress")
public class AdaptiveLearningQuizProgress {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="QPalxLessonID", nullable=false)
    private Long qPalxLessonID;

    @Column(name="MicroLessonID", nullable=false)
    private Long microLessonID;

    @Column(name="AdaptiveLearningQuizID", nullable=false)
    private Long adaptiveLearningQuizID;

    // Number of times Student user has attempted/taken this adaptive quiz
    @Column(name="NumberOfAttempts", nullable=false)
    private Long numberOfAttempts;

    // DateTime that the attempt was made
    @Column(name="LastAttemptEntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastAttemptEntryDate;

    @Column(name="QPalxUserID", nullable=false)
    private Long qPalxUserID;



    public AdaptiveLearningQuizProgress() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getqPalxLessonID() {
        return qPalxLessonID;
    }

    public void setqPalxLessonID(Long qPalxLessonID) {
        this.qPalxLessonID = qPalxLessonID;
    }

    public Long getMicroLessonID() {
        return microLessonID;
    }

    public void setMicroLessonID(Long microLessonID) {
        this.microLessonID = microLessonID;
    }

    public Long getAdaptiveLearningQuizID() {
        return adaptiveLearningQuizID;
    }

    public void setAdaptiveLearningQuizID(Long adaptiveLearningQuizID) {
        this.adaptiveLearningQuizID = adaptiveLearningQuizID;
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

    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder{

        private final AdaptiveLearningQuizProgress adaptiveLearningQuizProgress = new AdaptiveLearningQuizProgress();

        public Builder adaptiveLearningQuizID(Long adaptiveLearningQuizID) {
            adaptiveLearningQuizProgress.adaptiveLearningQuizID = adaptiveLearningQuizID;
            return this;
        }

        public Builder qPalxLessonID(Long qPalxLessonID) {
            adaptiveLearningQuizProgress.qPalxLessonID = qPalxLessonID;
            return this;
        }

        public Builder microLessonID(Long microLessonID) {
            adaptiveLearningQuizProgress.microLessonID = microLessonID;
            return this;
        }

        public Builder numberOfAttempts(Long numberOfAttempts) {
            adaptiveLearningQuizProgress.numberOfAttempts = numberOfAttempts;
            return this;
        }

        public Builder lastAttemptEntryDate(DateTime lastAttemptEntryDate) {
            adaptiveLearningQuizProgress.lastAttemptEntryDate = lastAttemptEntryDate;
            return this;
        }

        public Builder qPalxUserID(Long qPalxUserID) {
            adaptiveLearningQuizProgress.qPalxUserID = qPalxUserID;
            return this;
        }

        public AdaptiveLearningQuizProgress build() {
            return adaptiveLearningQuizProgress;
        }
    }

}
