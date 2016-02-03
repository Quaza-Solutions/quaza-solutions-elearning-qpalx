package com.quaza.solutions.qpalx.elearning.domain.subscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name="QPalXSubscription")
public class QPalXSubscription {



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID", nullable=false)
	private Long id;

	@Column(name="SubscriptionName", nullable=false)
	private String subscriptionName;

	@Column(name="SubscriptionCost", nullable=false)
	private Double subscriptionCost;

	@Column(name="SubscriptionType", nullable=false, length=20)
	@Enumerated(EnumType.STRING)
	private SubscriptionTypeE subscriptionType;

	// Country for which this subscription has been setup
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "SubscriptionQPalXCountryID", nullable = false)
	private QPalXCountry subscriptionQPalXCountry;

	public QPalXSubscription() {
	}

	public QPalXSubscription(Double subscriptionCost, SubscriptionTypeE subscriptionType) {
		this.subscriptionCost = subscriptionCost;
		this.subscriptionType = subscriptionType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public Double getSubscriptionCost() {
		return subscriptionCost;
	}

	public void setSubscriptionCost(Double subscriptionCost) {
		this.subscriptionCost = subscriptionCost;
	}

	public SubscriptionTypeE getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(SubscriptionTypeE subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public QPalXCountry getSubscriptionQPalXCountry() {
		return subscriptionQPalXCountry;
	}

	public void setSubscriptionQPalXCountry(QPalXCountry subscriptionQPalXCountry) {
		this.subscriptionQPalXCountry = subscriptionQPalXCountry;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		QPalXSubscription that = (QPalXSubscription) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(subscriptionName, that.subscriptionName)
				.append(subscriptionCost, that.subscriptionCost)
				.append(subscriptionType, that.subscriptionType)
				.append(subscriptionQPalXCountry, that.subscriptionQPalXCountry)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(subscriptionName)
				.append(subscriptionCost)
				.append(subscriptionType)
				.append(subscriptionQPalXCountry)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("subscriptionName", subscriptionName)
				.append("subscriptionCost", subscriptionCost)
				.append("subscriptionType", subscriptionType)
				.append("subscriptionQPalXCountry", subscriptionQPalXCountry)
				.toString();
	}

	public static final class Builder {

		private final QPalXSubscription qPalXSubscription = new QPalXSubscription();

		public Builder subscriptionCost(Double subscriptionCost) {
			qPalXSubscription.setSubscriptionCost(subscriptionCost);
			return this;
		}

		public Builder subscriptionQPalXCountry(QPalXCountry qPalXCountry) {
			qPalXSubscription.setSubscriptionQPalXCountry(qPalXCountry);
			return this;
		}

		public Builder subscriptionType(SubscriptionTypeE subscriptionTypeE) {
			qPalXSubscription.setSubscriptionType(subscriptionTypeE);
			return this;
		}

        public Builder subscriptionName(String subscriptionName) {
            qPalXSubscription.subscriptionName = subscriptionName;
            return this;
        }

		public QPalXSubscription build() {
			return qPalXSubscription;
		}
	}

	public static final Builder builder() {
		return new Builder();
	}
}
