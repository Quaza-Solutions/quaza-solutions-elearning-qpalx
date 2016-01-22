package com.quaza.solutions.qpalx.elearning.domain.institutions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

/**
 * 
 * 
 * @author manyce400
 */
@Entity
@Table(name="QPalXEducationalInstitution")
public class QPalXEducationalInstitution {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="Code", nullable=false, length=6)
	private String code;
	
	@Column(name="Name", nullable=false, length=100, unique=true)
	private String name;
	
	@Column(name="Description", nullable=true, length=256)
	private String description;
	
	@Embedded
	private InstitutionalContactMethod institutionalContactMethod;
	
	@Column(name="WebSiteAddress", nullable=true, length=256)
	private String webSiteAddress;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebSiteAddress() {
		return webSiteAddress;
	}

	public InstitutionalContactMethod getInstitutionalContactMethod() {
		return institutionalContactMethod;
	}

	public void setInstitutionalContactMethod(InstitutionalContactMethod institutionalContactMethod) {
		this.institutionalContactMethod = institutionalContactMethod;
	}

	public void setWebSiteAddress(String webSiteAddress) {
		this.webSiteAddress = webSiteAddress;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		QPalXEducationalInstitution that = (QPalXEducationalInstitution) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(code, that.code)
				.append(name, that.name)
				.append(description, that.description)
				.append(institutionalContactMethod, that.institutionalContactMethod)
				.append(webSiteAddress, that.webSiteAddress)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(code)
				.append(name)
				.append(description)
				.append(institutionalContactMethod)
				.append(webSiteAddress)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("code", code)
				.append("name", name)
				.append("description", description)
				.append("institutionalContactMethod", institutionalContactMethod)
				.append("webSiteAddress", webSiteAddress)
				.toString();
	}
}
