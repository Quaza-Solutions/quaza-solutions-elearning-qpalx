package com.quaza.solutions.qpalx.elearning.web.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Web implementation of IQPalXUserVO.  This uses Java EE validation annotations to make sure all bindings to this
 * Value Object will be acceptable by the actual underlying QPalXUser business object.
 *
 * @author manyce400
 */
public class QPalXWebUserVO implements IQPalXUserVO {




    //    @NotNull
//    @Size(min=2, max=50)
    private String firstName;

    //    @NotNull
//    @Size(min=2, max=50)
    private String lastName;

    //    @NotNull
//    @Size(min=2, max=50)
//    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\\\.\"\n" +
//            "        +\"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@\"\n" +
//            "        +\"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email account.")
    private String email;

    //    @NotNull
//    @Size(min=2, max=10)
    private String password;

    //    @NotNull
    private Long municipalityID;

    //    @NotNull
    private Long subscriptionID;

    //    @NotNull
    private Long tutorialLevelID;

    //@NotNull
    private Long educationalInstitutionID;

    //@NotNull
    private String mobileMoneySystem;

    //@NotNull
    private String mPowerAccountAlias;

    //@NotNull
    private String mPowerAuthorizationToken;

    public QPalXWebUserVO() {

    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Long getMunicipalityID() {
        return municipalityID;
    }

    public void setMunicipalityID(Long municipalityID) {
        this.municipalityID = municipalityID;
    }

    @Override
    public Long getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(Long subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    @Override
    public Long getTutorialLevelID() {
        return tutorialLevelID;
    }

    public void setTutorialLevelID(Long tutorialLevelID) {
        this.tutorialLevelID = tutorialLevelID;
    }

    public Long getEducationalInstitutionID() {
        return educationalInstitutionID;
    }

    public void setEducationalInstitutionID(Long educationalInstitutionID) {
        this.educationalInstitutionID = educationalInstitutionID;
    }

    @Override
    public Long getPaymentSystemID() {
        return null;
    }

    public String getMobileMoneySystem() {
        return mobileMoneySystem;
    }

    public void setMobileMoneySystem(String mobileMoneySystem) {
        this.mobileMoneySystem = mobileMoneySystem;
    }

    public String getMPowerAccountAlias() {
        return mPowerAccountAlias;
    }

    public void setMPowerAccountAlias(String mPowerAccountAlias) {
        this.mPowerAccountAlias = mPowerAccountAlias;
    }

    public String getmPowerAuthorizationToken() {
        return mPowerAuthorizationToken;
    }

    public void setmPowerAuthorizationToken(String mPowerAuthorizationToken) {
        this.mPowerAuthorizationToken = mPowerAuthorizationToken;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("email", email)
                .append("password", password)
                .append("municipalityID", municipalityID)
                .append("subscriptionID", subscriptionID)
                .append("tutorialLevelID", tutorialLevelID)
                .append("educationalInstitutionID", educationalInstitutionID)
                .append("mobileMoneySystem", mobileMoneySystem)
                .append("mPowerAccountAlias", mPowerAccountAlias)
                .append("mPowerAuthorizationToken", mPowerAuthorizationToken)
                .toString();
    }
}