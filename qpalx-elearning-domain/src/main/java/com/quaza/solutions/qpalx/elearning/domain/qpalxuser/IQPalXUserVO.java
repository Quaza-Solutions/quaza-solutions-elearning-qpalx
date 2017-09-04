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

    public String getMobilePhoneNumber();

    public String getPassword();

    public String getUserSex();

    public Long getMunicipalityID();

    public Long getSubscriptionID();

    public Long getStudentTutorialLevelID();

    public Long getEducationalInstitutionID();

    public Long getTutorialGradeID();

    public String getMPowerAccountAlias();

    public Long getPaymentSystemID();

    public QPalXUser getGlobalAuditQPalxUser();

}
