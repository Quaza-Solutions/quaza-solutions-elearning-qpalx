package com.quaza.solutions.qpalx.elearning.domain.qpalxuser;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * POJO specifying properties for a QPALX User.
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

    @Column(name="SuccessID", nullable=false, length=255, unique=true)
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

    // Specifies the QPalX TutorialLevel this is a critical component of QPalX system and must be fetched eager
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QPalXTutorialLevelID", nullable = false)
    private QPalXTutorialLevel qPalXTutorialLevel;

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

    public QPalXTutorialLevel getqPalXTutorialLevel() {
        return qPalXTutorialLevel;
    }

    public void setqPalXTutorialLevel(QPalXTutorialLevel qPalXTutorialLevel) {
        this.qPalXTutorialLevel = qPalXTutorialLevel;
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
                .append(id, qPalXUser.id)
                .append(successID, qPalXUser.successID)
                .append(firstName, qPalXUser.firstName)
                .append(lastName, qPalXUser.lastName)
                .append(userType, qPalXUser.userType)
                .append(userSex, qPalXUser.userSex)
                .append(email, qPalXUser.email)
                .append(qPalXTutorialLevel, qPalXUser.qPalXTutorialLevel)
                .append(password, qPalXUser.password)
                .append(qPalXMunicipality, qPalXMunicipality)
                .append(lastLoginDate, qPalXUser.lastLoginDate)
                .append(accountLocked, qPalXUser.accountLocked)
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
                .append(qPalXTutorialLevel)
                .append(password)
                .append(qPalXMunicipality)
                .append(lastLoginDate)
                .append(accountLocked)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("ID", id)
                .append("successID", successID)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("userType", userType)
                .append("userSex", userSex)
                .append("email", email)
                .append("qPalXTutorialLevel", qPalXTutorialLevel)
                .append("qPalXMunicipality", qPalXMunicipality)
                .append("resetPassword", resetPassword)
                .append("accountLocked", accountLocked)
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

        public Builder qPalXTutorialLevel(QPalXTutorialLevel qPalXTutorialLevel) {
            qPalXUser.setqPalXTutorialLevel(qPalXTutorialLevel);
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