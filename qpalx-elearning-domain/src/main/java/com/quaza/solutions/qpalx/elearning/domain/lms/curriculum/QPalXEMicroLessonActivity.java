package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

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
@Table(name="QPalXEMicroLessonActivity")
public class QPalXEMicroLessonActivity {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="MicroLessonActivityName", nullable=false, length=255)
    private String microLessonActivityName;

    @Column(name="MicroLessonActivityDescription", nullable=false, length=255)
    private String microLessonActivityDescription;

    // Tracks and records the learning activity taken/engaged in as part of this micro lesson activity
    @Column(name="MicroLessonActivityType", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private MicroLessonActivityTypeE microLessonActivityType;

    // Always fetch this Eager as we always need the parent QPalXELesson always avaialable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalXEMicroLessonID", nullable = false)
    private QPalXEMicroLesson qPalXEMicroLesson;

    // Provides information on Media Content that is associated with this activity.
    @Embedded
    private ELearningMediaContent eLearningMediaContent;

    // DateTime that the ELearning Media Content was uploaded on the QPalX platform.
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    // IF set to true then this QPalXEMicroLesson is currently active
    @Column(name="MicroLessonActivityActive", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean microLessonActivityActive;

    public QPalXEMicroLessonActivity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMicroLessonActivityName() {
        return microLessonActivityName;
    }

    public void setMicroLessonActivityName(String microLessonActivityName) {
        this.microLessonActivityName = microLessonActivityName;
    }

    public String getMicroLessonActivityDescription() {
        return microLessonActivityDescription;
    }

    public void setMicroLessonActivityDescription(String microLessonActivityDescription) {
        this.microLessonActivityDescription = microLessonActivityDescription;
    }

    public MicroLessonActivityTypeE getMicroLessonActivityType() {
        return microLessonActivityType;
    }

    public void setMicroLessonActivityType(MicroLessonActivityTypeE microLessonActivityType) {
        this.microLessonActivityType = microLessonActivityType;
    }

    public QPalXEMicroLesson getqPalXEMicroLesson() {
        return qPalXEMicroLesson;
    }

    public void setqPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
        this.qPalXEMicroLesson = qPalXEMicroLesson;
    }

    public ELearningMediaContent geteLearningMediaContent() {
        return eLearningMediaContent;
    }

    public void seteLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isMicroLessonActivityActive() {
        return microLessonActivityActive;
    }

    public void setMicroLessonActivityActive(boolean microLessonActivityActive) {
        this.microLessonActivityActive = microLessonActivityActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QPalXEMicroLessonActivity that = (QPalXEMicroLessonActivity) o;

        return new EqualsBuilder()
                .append(microLessonActivityActive, that.microLessonActivityActive)
                .append(id, that.id)
                .append(microLessonActivityName, that.microLessonActivityName)
                .append(microLessonActivityDescription, that.microLessonActivityDescription)
                .append(microLessonActivityType, that.microLessonActivityType)
                .append(qPalXEMicroLesson, that.qPalXEMicroLesson)
                .append(eLearningMediaContent, that.eLearningMediaContent)
                .append(entryDate, that.entryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(microLessonActivityName)
                .append(microLessonActivityDescription)
                .append(microLessonActivityType)
                .append(qPalXEMicroLesson)
                .append(eLearningMediaContent)
                .append(entryDate)
                .append(microLessonActivityActive)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("microLessonActivityName", microLessonActivityName)
                .append("microLessonActivityDescription", microLessonActivityDescription)
                .append("microLessonActivityType", microLessonActivityType)
                .append("qPalXEMicroLesson", qPalXEMicroLesson)
                .append("eLearningMediaContent", eLearningMediaContent)
                .append("entryDate", entryDate)
                .append("microLessonActivityActive", microLessonActivityActive)
                .toString();
    }


    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final QPalXEMicroLessonActivity qPalXEMicroLessonActivity = new QPalXEMicroLessonActivity();

        public Builder microLessonActivityName(String microLessonActivityName) {
            qPalXEMicroLessonActivity.microLessonActivityName = microLessonActivityName;
            return this;
        }

        public Builder microLessonActivityDescription(String microLessonActivityDescription) {
            qPalXEMicroLessonActivity.microLessonActivityDescription = microLessonActivityDescription;
            return this;
        }

        public Builder microLessonActivityType(MicroLessonActivityTypeE microLessonActivityType) {
            qPalXEMicroLessonActivity.microLessonActivityType = microLessonActivityType;
            return this;
        }

        public Builder qPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
            qPalXEMicroLessonActivity.qPalXEMicroLesson = qPalXEMicroLesson;
            return this;
        }

        public Builder eLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
            qPalXEMicroLessonActivity.eLearningMediaContent = eLearningMediaContent;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            qPalXEMicroLessonActivity.entryDate = entryDate;
            return this;
        }

        public Builder microLessonActivityActive(boolean microLessonActivityActive) {
            qPalXEMicroLessonActivity.microLessonActivityActive = microLessonActivityActive;
            return this;
        }

        public QPalXEMicroLessonActivity build() {
            return qPalXEMicroLessonActivity;
        }
    }
}
