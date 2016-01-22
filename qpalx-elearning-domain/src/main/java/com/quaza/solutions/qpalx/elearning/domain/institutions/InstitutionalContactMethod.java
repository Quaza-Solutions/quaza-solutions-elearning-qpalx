package com.quaza.solutions.qpalx.elearning.domain.institutions;

import com.quaza.solutions.qpalx.elearning.domain.contact.ContactMethod;
import com.quaza.solutions.qpalx.elearning.domain.contact.PrefferedContactTypeE;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * ContactMethod specifically for an Institution or an Organization.
 * 
 * @author manyce400
 */
@Embeddable
public class InstitutionalContactMethod implements ContactMethod {
	

	@Column(name="PrimaryMobilePhone", nullable=true, length=50)
	private Long primaryMobilePhone;
	
	@Column(name="PrimaryOfficePhone", nullable=true, length=50)
	private Long primaryOfficePhone;
	
	@Column(name="PrimaryOfficeFax", nullable=true, length=50)
	private Long primaryOfficeFax;
	
	@Column(name="PrimaryOfficeEmail", nullable=true, length=100)
	private String primaryOfficeEmail;
	
	@Column(name="PrefferedContactType", nullable=true, length=10)
	@Enumerated(EnumType.STRING) 
	private PrefferedContactTypeE prefferedContactType;

	public Long getPrimaryMobilePhone() {
		return primaryMobilePhone;
	}

	public void setPrimaryMobilePhone(Long primaryMobilePhone) {
		this.primaryMobilePhone = primaryMobilePhone;
	}

	public Long getPrimaryPhone() {
		return primaryOfficePhone;
	}

	public void setPrimaryPhone(Long primaryOfficePhone) {
		this.primaryOfficePhone = primaryOfficePhone;
	}

	public Long getPrimaryFax() {
		return primaryOfficeFax;
	}

	public void setPrimaryFax(Long primaryOfficeFax) {
		this.primaryOfficeFax = primaryOfficeFax;
	}

	public String getPrimaryEmail() { 
		return primaryOfficeEmail;
	}
	
	public void setPrimaryEmail(String primaryOfficeEmail) {
		this.primaryOfficeEmail = primaryOfficeEmail;
	}

	public PrefferedContactTypeE getPrefferedContactType() {
		return prefferedContactType;
	}

	public void setPrefferedContactType(PrefferedContactTypeE prefferedContactType) {
		this.prefferedContactType = prefferedContactType;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("primaryMobilePhone", primaryMobilePhone)
			.append("primaryOfficePhone", primaryOfficePhone)
			.append("primaryOfficeFax", primaryOfficeFax)
			.append("primaryOfficeEmail", primaryOfficeEmail)
			.append("prefferedContactType", prefferedContactType)
			.toString();
	}

}
