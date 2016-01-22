package com.quaza.solutions.qpalx.elearning.domain.socialnetwork;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name="SocialNetwork")
public class SocialNetwork {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="SocialNetworkName", nullable=false, length=100, unique=true)
    private String socialNetworkName;

    @Column(name="SocialNetworkDesc", nullable=true, length=256)
    private String socialNetworkDesc;

    @Column(name="WebSiteAddress", nullable=true, length=256)
    private String webSiteAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocialNetworkName() {
        return socialNetworkName;
    }

    public void setSocialNetworkName(String socialNetworkName) {
        this.socialNetworkName = socialNetworkName;
    }

    public String getSocialNetworkDesc() {
        return socialNetworkDesc;
    }

    public void setSocialNetworkDesc(String socialNetworkDesc) {
        this.socialNetworkDesc = socialNetworkDesc;
    }

    public String getWebSiteAddress() {
        return webSiteAddress;
    }

    public void setWebSiteAddress(String webSiteAddress) {
        this.webSiteAddress = webSiteAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SocialNetwork that = (SocialNetwork) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(socialNetworkName, that.socialNetworkName)
                .append(socialNetworkDesc, that.socialNetworkDesc)
                .append(webSiteAddress, that.webSiteAddress)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(socialNetworkName)
                .append(socialNetworkDesc)
                .append(webSiteAddress)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("socialNetworkName", socialNetworkName)
                .append("socialNetworkDesc", socialNetworkDesc)
                .append("webSiteAddress", webSiteAddress)
                .toString();
    }
}