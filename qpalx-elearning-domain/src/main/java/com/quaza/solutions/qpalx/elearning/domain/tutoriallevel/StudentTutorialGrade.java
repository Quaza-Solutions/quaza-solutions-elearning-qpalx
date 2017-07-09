package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Tutorial Grade represents the current specific level for a Student under a tutorial level.
 *
 * @author manyce400
 */
@Entity
@Table(name="StudentTutorialGrade")
public class StudentTutorialGrade {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="TutorialGrade", nullable=false, length=50, unique=true)
    private String tutorialGrade;

    // Determines if this tutorial grade is active in the QPalX platform
    @Column(name="Enabled", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean enabled;

    // Date tutorial level was created
    @Column(name="EntryDateTime", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDateTime;

    // Fetch this eager as we want to be able to actively look this up always on demand
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentTutorialLevelID", nullable = false)
    private StudentTutorialLevel studentTutorialLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTutorialGrade() {
        return tutorialGrade;
    }

    public void setTutorialGrade(String tutorialGrade) {
        this.tutorialGrade = tutorialGrade;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(DateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public StudentTutorialLevel getStudentTutorialLevel() {
        return studentTutorialLevel;
    }

    public void setStudentTutorialLevel(StudentTutorialLevel studentTutorialLevel) {
        this.studentTutorialLevel = studentTutorialLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StudentTutorialGrade that = (StudentTutorialGrade) o;

        return new EqualsBuilder()
                .append(enabled, that.enabled)
                .append(id, that.id)
                .append(tutorialGrade, that.tutorialGrade)
                .append(entryDateTime, that.entryDateTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tutorialGrade)
                .append(enabled)
                .append(entryDateTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("studentTutorialGrade", tutorialGrade)
                .append("enabled", enabled)
                .append("entryDateTime", entryDateTime)
                .append("studentTutorialLevel", studentTutorialLevel)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final StudentTutorialGrade studentTutorialGrade = new StudentTutorialGrade();

        public Builder tutorialGrade(String tutorialGradeName) {
            studentTutorialGrade.setTutorialGrade(tutorialGradeName);
            return this;
        }

        public Builder enabled(boolean enabled) {
            studentTutorialGrade.enabled = enabled;
            return this;
        }

        public Builder entryDateTime(DateTime entryDateTime) {
            studentTutorialGrade.entryDateTime = entryDateTime;
            return this;
        }

        public Builder studentTutorialLevel(StudentTutorialLevel studentTutorialLevel) {
            studentTutorialGrade.studentTutorialLevel = studentTutorialLevel;
            return this;
        }

        public StudentTutorialGrade build() {
            return studentTutorialGrade;
        }

    }
}
