package com.quaza.solutions.qpalx.elearning.domain.sstatic.content;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name="StaticContentConfiguration")
public class StaticContentConfiguration {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="StaticContentName", nullable=false, length=100, unique=true)
    @Enumerated(EnumType.STRING)
    private StaticContentConfigurationTypeE staticContentConfigurationType;

    @Column(name="StaticContentPhysicalLocation", nullable=false, length=200, unique=true)
    private String staticContentPhysicalLocation;

    @Column(name="StaticContentApplicationContextLocation", nullable=false, length=200)
    private String staticContentApplicationContextLocation;


    public StaticContentConfiguration() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StaticContentConfigurationTypeE getStaticContentConfigurationType() {
        return staticContentConfigurationType;
    }

    public void setStaticContentConfigurationType(StaticContentConfigurationTypeE staticContentConfigurationType) {
        this.staticContentConfigurationType = staticContentConfigurationType;
    }

    public String getStaticContentPhysicalLocation() {
        return staticContentPhysicalLocation;
    }

    public void setStaticContentPhysicalLocation(String staticContentPhysicalLocation) {
        this.staticContentPhysicalLocation = staticContentPhysicalLocation;
    }

    public String getStaticContentApplicationContextLocation() {
        return staticContentApplicationContextLocation;
    }

    public void setStaticContentApplicationContextLocation(String staticContentApplicationContextLocation) {
        this.staticContentApplicationContextLocation = staticContentApplicationContextLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StaticContentConfiguration that = (StaticContentConfiguration) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(staticContentConfigurationType, that.staticContentConfigurationType)
                .append(staticContentPhysicalLocation, that.staticContentPhysicalLocation)
                .append(staticContentApplicationContextLocation, that.staticContentApplicationContextLocation)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(staticContentConfigurationType)
                .append(staticContentPhysicalLocation)
                .append(staticContentApplicationContextLocation)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("staticContentConfigurationType", staticContentConfigurationType)
                .append("staticContentPhysicalLocation", staticContentPhysicalLocation)
                .append("staticContentApplicationContextLocation", staticContentApplicationContextLocation)
                .toString();
    }

}
