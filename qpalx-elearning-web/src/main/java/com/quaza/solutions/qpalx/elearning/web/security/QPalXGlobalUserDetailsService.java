package com.quaza.solutions.qpalx.elearning.web.security;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionValidationResult;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.WebQPalXUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service bean that works in conjunction with spring-boot-starter-security to enforce application wide security and
 * login functionality.
 *
 * When a QPalXUser logs in using the login for, class is invoked to load the user's information by email.
 *
 * IF user is found, their details including password is returned back to Spring Boot Security to validate their password
 * as well as their granted authority access levels.
 *
 * @author manyce400
 */
@Service
public class QPalXGlobalUserDetailsService implements UserDetailsService {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iqPalxSubscriptionService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXGlobalUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        LOGGER.info("Looking up QPalXUser with User Email: {} for login...", userEmail);
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserByEmail(userEmail);

        if(qPalXUser != null) {
            LOGGER.info("Valid QPalXUser has been found:>  {} proceeding with user subscription validation", qPalXUser);

            // Validate subscription only for UserTypeE Student.
            QPalxUserTypeE  userTypeE = qPalXUser.getUserType();

            if (QPalxUserTypeE.STUDENT == userTypeE) {
                SubscriptionValidationResult subscriptionValidationResult = iqPalxSubscriptionService.validateUserQPalXSubscription(qPalXUser);
                WebQPalXUser validatedQPalXUser = getQPalXLoginEvent(qPalXUser, subscriptionValidationResult);

                if(SubscriptionStatusE.ACTIVE == subscriptionValidationResult.getSubscriptionStatusE()) {
                    return validatedQPalXUser;
                } else {
                    LOGGER.info("No valid or active subscription found for user with email: {}", qPalXUser.getEmail());
                    throw new QPalXUserLoginException(validatedQPalXUser, "QPalXSubscription has currently expired");
                }
            } else {
                // Create login with no subscription event
                WebQPalXUser user = getQPalXLoginEvent(qPalXUser, null);
                return user;
            }
        }

        LOGGER.info("Denying access to QPalX for User Email: {}", userEmail);
        WebQPalXUser invalidUser = getInvalidLoginEvent();
        throw new QPalXUserLoginException(invalidUser, String.format("User with email :> =%s was not found in QPalX User Database", userEmail));
    }

    private WebQPalXUser getQPalXLoginEvent(final QPalXUser qPalXUser, SubscriptionValidationResult subscriptionValidationResult) {
        WebQPalXUser webQPalXUser = new WebQPalXUser(qPalXUser, subscriptionValidationResult);
        qPalXUser.setLastLoginDate(new DateTime());
        iqPalxUserService.saveQPalXUser(qPalXUser);
        return webQPalXUser;
    }

    private WebQPalXUser getInvalidLoginEvent() {
        SubscriptionValidationResult subscriptionValidationResult = new SubscriptionValidationResult(SubscriptionStatusE.INVALID);
        WebQPalXUser webQPalXUser = new WebQPalXUser(subscriptionValidationResult);
        return webQPalXUser;
    }
}

