package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
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

    // Floor ProficiencyRankingScale should always be specified for each ELearningCourse.
    // Students can only take ElearningCourse's inclusive between the specified ceiling and floor ProficiencyRanking
    @Column(name="ProficiencyRankingScaleFloor", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private ProficiencyRankingScaleE proficiencyRankingScaleFloor;

    // Ceiling ProficiencyRankingScale should always be specified for each ELearningCourse.
    // Students can only take ElearningCourse's inclusive between the specified ceiling and floor ProficiencyRanking
    @Column(name="ProficiencyRankingScaleCeiling", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private ProficiencyRankingScaleE proficiencyRankingScaleCeiling;


    // Always fetch this Eager as we always need the course readily available to use.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ELearningCourseID", nullable = false)
    private ELearningCourse eLearningCourse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TutorialLevelCalendarID", nullable = false)
    private TutorialLevelCalendar tutorialLevelCalendar;

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

    public void seteLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    public ProficiencyRankingScaleE getProficiencyRankingScaleFloor() {
        return proficiencyRankingScaleFloor;
    }

    public void setProficiencyRankingScaleFloor(ProficiencyRankingScaleE proficiencyRankingScaleFloor) {
        this.proficiencyRankingScaleFloor = proficiencyRankingScaleFloor;
    }

    public ProficiencyRankingScaleE getProficiencyRankingScaleCeiling() {
        return proficiencyRankingScaleCeiling;
    }

    public void setProficiencyRankingScaleCeiling(ProficiencyRankingScaleE proficiencyRankingScaleCeiling) {
        this.proficiencyRankingScaleCeiling = proficiencyRankingScaleCeiling;
    }

    public ELearningCourse geteLearningCourse() {
        return eLearningCourse;
    }

    public void seteLearningCourse(ELearningCourse eLearningCourse) {
        this.eLearningCourse = eLearningCourse;
    }

    public TutorialLevelCalendar getTutorialLevelCalendar() {
        return tutorialLevelCalendar;
    }

    public void setTutorialLevelCalendar(TutorialLevelCalendar tutorialLevelCalendar) {
        this.tutorialLevelCalendar = tutorialLevelCalendar;
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
                .append(proficiencyRankingScaleFloor, that.proficiencyRankingScaleFloor)
                .append(proficiencyRankingScaleCeiling, that.proficiencyRankingScaleCeiling)
                .append(eLearningCourse, that.eLearningCourse)
                .append(tutorialLevelCalendar, that.tutorialLevelCalendar)
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
                .append(proficiencyRankingScaleFloor)
                .append(proficiencyRankingScaleCeiling)
                .append(eLearningCourse)
                .append(tutorialLevelCalendar)
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
                .append("proficiencyRankingScaleFloor", proficiencyRankingScaleFloor)
                .append("proficiencyRankingScaleCeiling", proficiencyRankingScaleCeiling)
                .append("eLearningCourse", eLearningCourse)
                .append("tutorialLevelCalendar", tutorialLevelCalendar)
                .append("entryDate", entryDate)
                .append("activityActive", activityActive)
                .toString();
    }

    public static final class Builder {

        private final ELearningCourseActivity eLearningCourseActivity = new ELearningCourseActivity();

        public Builder activityName(String activityName) {
            eLearningCourseActivity.activityName = activityName;
            return this;
        }

        public Builder activityDescription(String activityDescription) {
            eLearningCourseActivity.activityDescription = activityDescription;
            return this;
        }

        public Builder learningActivityE(LearningActivityE learningActivityE) {
            eLearningCourseActivity.learningActivityE = learningActivityE;
            return this;
        }

        public Builder eLearningMediaContent(ELearningMediaContent eLearningMediaContent) {
            eLearningCourseActivity.eLearningMediaContent = eLearningMediaContent;
            return this;
        }

        public Builder eLearningCourse(ELearningCourse eLearningCourse) {
            eLearningCourseActivity.eLearningCourse = eLearningCourse;
            return this;
        }

        public Builder entryDate(DateTime entryDate) {
            eLearningCourseActivity.entryDate = entryDate;
            return this;
        }

        public Builder activityActive(boolean activityActive) {
            eLearningCourseActivity.activityActive = activityActive;
            return this;
        }

        public ELearningCourseActivity build() {
            return eLearningCourseActivity;
        }

    }

    public static Builder builder() {
        return new Builder();
    }
}
