package com.quaza.solutions.qpalx.elearning.web.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionValidationResult;
import com.quaza.solutions.qpalx.elearning.service.geographical.IGeographicalDateTimeFormatter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Web only QPalXUser object which hides the underlying Domain data Object model for QPalXUser and exposes only relevant Web only
 * methods for clients.
 *
 * @author manyce400
 */
public class WebQPalXUser implements UserDetails {


    private final QPalXUser qPalXUser;

    private final SubscriptionValidationResult subscriptionValidationResult;

    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    public WebQPalXUser(final QPalXUser qPalXUser, SubscriptionValidationResult subscriptionValidationResult) {
        //super(qPalXUser.getEmail(), qPalXUser.getPassword(), null);
        this.qPalXUser = qPalXUser;
        this.subscriptionValidationResult = subscriptionValidationResult;
    }


    public String getUserFullName() {
        return new StringBuffer(qPalXUser.getFirstName())
                .append(" ")
                .append(qPalXUser.getLastName())
                .toString();
    }

    public String getEmail() {
        return qPalXUser.getEmail();
    }

    public String getSuccessID() {
        return qPalXUser.getSuccessID();
    }

    public String getTutorialLevel() {
        return qPalXUser.getqPalXTutorialLevel().getTutorialLevel();
    }

    public String getUserSubscriptionExpiryDate() {
        DateTime expiryDate = subscriptionValidationResult.getExpirationDate();
        QPalXMunicipality studentMunicipality = qPalXUser.getQPalXMunicipality();
        return iGeographicalDateTimeFormatter.getDisplayDateTimeWithTimeZone(expiryDate, studentMunicipality);
    }

    public QPalXUser getQPalXUser() {
        return qPalXUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(qPalXUser.getUserType().toString());
    }

    @Override
    public String getPassword() {
        return qPalXUser.getPassword();
    }

    @Override
    public String getUsername() {
        return qPalXUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !qPalXUser.isAccountLocked(); // Return inverse since isAccountLocked will return false if not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // By default return true since this functionality is not yet supported in QPalX
    }

    @Override
    public boolean isEnabled() {
        return !qPalXUser.isAccountLocked(); // Return inverse since isAccountLocked will return false if not locked
    }

    public SubscriptionValidationResult getSubscriptionValidationResult() {
        return subscriptionValidationResult;
    }

    public void setIGeographicalDateTimeFormatter(IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter) {
        this.iGeographicalDateTimeFormatter = iGeographicalDateTimeFormatter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("qPalXUser", qPalXUser)
                .append("subscriptionValidationResult", subscriptionValidationResult)
                .append("iGeographicalDateTimeFormatter", iGeographicalDateTimeFormatter)
                .toString();
    }
}
