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
 * POJO with properties representing the MPower Customer that charge will be posted against.
 * 
 * @JsonInclude will exlcude during generation to and from JSON and field values that might be null
 * 
 * @author manyce400 
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPowerCustomerData {
	
	
	
	@JsonProperty(value="name")
	private String name;

	// this is the unique MPower User ID
	@JsonProperty(value="account_alias")
	private String userName;
	
	@JsonProperty(value="phone")
	private String phoneNumber;
	
	@JsonProperty(value="email")
	private String email;
	
	
	public MPowerCustomerData() {
		
	}

	public MPowerCustomerData(String userName, String phoneNumber) { 
		this.userName = userName;
		this.phoneNumber = phoneNumber;
	}
	
	public MPowerCustomerData(String name, String phoneNumber, String email) { 
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}
	
	public MPowerCustomerData(String name, String userName, String phoneNumber, String email) {
		this.name = name;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		 return new HashCodeBuilder()
				 .append(name)
				 .append(userName)
				 .append(phoneNumber)
				 .append(email)
				 .toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MPowerCustomerData)) {
			return false;
		}
		
		MPowerCustomerData other = (MPowerCustomerData) obj;
		return new EqualsBuilder() 
				.append(name, other.name)
                .append(userName, other.userName)
                .append(phoneNumber, other.phoneNumber) 
                .append(email, other.email) 
                .isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("name", name)
				.append("userName", userName)
				.append("phoneNumber", phoneNumber)
				.append("email", email)
				.toString();
	}
	
}
