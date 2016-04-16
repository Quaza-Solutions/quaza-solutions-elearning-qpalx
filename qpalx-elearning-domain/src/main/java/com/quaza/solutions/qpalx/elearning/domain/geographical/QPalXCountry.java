package com.quaza.solutions.qpalx.elearning.domain.geographical;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 *
 * @author manyce400
 */
@Entity
@Table(name="QPalXCountry")
public class QPalXCountry {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="CountryName", nullable=false, length=100, unique=true)
    private String countryName;

    @Column(name="CountryCode", nullable=false, length=2)
    private String countryCode;

    @Column(name="CountryCurrency", nullable=false, length=10)
    private String countryCurrency;

    // Geographical region area that Country is mapped to
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GeographicalRegionID", nullable = false)
    private GeographicalRegion geographicalRegion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QPalXCountry() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCurrency() {
        return countryCurrency;
    }

    public void setCountryCurrency(String countryCurrency) {
        this.countryCurrency = countryCurrency;
    }

    public GeographicalRegion getGeographicalRegion() {
        return geographicalRegion;
    }

    public void setGeographicalRegion(GeographicalRegion geographicalRegion) {
        this.geographicalRegion = geographicalRegion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QPalXCountry that = (QPalXCountry) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(countryName, that.countryName)
                .append(countryCode, that.countryCode)
                .append(countryCurrency, that.countryCurrency)
                .append(geographicalRegion, that.geographicalRegion)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(countryName)
                .append(countryCode)
                .append(countryCurrency)
                .append(geographicalRegion)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("countryName", countryName)
                .append("countryCode", countryCode)
                .append("countryCurrency", countryCurrency)
                .append("geographicalRegion", geographicalRegion)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final QPalXCountry qPalXCountry = new QPalXCountry();

        public Builder countryName(String countryName) {
            qPalXCountry.setCountryName(countryName);
            return this;
        }

        public Builder countryCode(String countryCode) {
            qPalXCountry.setCountryCode(countryCode);
            return this;
        }

        public Builder countryCurrency(String countryCurrency) {
            qPalXCountry.setCountryCurrency(countryCurrency);
            return this;
        }

        public Builder geographicalRegion(GeographicalRegion geographicalRegion) {
            qPalXCountry.geographicalRegion = geographicalRegion;
            return this;
        }

        public QPalXCountry build() {
            return this.qPalXCountry;
        }
    }
}
