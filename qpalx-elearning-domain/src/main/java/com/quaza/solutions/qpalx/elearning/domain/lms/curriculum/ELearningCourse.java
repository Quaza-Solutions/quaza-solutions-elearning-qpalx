package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Business Object representing an Electronic Course on the ELMS.  ELearningCourse's are directly tied to a
 * specific Curriculum.
 * 
 * @author manyce400
 */
@Entity
@Table(name="ELearningCourse")
public class ELearningCourse {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="CourseName", nullable=false, length=255)
	private String courseName;
	
	@Column(name="CourseDescription", nullable=false, length=255)
	private String courseDescription;


	// Ceiling ProficiencyRankingScale should always be specified for each ELearningCourse.
	// Students can only take ElearningCourse's inclusive between the specified ceiling and floor ProficiencyRanking
	@Column(name="ProficiencyRankingScaleCeiling", nullable=false, length=10)
	@Enumerated(EnumType.STRING)
	private ProficiencyRankingScaleE proficiencyRankingScaleCeiling;

    // Floor ProficiencyRankingScale should always be specified for each ELearningCourse.
    // Students can only take ElearningCourse's inclusive between the specified ceiling and floor ProficiencyRanking
    @Column(name="ProficiencyRankingScaleFloor", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private ProficiencyRankingScaleE proficiencyRankingScaleFloor;

    // ELearningCurriculum that this course is associated with.
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ELearningCurriculumID", nullable = false)
	private ELearningCurriculum eLearningCurriculum;

    // Optional QPalXEducationalInstitution, IF this is set this ELearningCourse should only be accessible by QPalXUser's
    // matriculated/attending that specific Educational Institution.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalXEducationalInstitutionID", nullable = true)
    private QPalXEducationalInstitution qPalXEducationalInstitution;

    // DateTime that the Course was uploaded into the QPalX platform.
    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;

	// IF set to true then this ELearningCourse is currently active and can be accessed by QPalX Users
	@Column(name="CourseActive", nullable = true, columnDefinition = "TINYINT", length = 1)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean courseActive;

    // Mapping to all the E-Learning course activities created for this E
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eLearningCourse")
    private Set<ELearningCourseActivity> eLearningCourseActivities = new HashSet<ELearningCourseActivity>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

    public ProficiencyRankingScaleE getProficiencyRankingScaleCeiling() {
        return proficiencyRankingScaleCeiling;
    }

    public void setProficiencyRankingScaleCeiling(ProficiencyRankingScaleE proficiencyRankingScaleCeiling) {
        this.proficiencyRankingScaleCeiling = proficiencyRankingScaleCeiling;
    }

    public ProficiencyRankingScaleE getProficiencyRankingScaleFloor() {
        return proficiencyRankingScaleFloor;
    }

    public void setProficiencyRankingScaleFloor(ProficiencyRankingScaleE proficiencyRankingScaleFloor) {
        this.proficiencyRankingScaleFloor = proficiencyRankingScaleFloor;
    }

    public ELearningCurriculum geteLearningCurriculum() {
        return eLearningCurriculum;
    }

    public void seteLearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        this.eLearningCurriculum = eLearningCurriculum;
    }

    public QPalXEducationalInstitution getqPalXEducationalInstitution() {
        return qPalXEducationalInstitution;
    }

    public void setqPalXEducationalInstitution(QPalXEducationalInstitution qPalXEducationalInstitution) {
        this.qPalXEducationalInstitution = qPalXEducationalInstitution;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isCourseActive() {
        return courseActive;
    }

    public void setCourseActive(boolean courseActive) {
        this.courseActive = courseActive;
    }

    public void addELearningCourseActivity(final ELearningCourseActivity eLearningCourseActivity) {
        Assert.notNull(eLearningCourseActivity, "eLearningCourseActivity cannot be null");
        eLearningCourseActivities.add(eLearningCourseActivity);
    }

    public void removeELearningCourseActivity(final ELearningCourseActivity eLearningCourseActivity) {
        Assert.notNull(eLearningCourseActivity, "eLearningCourseActivity cannot be null");
        eLearningCourseActivities.remove(eLearningCourseActivity);
    }

    public Set<ELearningCourseActivity> getELearningCourseActivities() {
        return ImmutableSet.copyOf(eLearningCourseActivities);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ELearningCourse that = (ELearningCourse) o;

        return new EqualsBuilder()
                .append(courseActive, that.courseActive)
                .append(id, that.id)
                .append(courseName, that.courseName)
                .append(courseDescription, that.courseDescription)
                .append(proficiencyRankingScaleCeiling, that.proficiencyRankingScaleCeiling)
                .append(proficiencyRankingScaleFloor, that.proficiencyRankingScaleFloor)
                .append(eLearningCurriculum, that.eLearningCurriculum)
                .append(qPalXEducationalInstitution, that.qPalXEducationalInstitution)
                .append(entryDate, that.entryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(courseName)
                .append(courseDescription)
                .append(proficiencyRankingScaleCeiling)
                .append(proficiencyRankingScaleFloor)
                .append(eLearningCurriculum)
                .append(qPalXEducationalInstitution)
                .append(entryDate)
                .append(courseActive)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("courseName", courseName)
                .append("courseDescription", courseDescription)
                .append("proficiencyRankingScaleCeiling", proficiencyRankingScaleCeiling)
                .append("proficiencyRankingScaleFloor", proficiencyRankingScaleFloor)
                .append("eLearningCurriculum", eLearningCurriculum)
                .append("qPalXEducationalInstitution", qPalXEducationalInstitution)
                .append("entryDate", entryDate)
                .append("courseActive", courseActive)
                .toString();
    }


    public static final class Builder {

        private final ELearningCourse eLearningCourse = new ELearningCourse();

        public Builder() {
        }

        public Builder courseName(String courseName) {
            eLearningCourse.courseName = courseName;
            return this;
        }

        public Builder courseDescription(String courseDescription) {
            eLearningCourse.courseDescription =courseDescription;
            return this;
        }

        public Builder courseActive(boolean courseActive) {
            eLearningCourse.courseActive = courseActive;
            return this;
        }

        public Builder proficiencyRankingScaleCeiling(ProficiencyRankingScaleE proficiencyRankingScaleCeiling) {
            eLearningCourse.proficiencyRankingScaleCeiling = proficiencyRankingScaleCeiling;
            return this;
        }

        public Builder proficiencyRankingScaleFloor(ProficiencyRankingScaleE proficiencyRankingScaleFloor) {
            eLearningCourse.proficiencyRankingScaleFloor = proficiencyRankingScaleFloor;
            return this;
        }

        public Builder eLearningCurriculum(ELearningCurriculum eLearningCurriculum) {
            eLearningCourse.eLearningCurriculum = eLearningCurriculum;
            return this;
        }

        public Builder qPalXEducationalInstitution( QPalXEducationalInstitution qPalXEducationalInstitution) {
            eLearningCourse.qPalXEducationalInstitution = qPalXEducationalInstitution;
            return  this;
        }

        public Builder entryDate(DateTime entryDate) {
            eLearningCourse.entryDate = entryDate;
            return this;
        }

        public ELearningCourse build() {
            return eLearningCourse;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
