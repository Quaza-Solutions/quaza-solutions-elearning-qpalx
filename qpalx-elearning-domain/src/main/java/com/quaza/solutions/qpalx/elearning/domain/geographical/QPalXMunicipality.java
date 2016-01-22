package com.quaza.solutions.qpalx.elearning.domain.geographical;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name = "QPalXMunicipality")
public class QPalXMunicipality {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "Code", nullable = false, length = 3)
    private String code;

    @Column(name = "TimeZone", nullable = false, length = 3)
    private String timeZone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXCountryID", nullable = false)
    private QPalXCountry qPalXCountry;

    public QPalXMunicipality() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public QPalXCountry getQPalXCountry() {
        return qPalXCountry;
    }

    public void setQPalXCountry(QPalXCountry qPalXCountry) {
        this.qPalXCountry = qPalXCountry;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(code)
                .append(timeZone)
                .append(qPalXCountry)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QPalXMunicipality that = (QPalXMunicipality) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(code, that.code)
                .append(timeZone, that.timeZone)
                .append(qPalXCountry, that.qPalXCountry)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("code", code)
                .append("timeZone", timeZone)
                .append("qPalXCountry", qPalXCountry)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final QPalXMunicipality qPalXMunicipality = new QPalXMunicipality();

        public Builder addName(String name) {
            qPalXMunicipality.setName(name);
            return this;
        }

        public Builder addCode(String code) {
            qPalXMunicipality.setCode(code);
            return this;
        }

        public Builder addTimeZone(String timeZone) {
            qPalXMunicipality.setTimeZone(timeZone);
            return this;
        }

        public Builder addQPalXCountry(QPalXCountry qPalXCountry) {
            qPalXMunicipality.setQPalXCountry(qPalXCountry);
            return this;
        }

        public QPalXMunicipality get() {
            return qPalXMunicipality;
        }
    }


}
