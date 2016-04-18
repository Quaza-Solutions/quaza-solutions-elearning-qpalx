package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * POJO specifying properties for a QPalXUser.
 *
 * @author manyce400
 */
@Entity
@Table(name="QPalXUser")
public class QPalXUser {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;

    // SuccessID created only for Student QPalX Users
    @Column(name="SuccessID", nullable=true, length=255, unique=true)
    private String successID;

    @Column(name="FirstName", nullable=false, length = 50)
    private String firstName;

    @Column(name="LastName", nullable=false, length = 50)
    private String lastName;

    @Column(name="UserType", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private QPalxUserTypeE userType;

    @Column(name="UserSex", nullable=false, length=10)
    @Enumerated(EnumType.STRING)
    private QPalxUserSexE userSex;

    @Column(name="Email", nullable=true, unique = true, length = 50)
    private String email;

    @Column(name="MobilePhoneNumber", nullable=true, unique = true, length = 50)
    private String mobilePhoneNumber;

    // Nullable.  Existing QPalX User that was responsible for registering this user.  Null if user registered themself
    @Column(name="RegisteredByUserID", nullable=true)
    private Long registeredByUserID;

    // Password setup is always required to login
    @Column(name="Password", nullable=false, length = 10)
    private String password;

    @Column(name="ResetPassword", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean resetPassword;

    // IF set to true then user is not allowed to access QPalX application
    @Column(name="AccountLocked", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean accountLocked;

    // Specifies the QPalXMunicipality that user is resident/associated with. Fetched eager
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXMunicipalityID", nullable = false)
    private QPalXMunicipality qPalXMunicipality;

    @Column(name="LastLoginDate", nullable=true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastLoginDate;

    @Column(name="PhotoFileLocation", nullable=true, unique = true, length = 800)
    private String photoFileLocation;

    // For Student users, contains list of all TutorialGrade levels that user has gone through
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qpalxUser")
    private Set<QPalXStudentTutorialGrade> studentTutorialGradeLevels = new HashSet<>();

    // Social Network information that Employee belongs to
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qpalxUser")
    private Set<UserSocialNetwork> socialNetworks = new HashSet<UserSocialNetwork>();

    // Social Network information that Employee belongs to
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qpalxUser")
    private Set<UserEducationalInstitutions> educationalInstitutions = new HashSet<UserEducationalInstitutions>();


    // User subscription profiles
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qpalxUser")
    private Set<UserSubscriptionProfile> userSubscriptionProfiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuccessID() {
        return successID;
    }

    public void setSuccessID(String successID) {
        this.successID = successID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        String fullName = firstName + " " + lastName;
        return fullName;
    }

    public QPalxUserTypeE getUserType() {
        return userType;
    }

    public void setUserType(QPalxUserTypeE userType) {
        this.userType = userType;
    }

    public QPalxUserSexE getUserSex() {
        return userSex;
    }

    public void setUserSex(QPalxUserSexE userSex) {
        this.userSex = userSex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Long getRegisteredByUserID() {
        return registeredByUserID;
    }

    public void setRegisteredByUserID(Long registeredByUserID) {
        this.registeredByUserID = registeredByUserID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public QPalXMunicipality getQPalXMunicipality() {
        return qPalXMunicipality;
    }

    public void setQPalXMunicipality(QPalXMunicipality qPalXMunicipality) {
        this.qPalXMunicipality = qPalXMunicipality;
    }

    public DateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(DateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getPhotoFileLocation() {
        return photoFileLocation;
    }

    public void setPhotoFileLocation(String photoFileLocation) {
        this.photoFileLocation = photoFileLocation;
    }

    public Set<QPalXStudentTutorialGrade> getStudentTutorialGradeLevels() {
        return ImmutableSet.copyOf(studentTutorialGradeLevels);
    }

    public void addStudentTutorialGradeLevel(QPalXStudentTutorialGrade qPalXStudentTutorialGrade) {
        Assert.notNull(qPalXStudentTutorialGrade, "qPalXStudentTutorialGrade cannot be null");
        studentTutorialGradeLevels.add(qPalXStudentTutorialGrade);
    }

    public Set<UserSocialNetwork> getSocialNetworks() {
        return ImmutableSet.copyOf(socialNetworks);
    }

    public void addSocialNetwork(UserSocialNetwork userSocialNetwork) {
        this.socialNetworks.add(userSocialNetwork);
    }

    public Set<UserEducationalInstitutions> getEducationalInstitutions() {
        return ImmutableSet.copyOf(educationalInstitutions);
    }

    public void addEducationalInstitution(UserEducationalInstitutions userEducationalInstitution) {
        this.educationalInstitutions.add(userEducationalInstitution);
    }

    public Set<UserSubscriptionProfile> getUserSubscriptionProfiles() {
        return ImmutableSet.copyOf(userSubscriptionProfiles);
    }

    public void addUserSubscriptionProfile(UserSubscriptionProfile userSubscriptionProfile) {
        userSubscriptionProfiles.add(userSubscriptionProfile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        QPalXUser qPalXUser = (QPalXUser) o;

        return new EqualsBuilder()
                .append(resetPassword, qPalXUser.resetPassword)
                .append(accountLocked, qPalXUser.accountLocked)
                .append(id, qPalXUser.id)
                .append(successID, qPalXUser.successID)
                .append(firstName, qPalXUser.firstName)
                .append(lastName, qPalXUser.lastName)
                .append(userType, qPalXUser.userType)
                .append(userSex, qPalXUser.userSex)
                .append(email, qPalXUser.email)
                .append(mobilePhoneNumber, qPalXUser.mobilePhoneNumber)
                .append(registeredByUserID, qPalXUser.registeredByUserID)
                .append(password, qPalXUser.password)
                .append(qPalXMunicipality, qPalXUser.qPalXMunicipality)
                .append(lastLoginDate, qPalXUser.lastLoginDate)
                .append(photoFileLocation, qPalXUser.photoFileLocation)
                .append(socialNetworks, qPalXUser.socialNetworks)
                .append(educationalInstitutions, qPalXUser.educationalInstitutions)
                .append(userSubscriptionProfiles, qPalXUser.userSubscriptionProfiles)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(successID)
                .append(firstName)
                .append(lastName)
                .append(userType)
                .append(userSex)
                .append(email)
                .append(mobilePhoneNumber)
                .append(registeredByUserID)
                .append(password)
                .append(resetPassword)
                .append(accountLocked)
                .append(qPalXMunicipality)
                .append(lastLoginDate)
                .append(photoFileLocation)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("photoFileLocation", photoFileLocation)
                .append("id", id)
                .append("successID", successID)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("userType", userType)
                .append("userSex", userSex)
                .append("email", email)
                .append("mobilePhoneNumber", mobilePhoneNumber)
                .append("registeredByUserID", registeredByUserID)
                .append("password", password)
                .append("resetPassword", resetPassword)
                .append("accountLocked", accountLocked)
                .append("qPalXMunicipality", qPalXMunicipality)
                .append("lastLoginDate", lastLoginDate)
                .toString();
    }

    public static final Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final QPalXUser qPalXUser = new QPalXUser();

        public Builder iD(Long id) {
            qPalXUser.setId(id);
            return this;
        }

        public Builder sccessID(String successID) {
            qPalXUser.setSuccessID(successID);
            return this;
        }

        public Builder firstName(String firstName) {
            qPalXUser.setFirstName(firstName);
            return this;
        }

        public Builder lastName(String lastName) {
            qPalXUser.setLastName(lastName);
            return this;
        }

        public Builder email(String email) {
            qPalXUser.setEmail(email);
            return this;
        }

        public Builder password(String password) {
            qPalXUser.setPassword(password);
            return this;
        }

        public Builder qPalxUserTypeE(QPalxUserTypeE qPalxUserTypeE) {
            qPalXUser.setUserType(qPalxUserTypeE);
            return this;
        }

        public Builder qPalxUserSexE(QPalxUserSexE qPalxUserSexE) {
            qPalXUser.setUserSex(qPalxUserSexE);
            return this;
        }

        public Builder accountLockedStatus(boolean accountLocked) {
            qPalXUser.setAccountLocked(accountLocked);
            return this;
        }

        public Builder resetPasswordStatus(boolean resetPassword) {
            qPalXUser.setResetPassword(resetPassword);
            return this;
        }

        public Builder municipality(QPalXMunicipality municipality) {
            qPalXUser.setQPalXMunicipality(municipality);
            return this;
        }

        public Builder lastLoginDate(DateTime lastLoginDate) {
            qPalXUser.setLastLoginDate(lastLoginDate);
            return this;
        }

        public Builder mobilePhoneNumber(String mobilePhoneNumber) {
            qPalXUser.mobilePhoneNumber = mobilePhoneNumber;
            return this;
        }

        public Builder registeredByUserID(Long registeredByUserID) {
            qPalXUser.registeredByUserID = registeredByUserID;
            return this;
        }

        public Builder photoFileLocation(String photoFileLocation) {
            qPalXUser.photoFileLocation = photoFileLocation;
            return this;
        }

        public Builder qPalXStudentTutorialGrade(QPalXStudentTutorialGrade qPalXStudentTutorialGrade) {
            qPalXUser.addStudentTutorialGradeLevel(qPalXStudentTutorialGrade);
            return this;
        }

        public Builder userSocialNetwork(UserSocialNetwork userSocialNetwork) {
            qPalXUser.addSocialNetwork(userSocialNetwork);
            return this;
        }

        public Builder userSubscriptionProfile(UserSubscriptionProfile userSubscriptionProfile) {
            qPalXUser.addUserSubscriptionProfile(userSubscriptionProfile);
            return  this;
        }

        public QPalXUser build() {
            return qPalXUser;
        }
    }
}