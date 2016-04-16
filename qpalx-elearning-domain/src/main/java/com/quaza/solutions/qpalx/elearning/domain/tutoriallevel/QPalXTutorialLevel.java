package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
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
@Table(name="QPalXTutorialLevel")
public class QPalXTutorialLevel {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="TutorialLevel", nullable=false, length=50, unique=true)
    private String tutorialLevel;

    @Column(name="TutorialLevelDescription", nullable=false, length=100)
    private String tutorialLevelDescription;

    // Tutorial level is attached to a specific geographical region
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GeographicalRegionID", nullable = false)
    private GeographicalRegion geographicalRegion;

    @Column(name="Enabled", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean enabled;

    // Date tutorial level was created
    @Column(name="EntryDateTime", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDateTime;


    public QPalXTutorialLevel() {
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

    public GeographicalRegion getGeographicalRegion() {
        return geographicalRegion;
    }

    public void setGeographicalRegion(GeographicalRegion geographicalRegion) {
        this.geographicalRegion = geographicalRegion;
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

        QPalXTutorialLevel that = (QPalXTutorialLevel) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(tutorialLevel, that.tutorialLevel)
                .append(tutorialLevelDescription, that.tutorialLevelDescription)
                .append(geographicalRegion, that.geographicalRegion)
                .append(entryDateTime, that.entryDateTime)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(tutorialLevel)
                .append(tutorialLevelDescription)
                .append(geographicalRegion)
                .append(entryDateTime)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("tutorialLevel", tutorialLevel)
                .append("tutorialLevelDescription", tutorialLevelDescription)
                .append("geographicalRegion", geographicalRegion)
                .append("entryDateTime", entryDateTime)
                .toString();
    }
}
