package com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Response object received from MPower payment system as a result of initial payment charge
 * request.
 * 
 * @author manyce400 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPowerPaymentActionResponse {

	
	@JsonProperty(value="response_code")
	private String responseCode;
	
	@JsonProperty(value="response_text")
	private String responseText;
	
	@JsonProperty(value="description")
	private String description;
	
	// This is the actual token that needs to be sent to MPower as part of the final payment request.
	@JsonProperty(value="token")
	private String paymentToken;
	
	@JsonProperty(value="invoice_token")
	private String invoiceToken;

	public MPowerPaymentActionResponse() {
		super(); 
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentToken() {
		return paymentToken;
	}

	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}

	public String getInvoiceToken() {
		return invoiceToken;
	}

	public void setInvoiceToken(String invoiceToken) {
		this.invoiceToken = invoiceToken;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).
			       append("responseCode", responseCode).
			       append("responseText", responseText).
			       append("description", description).
			       append("paymentToken", paymentToken).
			       append("invoiceToken", invoiceToken). 
			       toString();
	}
	
	
}
