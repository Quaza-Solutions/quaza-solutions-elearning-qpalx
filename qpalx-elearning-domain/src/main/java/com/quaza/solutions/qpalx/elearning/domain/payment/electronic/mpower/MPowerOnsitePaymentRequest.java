package com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Testing JSON properties of MPowerInvoiceData.
 * 
 * @JsonInclude will exlcude during generation to and from JSON and field values that might be null
 * 
 * @author manyce400
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPowerOnsitePaymentRequest {

	
	@JsonProperty(value="invoice_data")
	private MPowerInvoiceData invoiceData;
	
	@JsonProperty(value="opr_data")
	private MPowerCustomerData customerData;
	
	

	public MPowerOnsitePaymentRequest() {
		
	}

	public MPowerOnsitePaymentRequest(MPowerInvoiceData invoiceData, MPowerCustomerData customerData) {
		this.invoiceData = invoiceData;
		this.customerData = customerData;
	}

	public MPowerInvoiceData getInvoiceData() {
		return invoiceData;
	}

	public void setInvoiceData(MPowerInvoiceData invoiceData) {
		this.invoiceData = invoiceData;
	}

	public MPowerCustomerData getCustomerData() {
		return customerData;
	}

	public void setCustomerData(MPowerCustomerData customerData) {
		this.customerData = customerData;
	}
	
	

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MPowerOnsitePaymentRequest)) {
			return false;
		}
		
		MPowerOnsitePaymentRequest other = (MPowerOnsitePaymentRequest) obj;
		return new EqualsBuilder() 
                .append(invoiceData, other.invoiceData)
                .append(customerData, other.customerData) 
                .isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
				.append("invoiceData", invoiceData)
				.append("customerData", customerData)
				.toString();
	}
	
	
	
}
