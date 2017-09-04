package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

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
@Table(name="GlobalStudentPerformanceAudit")
public class GlobalStudentPerformanceAudit {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    // The QPalxUser responsible for auditing performance for Student User in this module.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AuditorQPalxUserID", nullable = false)
    private QPalXUser auditorQPalxUser;

    // The QPalxUser Student that has their performance being tracked
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentQPalxUserID", nullable = false)
    private QPalXUser studentQPalxUser;

    // Date performance monitoring started
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="EffectiveDate", nullable=true)
    private DateTime effectiveDate;

    // Administratively QPalx can disable any global performance monitor by any user.
    @Column(name="Enabled", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean enabled;


    public GlobalStudentPerformanceAudit() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QPalXUser getAuditorQPalxUser() {
        return auditorQPalxUser;
    }

    public void setAuditorQPalxUser(QPalXUser auditorQPalxUser) {
        this.auditorQPalxUser = auditorQPalxUser;
    }

    public QPalXUser getStudentQPalxUser() {
        return studentQPalxUser;
    }

    public void setStudentQPalxUser(QPalXUser studentQPalxUser) {
        this.studentQPalxUser = studentQPalxUser;
    }

    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GlobalStudentPerformanceAudit that = (GlobalStudentPerformanceAudit) o;

        return new EqualsBuilder()
                .append(enabled, that.enabled)
                .append(id, that.id)
                .append(auditorQPalxUser, that.auditorQPalxUser)
                .append(studentQPalxUser, that.studentQPalxUser)
                .append(effectiveDate, that.effectiveDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(auditorQPalxUser)
                .append(studentQPalxUser)
                .append(effectiveDate)
                .append(enabled)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("auditorQPalxUser", auditorQPalxUser)
                .append("studentQPalxUser", studentQPalxUser)
                .append("effectiveDate", effectiveDate)
                .append("enabled", enabled)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private GlobalStudentPerformanceAudit globalStudentPerformanceAudit = new GlobalStudentPerformanceAudit();

        public Builder auditorQPalxUser(QPalXUser auditorQPalxUser) {
            globalStudentPerformanceAudit.auditorQPalxUser = auditorQPalxUser;
            return this;
        }

        public Builder studentQPalxUser(QPalXUser studentQPalxUser) {
            globalStudentPerformanceAudit.studentQPalxUser = studentQPalxUser;
            return this;
        }

        public Builder effectiveDate(DateTime effectiveDate) {
            globalStudentPerformanceAudit.effectiveDate = effectiveDate;
            return this;
        }

        public Builder enabled(boolean enabled) {
            globalStudentPerformanceAudit.enabled = enabled;
            return this;
        }

        public GlobalStudentPerformanceAudit build() {
            return globalStudentPerformanceAudit;
        }

    }

}
