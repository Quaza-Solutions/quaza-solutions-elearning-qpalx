package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name="UserEducationalInstitutions")
public class UserEducationalInstitutions {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QPalxUserID", nullable = false)
	private QPalXUser qpalxUser;
	

	// Fetch this Eager as this points to actual SocialNetwork attributes
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EducationalInstitutionID", nullable = false)
	private QPalXEducationalInstitution qPalXEducationalInstitution;

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

	public QPalXEducationalInstitution getqPalXEducationalInstitution() {
		return qPalXEducationalInstitution;
	}

	public void setqPalXEducationalInstitution(QPalXEducationalInstitution qPalXEducationalInstitution) {
		this.qPalXEducationalInstitution = qPalXEducationalInstitution;
	}

	public DateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(DateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		UserEducationalInstitutions that = (UserEducationalInstitutions) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(qpalxUser, that.qpalxUser)
				.append(qPalXEducationalInstitution, that.qPalXEducationalInstitution)
				.append(effectiveDate, that.effectiveDate)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(qpalxUser)
				.append(qPalXEducationalInstitution)
				.append(effectiveDate)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("qpalxUser", qpalxUser)
				.append("qPalXEducationalInstitution", qPalXEducationalInstitution)
				.append("effectiveDate", effectiveDate)
				.toString();
	}
}
