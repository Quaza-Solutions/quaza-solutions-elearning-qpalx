package com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * POJO specifying all properties of an MPower Payment Invoice
 * 
 * @JsonInclude will exlcude during generation to and from JSON and field values that might be null
 * 
 * @author manyce400
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPowerInvoice {
	
	
	@JsonProperty(value="total_amount")
	private Double totalAmount;
	
	@JsonProperty(value="description")
	private String invoiceDescription;
	

	public MPowerInvoice() {  
		
	}

	public MPowerInvoice(Double totalAmount, String invoiceDescription) { 
		this.totalAmount = totalAmount;
		this.invoiceDescription = invoiceDescription;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInvoiceDescription() {
		return invoiceDescription;
	}

	public void setInvoiceDescription(String invoiceDescription) {
		this.invoiceDescription = invoiceDescription;
	}

	@Override
	public int hashCode() {
		 return new HashCodeBuilder()
				 .append(totalAmount)
				 .append(invoiceDescription)
				 .toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MPowerInvoice)) {
			return false;
		}
		
		MPowerInvoice other = (MPowerInvoice) obj;
		return new EqualsBuilder() 
                .append(totalAmount, other.totalAmount)
                .append(invoiceDescription, other.invoiceDescription) 
                .isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("totalAmount", totalAmount)
				.append("invoiceDescription", invoiceDescription)
				.toString();
	}
	
	
	

}
