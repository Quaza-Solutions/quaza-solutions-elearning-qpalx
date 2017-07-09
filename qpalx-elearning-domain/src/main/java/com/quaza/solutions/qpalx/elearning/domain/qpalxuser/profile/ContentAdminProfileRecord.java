package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * For all QPalX users that are Content Administrators, this business domain object defines all attributes that define the type of Content
 * that user will be responsible for.
 *
 * @author manyce400
 */
@Entity
@Table(name="ContentAdminProfileRecord")
public class ContentAdminProfileRecord {





    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // The actual QPalXUser record for this Student.
    // Eagerly loaded as this piece of information is needed for all Students.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qpalxUser;


    // Identifies the tutorial level that this user will be able to administer content for.
    // Fetch this eager as we want to be able to actively look this up always on demand
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentTutorialLevelID", nullable = false)
    private StudentTutorialLevel studentTutorialLevel;


    // Determines if this tutorial grade is active in the QPalX platform
    @Column(name="Enabled", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean enabled;


    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="EffectiveDate", nullable=false)
    private DateTime effectiveDate;


    // IF school is set content created by this admin will only be visible to Students belonging to this institution
    // Fetch this Eager, nullable since content admin does not have to be tied to an EducationalInstitution.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXEducationalInstitutionID", nullable = true)
    private QPalXEducationalInstitution educationalInstitution;


    public ContentAdminProfileRecord() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QPalXUser getQpalxUser() {
        return qpalxUser;
    }

    public void setQpalxUser(QPalXUser qpalxUser) {
        this.qpalxUser = qpalxUser;
    }

    public StudentTutorialLevel getStudentTutorialLevel() {
        return studentTutorialLevel;
    }

    public void setStudentTutorialLevel(StudentTutorialLevel studentTutorialLevel) {
        this.studentTutorialLevel = studentTutorialLevel;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public QPalXEducationalInstitution getEducationalInstitution() {
        return educationalInstitution;
    }

    public void setEducationalInstitution(QPalXEducationalInstitution educationalInstitution) {
        this.educationalInstitution = educationalInstitution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ContentAdminProfileRecord that = (ContentAdminProfileRecord) o;

        return new EqualsBuilder()
                .append(enabled, that.enabled)
                .append(id, that.id)
                .append(qpalxUser, that.qpalxUser)
                .append(studentTutorialLevel, that.studentTutorialLevel)
                .append(effectiveDate, that.effectiveDate)
                .append(educationalInstitution, that.educationalInstitution)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(qpalxUser)
                .append(studentTutorialLevel)
                .append(enabled)
                .append(effectiveDate)
                .append(educationalInstitution)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("qpalxUser", qpalxUser)
                .append("studentTutorialLevel", studentTutorialLevel)
                .append("enabled", enabled)
                .append("effectiveDate", effectiveDate)
                .append("educationalInstitution", educationalInstitution)
                .toString();
    }

}
