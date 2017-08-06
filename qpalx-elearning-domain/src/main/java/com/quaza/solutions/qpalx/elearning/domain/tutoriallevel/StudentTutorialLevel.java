package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Defines all pre-established and supported QPalX Tutorial Levels.  All students must have a unique and
 * specific tutorial level.  Students can only access Subject matter and ELearningCourse content directly corelated to
 * their tutorial level.
 *
 * @author manyce400
 */
@Entity
@Table(name="StudentTutorialLevel")
public class StudentTutorialLevel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="TutorialLevel", nullable=false, length=50, unique=true)
    private String tutorialLevel;

    @Column(name="TutorialLevelDescription", nullable=false, length=100)
    private String tutorialLevelDescription;

    // Tutorial level is attached to a specific Country
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXCountryID", nullable = false)
    @JsonIgnore
    private QPalXCountry qPalXCountry;

    @Column(name="Enabled", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @JsonIgnore
    private boolean enabled;

    @Column(name="AcademicLevel", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private AcademicLevelE academicLevel;

    // Date tutorial level was created
    @Column(name="EntryDateTime", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonIgnore
    private DateTime entryDateTime;


    public static final String CLASS_ATTRIBUTE_IDENTIFIER = "StudentTutorialLevel";


    public StudentTutorialLevel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTutorialLevel() {
        return tutorialLevel;
    }

    public void setTutorialLevel(String tutorialLevel) {
        this.tutorialLevel = tutorialLevel;
    }

    public String getTutorialLevelDescription() {
        return tutorialLevelDescription;
    }

    public void setTutorialLevelDescription(String tutorialLevelDescription) {
        this.tutorialLevelDescription = tutorialLevelDescription;
    }

    public QPalXCountry getQPalXCountry() {
        return qPalXCountry;
    }

    public void setQPalXCountry(QPalXCountry qPalXCountry) {
        this.qPalXCountry = qPalXCountry;
    }

    public AcademicLevelE getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevelE academicLevel) {
        this.academicLevel = academicLevel;
    }

    public DateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(DateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StudentTutorialLevel that = (StudentTutorialLevel) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(tutorialLevel, that.tutorialLevel)
                .append(tutorialLevelDescription, that.tutorialLevelDescription)
                .append(qPalXCountry, that.qPalXCountry)
                .append(entryDateTime, that.entryDateTime)
                .append(academicLevel, that.academicLevel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tutorialLevel)
                .append(tutorialLevelDescription)
                .append(qPalXCountry)
                .append(entryDateTime)
                .append(academicLevel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("tutorialLevel", tutorialLevel)
                .append("tutorialLevelDescription", tutorialLevelDescription)
                .append("qPalXCountry", qPalXCountry)
                .append("entryDateTime", entryDateTime)
                .append("academicLevel", academicLevel)
                .toString();
    }
}
