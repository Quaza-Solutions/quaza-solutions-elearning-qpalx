package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * This business domain object tracks all details of Student Enrolment on the QPalX Platform.
 *
 *
 * @author manyce400
 */
@Entity
@Table(name="StudentEnrolmentRecord")
public class StudentEnrolmentRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    // The actual QPalXUser record for this Student.
    // Eagerly loaded as this piece of information is needed for all Students.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qpalxUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentTutorialGradeID", nullable = false)
    private StudentTutorialGrade studentTutorialGrade;

    // Effective date for which students enrolment in StudentTutorialGrade began
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="EnrolmentEffectiveDate", nullable=false)
    private DateTime enrolmentEffectiveDate;

    // Effective date for which students enrolment in StudentTutorialGrade ended.  IF null student still enrolled/active
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="EnrolmentEndDate", nullable=true)
    private DateTime enrolmentEndDate;

    // Fetch this Eager, nullable since user does not have to be tied to an EducationalInstitution.
    // EducationInstitution student was attending at time of enrollment.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXEducationalInstitutionID", nullable = true)
    private QPalXEducationalInstitution educationalInstitution;




    public StudentEnrolmentRecord() {

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

    public StudentTutorialGrade getStudentTutorialGrade() {
        return studentTutorialGrade;
    }

    public void setStudentTutorialGrade(StudentTutorialGrade studentTutorialGrade) {
        this.studentTutorialGrade = studentTutorialGrade;
    }

    public DateTime getEnrolmentEffectiveDate() {
        return enrolmentEffectiveDate;
    }

    public void setEnrolmentEffectiveDate(DateTime enrolmentEffectiveDate) {
        this.enrolmentEffectiveDate = enrolmentEffectiveDate;
    }

    public DateTime getEnrolmentEndDate() {
        return enrolmentEndDate;
    }

    public void setEnrolmentEndDate(DateTime enrolmentEndDate) {
        this.enrolmentEndDate = enrolmentEndDate;
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

        StudentEnrolmentRecord that = (StudentEnrolmentRecord) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(qpalxUser, that.qpalxUser)
                .append(studentTutorialGrade, that.studentTutorialGrade)
                .append(enrolmentEffectiveDate, that.enrolmentEffectiveDate)
                .append(enrolmentEndDate, that.enrolmentEndDate)
                .append(educationalInstitution, that.educationalInstitution)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(qpalxUser)
                .append(studentTutorialGrade)
                .append(enrolmentEffectiveDate)
                .append(enrolmentEndDate)
                .append(educationalInstitution)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("qpalxUser", qpalxUser)
                .append("studentTutorialGrade", studentTutorialGrade)
                .append("enrolmentEffectiveDate", enrolmentEffectiveDate)
                .append("enrolmentEndDate", enrolmentEndDate)
                .append("educationalInstitution", educationalInstitution)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final StudentEnrolmentRecord studentEnrolmentRecord = new StudentEnrolmentRecord();

        public Builder qpalxUser(QPalXUser qPalXUser) {
            studentEnrolmentRecord.qpalxUser = qPalXUser;
            return this;
        }

        public Builder studentTutorialGrade(StudentTutorialGrade studentTutorialGrade) {
            studentEnrolmentRecord.studentTutorialGrade = studentTutorialGrade;
            return this;
        }

        public Builder enrolmentEffectiveDate(DateTime enrolmentEffectiveDate) {
            studentEnrolmentRecord.enrolmentEffectiveDate = enrolmentEffectiveDate;
            return this;
        }

        public Builder enrolmentEndDate(DateTime enrolmentEndDate) {
            studentEnrolmentRecord.enrolmentEndDate = enrolmentEndDate;
            return this;
        }

        public Builder educationalInstitution(QPalXEducationalInstitution educationalInstitution) {
            studentEnrolmentRecord.educationalInstitution = educationalInstitution;
            return this;
        }

        public StudentEnrolmentRecord build() {
            return studentEnrolmentRecord;
        }
    }
}
