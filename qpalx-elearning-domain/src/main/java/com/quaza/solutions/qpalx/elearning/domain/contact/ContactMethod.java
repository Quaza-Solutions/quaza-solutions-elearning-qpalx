package com.quaza.solutions.qpalx.elearning.domain.contact;

/**
 * Interface that defines a contact method.  Contact method
 * contains information for the basic types of contact.
 * 
 * @author manyce400 
 */
public interface ContactMethod {

	/**
	 * Returns a valid mobile phone number associated with this contact.
	 * 
	 * @return <code>null</code> if implementation does not support this.
	 */
	public Long getPrimaryMobilePhone();
	
	/**
	 * Primary land line phone number associated with this ContactMethod.
	 * 
	 * @return <code>null</code> if implementation does not support this.
	 */
	public Long getPrimaryPhone();
	
	/**
	 * Primary fax number associated with this ContactMethod.
	 * 
	 * @return <code>null</code> if implementation does not support this.
	 */
	public Long getPrimaryFax();
	
	/**
	 * Primary email associated with this ContactMethod
	 * 
	 * @return <code>null</code> if implementation does not support this.
	 */
	public String getPrimaryEmail();
	
	/**
	 * Return the PrefferedContactTypeE type supported by this implementation.
	 * 
	 * @return PrefferedContactTypeE
	 */
	public PrefferedContactTypeE getPrefferedContactType();
}

