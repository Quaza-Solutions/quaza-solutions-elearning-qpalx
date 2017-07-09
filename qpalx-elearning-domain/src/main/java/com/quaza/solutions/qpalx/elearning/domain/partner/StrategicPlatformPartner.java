package com.quaza.solutions.qpalx.elearning.domain.partner;

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
@Table(name="StrategicPlatformPartner")
public class StrategicPlatformPartner {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="PlatformPartnerName", nullable=false, length=25, unique = true)
    private String platformPartnerName;

    @Column(name="PlatformPartnerType", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PlatformPartnerTypeE platformPartnerTypeE;

    @Column(name="EntryDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime entryDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlatformPartnerName() {
        return platformPartnerName;
    }

    public void setPlatformPartnerName(String platformPartnerName) {
        this.platformPartnerName = platformPartnerName;
    }

    public PlatformPartnerTypeE getPlatformPartnerTypeE() {
        return platformPartnerTypeE;
    }

    public void setPlatformPartnerTypeE(PlatformPartnerTypeE platformPartnerTypeE) {
        this.platformPartnerTypeE = platformPartnerTypeE;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StrategicPlatformPartner that = (StrategicPlatformPartner) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(platformPartnerName, that.platformPartnerName)
                .append(platformPartnerTypeE, that.platformPartnerTypeE)
                .append(entryDate, that.entryDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(platformPartnerName)
                .append(platformPartnerTypeE)
                .append(entryDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("platformPartnerName", platformPartnerName)
                .append("platformPartnerTypeE", platformPartnerTypeE)
                .append("entryDate", entryDate)
                .toString();
    }
}
