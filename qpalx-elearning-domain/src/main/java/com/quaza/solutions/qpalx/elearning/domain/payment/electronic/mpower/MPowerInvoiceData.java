package com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.PaymentModeE;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.PaymentStatusE;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * POJO that contains the entire subset of an MPower Payment Invoice.
 * 
 * @JsonInclude will exlcude during generation to and from JSON and field values that might be null
 * 
 * @author manyce400
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPowerInvoiceData {

	
	@JsonProperty(value="invoice")
	private MPowerInvoice invoice;
	
	@JsonProperty(value="store")
	private MPowerIntegrationStore store;
	
	@JsonProperty(value="customer")
	private MPowerCustomerData customerData;
	
	@JsonProperty(value="mode")
	private PaymentModeE paymentMode;
	
	@JsonProperty(value="status")
	private PaymentStatusE paymentStatus;

	public MPowerInvoiceData() { 
		
	}

	public MPowerInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(MPowerInvoice invoice) {
		this.invoice = invoice;
	}

	public MPowerIntegrationStore getStore() {
		return store;
	}

	public void setStore(MPowerIntegrationStore store) {
		this.store = store;
	}

	public MPowerCustomerData getCustomerData() {
		return customerData;
	}

	public void setCustomerData(MPowerCustomerData customerData) {
		this.customerData = customerData;
	}

	public PaymentModeE getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentModeE paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentStatusE getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatusE paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public int hashCode() {
		 return new HashCodeBuilder()
				 .append(invoice)
				 .append(store)
				 .append(customerData)
				 .append(paymentMode)
				 .append(paymentStatus)
				 .toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MPowerInvoiceData)) {
			return false;
		}
		
		MPowerInvoiceData other = (MPowerInvoiceData) obj;
		return new EqualsBuilder() 
                .append(invoice, other.invoice)
                .append(store, other.store) 
                .append(customerData, other.customerData)
                .append(paymentMode, other.paymentMode)
                .append(paymentStatus, other.paymentStatus)
                .isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("invoice", invoice)
				.append("store", store)
				.append("customerData", customerData)
				.append("paymentMode", paymentMode)
				.append("paymentStatus", paymentStatus)
				.toString();
	}
	
}
