package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialGrade;
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
@Table(name="QPalXStudentTutorialGrade")
public class QPalXStudentTutorialGrade {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QPalxUserID", nullable = false)
    private QPalXUser qpalxUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TutorialGradeID", nullable = false)
    private TutorialGrade tutorialGrade;

    // Effective date for which students enrolment in TutorialGrade began
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="EnrolmentEffectiveDate", nullable=false)
    private DateTime enrolmentEffectiveDate;

    // Effective date for which students enrolment in TutorialGrade ended.  IF null student still enrolled
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="EnrolmentEndDate", nullable=true)
    private DateTime enrolmentEndDate;


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

    public TutorialGrade getTutorialGrade() {
        return tutorialGrade;
    }

    public void setTutorialGrade(TutorialGrade tutorialGrade) {
        this.tutorialGrade = tutorialGrade;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QPalXStudentTutorialGrade that = (QPalXStudentTutorialGrade) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(qpalxUser, that.qpalxUser)
                .append(tutorialGrade, that.tutorialGrade)
                .append(enrolmentEffectiveDate, that.enrolmentEffectiveDate)
                .append(enrolmentEndDate, that.enrolmentEndDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(qpalxUser)
                .append(tutorialGrade)
                .append(enrolmentEffectiveDate)
                .append(enrolmentEndDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("qpalxUser", qpalxUser)
                .append("tutorialGrade", tutorialGrade)
                .append("enrolmentEffectiveDate", enrolmentEffectiveDate)
                .append("enrolmentEndDate", enrolmentEndDate)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final QPalXStudentTutorialGrade qPalXStudentTutorialGrade = new QPalXStudentTutorialGrade();

        public Builder qpalxUser(QPalXUser qPalXUser) {
            qPalXStudentTutorialGrade.qpalxUser = qPalXUser;
            return this;
        }

        public Builder tutorialGrade(TutorialGrade tutorialGrade) {
            qPalXStudentTutorialGrade.tutorialGrade = tutorialGrade;
            return this;
        }

        public Builder enrolmentEffectiveDate(DateTime enrolmentEffectiveDate) {
            qPalXStudentTutorialGrade.enrolmentEffectiveDate = enrolmentEffectiveDate;
            return this;
        }

        public Builder enrolmentEndDate(DateTime enrolmentEndDate) {
            qPalXStudentTutorialGrade.enrolmentEndDate = enrolmentEndDate;
            return this;
        }

        public QPalXStudentTutorialGrade build() {
            return qPalXStudentTutorialGrade;
        }
    }
}
