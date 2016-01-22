package com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author manyce400
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPowerOnSitePaymentCharge {

	
	@JsonProperty(value="token")
	private String qpalXPaymentToken;
	
	@JsonProperty(value="confirm_token")
	private String userPaymentConfirmToken;

	public MPowerOnSitePaymentCharge() {
		
	}
	
	public MPowerOnSitePaymentCharge(String qpalXPaymentToken, String userPaymentConfirmToken) {
		this.qpalXPaymentToken = qpalXPaymentToken;
		this.userPaymentConfirmToken = userPaymentConfirmToken;
	}


	public String getQpalXPaymentToken() {
		return qpalXPaymentToken;
	}

	public void setQpalXPaymentToken(String qpalXPaymentToken) {
		this.qpalXPaymentToken = qpalXPaymentToken;
	}

	public String getUserPaymentConfirmToken() {
		return userPaymentConfirmToken;
	}

	public void setUserPaymentConfirmToken(String userPaymentConfirmToken) {
		this.userPaymentConfirmToken = userPaymentConfirmToken;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("qpalXPaymentToken", qpalXPaymentToken)
				.append("userPaymentConfirmToken", userPaymentConfirmToken)
				.toString();
	}
}
