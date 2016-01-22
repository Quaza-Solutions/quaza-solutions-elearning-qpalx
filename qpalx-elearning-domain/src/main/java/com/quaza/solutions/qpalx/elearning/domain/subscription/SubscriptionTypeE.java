package com.quaza.solutions.qpalx.elearning.domain.subscription;

/**
 * Enumeration that specifies all the different Subscription types available in QPalX system.
 * 
 * @author manyce400 
 */
public enum SubscriptionTypeE {

	FIVE_DAYS(5),

	WEEKLY(7),

	TEN_DAYS(10),

	BI_WEEKLY(14),

	MONTHLY(31),
	
	QUARTERLY(120),
	
	YEARLY(365);


	private final int numberOfDays;

	SubscriptionTypeE(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}
}
