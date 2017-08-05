package com.quaza.solutions.qpalx.elearning.domain.institutions;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@Column(name="Code", nullable=false, length=6)
	private String code;
	
	@Column(name="Name", nullable=false, length=255, unique=true)
	private String name;
	
	@Column(name="Description", nullable=true, length=255)
	private String description;
	
	@Embedded
	private InstitutionalContactMethod institutionalContactMethod;
	
	@Column(name="WebSiteAddress", nullable=true, length=256)
	private String webSiteAddress;

	// Full path and file name of the media content
	@Column(name="SchoolLogo", nullable=true, length=255)
	private String schoolLogo;

	// Determines IF this institution offers Primary/Middle School Level Education
	@Column(name="HasPrimaryEducation", nullable = false, columnDefinition = "TINYINT", length = 1)
	@ColumnDefault("0")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean hasPrimaryEducation;

	// Determines IF this institution offers Junior School Level Education
	@Column(name="HasJuniorHighEducation", nullable = false, columnDefinition = "TINYINT", length = 1)
	@ColumnDefault("0")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean hasJuniorHighEducation;

	// Determines IF this institution offers Senior High School Level Education
	@Column(name="HasSeniorHighEducation", nullable = false, columnDefinition = "TINYINT", length = 1)
	@ColumnDefault("0")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean hasSeniorHighEducation;

	// Determines IF this institution offers Senior High School Level Education
	@Column(name="HasCollegeEducation", nullable = false, columnDefinition = "TINYINT", length = 1)
	@ColumnDefault("0")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean hasCollegeEducation;

	// Fetch this eagerly.  This is the Academic Level that this School teaches at
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "QPalXMunicipalityID", nullable =  true)
	private QPalXMunicipality qPalXMunicipality;

	public static final String CLASS_ATTRIBUTE_IDENTIFIER = "QPalXEducationalInstitution";

	
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

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}

	public boolean isHasPrimaryEducation() {
		return hasPrimaryEducation;
	}

	public void setHasPrimaryEducation(boolean hasPrimaryEducation) {
		this.hasPrimaryEducation = hasPrimaryEducation;
	}

	public boolean isHasJuniorHighEducation() {
		return hasJuniorHighEducation;
	}

	public void setHasJuniorHighEducation(boolean hasJuniorHighEducation) {
		this.hasJuniorHighEducation = hasJuniorHighEducation;
	}

	public boolean isHasSeniorHighEducation() {
		return hasSeniorHighEducation;
	}

	public void setHasSeniorHighEducation(boolean hasSeniorHighEducation) {
		this.hasSeniorHighEducation = hasSeniorHighEducation;
	}

	public QPalXMunicipality getQPalXMunicipality() {
		return qPalXMunicipality;
	}

	public void setQPalXMunicipality(QPalXMunicipality qPalXMunicipality) {
		this.qPalXMunicipality = qPalXMunicipality;
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
				.append(hasPrimaryEducation, that.hasPrimaryEducation)
				.append(hasJuniorHighEducation, that.hasJuniorHighEducation)
				.append(hasSeniorHighEducation, that.hasSeniorHighEducation)
				.append(id, that.id)
				.append(code, that.code)
				.append(name, that.name)
				.append(description, that.description)
				.append(institutionalContactMethod, that.institutionalContactMethod)
				.append(webSiteAddress, that.webSiteAddress)
				.append(schoolLogo, that.schoolLogo)
				.append(qPalXMunicipality, that.qPalXMunicipality)
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
				.append(schoolLogo)
				.append(hasPrimaryEducation)
				.append(hasJuniorHighEducation)
				.append(hasSeniorHighEducation)
				.append(qPalXMunicipality)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("code", code)
				.append("name", name)
				.append("description", description)
				.append("institutionalContactMethod", institutionalContactMethod)
				.append("webSiteAddress", webSiteAddress)
				.append("schoolLogo", schoolLogo)
				.append("hasPrimaryEducation", hasPrimaryEducation)
				.append("hasJuniorHighEducation", hasJuniorHighEducation)
				.append("hasSeniorHighEducation", hasSeniorHighEducation)
				.append("qPalXMunicipality", qPalXMunicipality)
				.toString();
	}

	public static final Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private final QPalXEducationalInstitution educationalInstitution = new QPalXEducationalInstitution();

		public Builder name(String name) {
			educationalInstitution.name = name;
			return this;
		}

		public Builder code(String code) {
			educationalInstitution.code = code;
			return this;
		}

		public Builder description(String description) {
			educationalInstitution.description = description;
			return this;
		}

		public Builder webSiteAddress(String webSiteAddress) {
			educationalInstitution.webSiteAddress = webSiteAddress;
			return this;
		}

		public QPalXEducationalInstitution build() {
			return educationalInstitution;
		}
	}
}
