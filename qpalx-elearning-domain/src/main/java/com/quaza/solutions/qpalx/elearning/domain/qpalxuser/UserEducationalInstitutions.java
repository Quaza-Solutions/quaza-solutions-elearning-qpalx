package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name="UserEducationalInstitutions")
public class UserEducationalInstitutions {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QPalxUserID", nullable = false)
	private QPalXUser qpalxUser;
	

	// Fetch this Eager as this points to actual SocialNetwork attributes
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EducationalInstitutionID", nullable = false)
	private QPalXEducationalInstitution socialNetworkId;

	// Date Social Network was effective from
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name="EffectiveDate", nullable=true)
	private DateTime effectiveDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public QPalXUser getQpalxUser() {
		return qpalxUser;
	}

	public void setQpalxUser(QPalXUser qpalxUser) {
		this.qpalxUser = qpalxUser;
	}

	public QPalXEducationalInstitution getSocialNetworkId() {
		return socialNetworkId;
	}

	public void setSocialNetworkId(QPalXEducationalInstitution socialNetworkId) {
		this.socialNetworkId = socialNetworkId;
	}

	public DateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(DateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	
}
