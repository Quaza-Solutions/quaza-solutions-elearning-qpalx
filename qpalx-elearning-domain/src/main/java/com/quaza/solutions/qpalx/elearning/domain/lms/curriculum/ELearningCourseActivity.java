package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Business Object represesnting an E Learning Activity.
 *
 * ELearningCourseActivity are directly tied to ELearningCourse.  Each ELearningCourse could have a number of activities
 * that QPalX Students that take as part of their Adaptive Learning Experience.
 *
 * @author manyce400
 */
@Entity
@Table(name="ELearningCourseActivity")
public class ELearningCourseActivity {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="ActivityName", nullable=false, length=255)
    private String activityName;

    @Column(name="ActivityDescription", nullable=false, length=255)
    private String activityDescription;

    // Tracks and records the learning activity taken/engaged in as part of this AdaptiveLearningExperience
    @Column(name="LearningActivity", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private LearningActivityE learningActivityE;

    // Provides information on Media Content that is associated with this activity.
    @Embedded
    private ELearningMediaContent eLearningMediaContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ELearningCourseID", nullable = false)
    private ELearningCourse eLearningCourse;

    // DateTime that the ELearning Media Content was uploaded on the QPalX platform.
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

    // IF set to true then this ELearningCourseActivity is currently not active and should not be viewable
    @Column(name="ActivityActive", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean activityActive;


    public ELearningCourseActivity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public LearningActivityE getLearningActivityE() {
        return learningActivityE;
    }

    public void setLearningActivityE(LearningActivityE learningActivityE) {
        this.learningActivityE = learningActivityE;
    }

    public ELearningMediaContent geteLearningMediaContent() {
        return eLearningMediaContent;
    }

    public void setELearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    public ELearningCourse getELearningCourse() {
        return eLearningCourse;
    }

    public void setELearningCourse(ELearningCourse eLearningCourse) {
        this.eLearningCourse = eLearningCourse;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isActivityActive() {
        return activityActive;
    }

    public void setActivityActive(boolean activityActive) {
        this.activityActive = activityActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ELearningCourseActivity that = (ELearningCourseActivity) o;

        return new EqualsBuilder()
                .append(activityActive, that.activityActive)
                .append(id, that.id)
                .append(activityName, that.activityName)
                .append(activityDescription, that.activityDescription)
                .append(learningActivityE, that.learningActivityE)
                .append(eLearningMediaContent, that.eLearningMediaContent)
                .append(eLearningCourse, that.eLearningCourse)
                .append(entryDate, that.entryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(activityName)
                .append(activityDescription)
                .append(learningActivityE)
                .append(eLearningMediaContent)
                .append(eLearningCourse)
                .append(entryDate)
                .append(activityActive)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("activityName", activityName)
                .append("activityDescription", activityDescription)
                .append("learningActivityE", learningActivityE)
                .append("eLearningMediaContent", eLearningMediaContent)
                .append("eLearningCourse", eLearningCourse)
                .append("entryDate", entryDate)
                .append("activityActive", activityActive)
                .toString();
    }
}
