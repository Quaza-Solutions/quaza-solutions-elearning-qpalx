package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 *
 * @author manyce400
 */
@Entity
@Table(name="TutorialGrade")
public class TutorialGrade {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXTutorialLevelID", nullable = false)
    private QPalXTutorialLevel qPalXTutorialLevel;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TutorialGrade that = (TutorialGrade) o;

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
                .append("tutorialGrade", tutorialGrade)
                .append("enabled", enabled)
                .append("entryDateTime", entryDateTime)
                .append("qPalXTutorialLevel", qPalXTutorialLevel)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final TutorialGrade tutorialGrade = new TutorialGrade();

        public Builder tutorialGrade(String tutorialGradeName) {
            tutorialGrade.setTutorialGrade(tutorialGradeName);
            return this;
        }

        public Builder enabled(boolean enabled) {
            tutorialGrade.enabled = enabled;
            return this;
        }

        public Builder entryDateTime(DateTime entryDateTime) {
            tutorialGrade.entryDateTime = entryDateTime;
            return this;
        }

        public Builder qPalXTutorialLevel(QPalXTutorialLevel qPalXTutorialLevel) {
            tutorialGrade.qPalXTutorialLevel = qPalXTutorialLevel;
            return this;
        }

        public TutorialGrade build() {
            return tutorialGrade;
        }

    }
}
