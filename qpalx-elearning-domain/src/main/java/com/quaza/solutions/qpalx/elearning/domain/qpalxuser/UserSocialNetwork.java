package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.socialnetwork.SocialNetwork;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * POJO specifying all the Social Networks that a QPalX User has registered on their profile
 * 
 * @author manyce400 
 */
@Entity
@Table(name="UserSocialNetworks")
public class UserSocialNetwork {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QPalxUserID", nullable = false)
	private QPalXUser qpalxUser;
	
	// Employee's unique identifier on the SocialNetwork
	@Column(name="SocialNetworkMemberID", nullable=false, length=56)
	private String socialNetworkMemberID;
	
	// Fetch this Eager as this points to actual SocialNetwork attributes
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SocialNetworkID", nullable = false)
	private SocialNetwork socialNetwork;

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

	public String getSocialNetworkMemberID() {
		return socialNetworkMemberID;
	}

	public void setSocialNetworkMemberID(String socialNetworkMemberID) {
		this.socialNetworkMemberID = socialNetworkMemberID;
	}

	public SocialNetwork getSocialNetwork() {
		return socialNetwork;
	}

	public void setSocialNetwork(SocialNetwork socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	public DateTime getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(DateTime effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	


}
