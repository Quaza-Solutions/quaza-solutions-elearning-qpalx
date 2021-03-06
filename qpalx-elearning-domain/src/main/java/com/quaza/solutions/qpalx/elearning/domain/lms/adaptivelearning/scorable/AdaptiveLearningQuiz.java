package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
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
@Table(name="AdaptiveLearningQuiz")
public class AdaptiveLearningQuiz implements IScorableActivity {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    // Unique scorable activity id
    @Column(name="ScorableActivityID", nullable=false, unique =true)
    private Long scorableActivityID;

    @Column(name="ScorableActivityName", nullable=false, length=255)
    private String scorableActivityName;

    @Column(name="ScorableActivityDescription", nullable=false, length=255)
    private String scorableActivityDescription;

    // Total possible score that a student can get on this
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="MaxPossibleActivityScore", nullable=false)
    private Double maxPossibleActivityScore;

    @Column(name="QPalXEmbedURL", nullable=false, length=255)
    private String qPalXEmbedURL;

    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    // IF set to true then this Quiz is currently active
    @Column(name="Active", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXEMicroLessonID", nullable = false)
    private QPalXEMicroLesson qPalXEMicroLesson;

    public AdaptiveLearningQuiz() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getScorableActivityID() {
        return scorableActivityID;
    }

    public void setScorableActivityID(Long scorableActivityID) {
        this.scorableActivityID = scorableActivityID;
    }

    @Override
    public String getScorableActivityName() {
        return scorableActivityName;
    }

    public void setScorableActivityName(String scorableActivityName) {
        this.scorableActivityName = scorableActivityName;
    }

    @Override
    public String getScorableActivityDescription() {
        return scorableActivityDescription;
    }

    public void setScorableActivityDescription(String scorableActivityDescription) {
        this.scorableActivityDescription = scorableActivityDescription;
    }

    @Override
    public Double getMaxPossibleActivityScore() {
        return maxPossibleActivityScore;
    }

    public void setMaxPossibleActivityScore(Double maxPossibleActivityScore) {
        this.maxPossibleActivityScore = maxPossibleActivityScore;
    }

    public String getqPalXEmbedURL() {
        return qPalXEmbedURL;
    }

    public void setqPalXEmbedURL(String qPalXEmbedURL) {
        this.qPalXEmbedURL = qPalXEmbedURL;
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

    public QPalXEMicroLesson getqPalXEMicroLesson() {
        return qPalXEMicroLesson;
    }

    public void setqPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
        this.qPalXEMicroLesson = qPalXEMicroLesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AdaptiveLearningQuiz that = (AdaptiveLearningQuiz) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(scorableActivityID, that.scorableActivityID)
                .append(scorableActivityName, that.scorableActivityName)
                .append(scorableActivityDescription, that.scorableActivityDescription)
                .append(maxPossibleActivityScore, that.maxPossibleActivityScore)
                .append(qPalXEmbedURL, that.qPalXEmbedURL)
                .append(entryDate, that.entryDate)
                .append(qPalXEMicroLesson, that.qPalXEMicroLesson)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(scorableActivityID)
                .append(scorableActivityName)
                .append(scorableActivityDescription)
                .append(maxPossibleActivityScore)
                .append(qPalXEmbedURL)
                .append(entryDate)
                .append(active)
                .append(qPalXEMicroLesson)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("scorableActivityID", scorableActivityID)
                .append("scorableActivityName", scorableActivityName)
                .append("scorableActivityDescription", scorableActivityDescription)
                .append("maxPossibleActivityScore", maxPossibleActivityScore)
                .append("qPalXEmbedURL", qPalXEmbedURL)
                .append("entryDate", entryDate)
                .append("active", active)
                .append("qPalXEMicroLesson", qPalXEMicroLesson)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private AdaptiveLearningQuiz adaptiveLearningQuiz = new AdaptiveLearningQuiz();

        public Builder scorableActivityID(Long scorableActivityID) {
            adaptiveLearningQuiz.scorableActivityID = scorableActivityID;
            return this;
        }

        public Builder scorableActivityName(String scorableActivityName) {
            adaptiveLearningQuiz.scorableActivityName = scorableActivityName;
            return this;
        }

        public Builder scorableActivityDescription(String scorableActivityDescription) {
            adaptiveLearningQuiz.scorableActivityDescription = scorableActivityDescription;
            return this;
        }

        public Builder maxPossibleActivityScore(Double maxPossibleActivityScore) {
            adaptiveLearningQuiz.maxPossibleActivityScore = maxPossibleActivityScore;
            return this;
        }

        public Builder qPalXEmbedURL(String qPalXEmbedURL) {
            adaptiveLearningQuiz.qPalXEmbedURL = qPalXEmbedURL;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            adaptiveLearningQuiz.entryDate = entryDate;
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

        public AdaptiveLearningQuiz build() {
            return adaptiveLearningQuiz;
        }
    }


}
