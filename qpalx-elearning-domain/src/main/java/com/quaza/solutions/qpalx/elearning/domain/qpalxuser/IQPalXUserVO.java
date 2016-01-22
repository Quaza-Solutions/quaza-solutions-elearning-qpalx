package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

/**
 * Interface that defines all basic methods implemented by a QPalXUser Value Object.
 *
 * This is a lite representation of the QPalXUser Domain and Business Object that is great for transfering data
 * between front ends and the back end
 *
 * @author manyce400
 */
public interface IQPalXUserVO {


    public String getFirstName();

    public String getLastName();

    public String getEmail();

    public String getPassword();

    /**
     * Returns User's Municipality ID.
     *
     * @return Long
     */
    public Long getMunicipalityID();

    /**
     * Returns User's subscription ID.
     *
     * @return
     */
    public Long getSubscriptionID();

    public Long getEducationalInstitutionID();

    /**
     * @return The QPalXUser tutorial level.
     */
    public Long getTutorialLevelID();

    public String getMPowerAccountAlias();

    /**
     * Returns the ID for the payment system that can be used to bill this user.
     *
     * @return Long.
     */
    public Long getPaymentSystemID();
}
