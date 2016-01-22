package com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Defines required MPower integration client Store.  In the case of this application will always default to QPalX
 * 
 * @author manyce400 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MPowerIntegrationStore {

	@JsonProperty(value="name")
	private String name;
	
	public static final MPowerIntegrationStore QPALX = new MPowerIntegrationStore("QPalX");
	

	public MPowerIntegrationStore() { 
		
	}

	private MPowerIntegrationStore(String name) { 
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static MPowerIntegrationStore getQpalx() {
		return QPALX;
	}

	@Override
	public int hashCode() {
		 return new HashCodeBuilder()
				 .append(name)
				 .toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MPowerIntegrationStore)) {
			return false;
		}
		
		MPowerIntegrationStore other = (MPowerIntegrationStore) obj;
		return new EqualsBuilder() 
                .append(name, other.name) 
                .isEquals();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("name", name) 
				.toString();
	}
	
	
}
