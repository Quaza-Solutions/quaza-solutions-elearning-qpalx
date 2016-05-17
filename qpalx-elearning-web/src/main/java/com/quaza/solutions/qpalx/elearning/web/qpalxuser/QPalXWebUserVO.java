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

    private String mobilePhoneNumber;

    //    @NotNull
//    @Size(min=2, max=10)
    private String password;

    private Long registeredByUserID;

    //    @NotNull
    private Long municipalityID;

    //    @NotNull
    private Long subscriptionID;

    //    @NotNull
    private Long tutorialGradeID;

    //@NotNull
    private Long educationalInstitutionID;

    //@NotNull
    private String mobileMoneySystem;

    //@NotNull
    private String mPowerAccountAlias;

    //@NotNull
    private String mPowerAuthorizationToken;

    private String studentPhotoFile;

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

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
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

    public Long getRegisteredByUserID() {
        return registeredByUserID;
    }

    public void setRegisteredByUserID(Long registeredByUserID) {
        this.registeredByUserID = registeredByUserID;
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
    public Long getTutorialGradeID() {
        return null;
    }

    public void setTutorialGradeID(Long tutorialGradeID) {
        this.tutorialGradeID = tutorialGradeID;
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

    public String getStudentPhotoFile() {
        return studentPhotoFile;
    }

    public void setStudentPhotoFile(String studentPhotoFile) {
        this.studentPhotoFile = studentPhotoFile;
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
                .append("tutorialGradeID", tutorialGradeID)
                .append("educationalInstitutionID", educationalInstitutionID)
                .append("mobileMoneySystem", mobileMoneySystem)
                .append("mPowerAccountAlias", mPowerAccountAlias)
                .append("mPowerAuthorizationToken", mPowerAuthorizationToken)
                .append("studentPhotoFile", studentPhotoFile)
                .toString();
    }


    public static final class Builder {

        private final QPalXWebUserVO qPalXWebUserVO = new QPalXWebUserVO();

        public Builder firstName(String firstName) {
            qPalXWebUserVO.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            qPalXWebUserVO.setLastName(lastName);
            return this;
        }

        public Builder email(String email) {
            qPalXWebUserVO.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            qPalXWebUserVO.setPassword(password);
            return this;
        }

        public Builder municipalityID(Long municipalityID) {
            qPalXWebUserVO.setMunicipalityID(municipalityID);
            return this;
        }

        public Builder subscriptionID(Long subscriptionID) {
            qPalXWebUserVO.setSubscriptionID(subscriptionID);
            return this;
        }

        public Builder tutorialGradeID(Long tutorialGradeID) {
            qPalXWebUserVO.setTutorialGradeID(tutorialGradeID);
            return this;
        }

        public Builder educationalInstitutionID(Long educationalInstitutionID) {
            qPalXWebUserVO.setEducationalInstitutionID(educationalInstitutionID);
            return this;
        }

        public Builder mobileMoneySystem(String mobileMoneySystem) {
            qPalXWebUserVO.setMobileMoneySystem(mobileMoneySystem);
            return this;
        }

        public Builder mPowerAccountAlias(String mPowerAccountAlias) {
            qPalXWebUserVO.setMPowerAccountAlias(mPowerAccountAlias);
            return this;
        }

        public Builder mPowerAuthorizationToken(String mPowerAuthorizationToken) {
            qPalXWebUserVO.setmPowerAuthorizationToken(mPowerAuthorizationToken);
            return this;
        }

        public Builder studentPhotoFile(String studentPhotoFile) {
            qPalXWebUserVO.setStudentPhotoFile(studentPhotoFile);
            return this;
        }

        public QPalXWebUserVO build() {
            return qPalXWebUserVO;
        }
    }


    public static final Builder builder() {
        return new Builder();
    }
}