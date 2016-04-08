package com.quaza.solutions.qpalx.elearning.web.security;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionValidationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 *
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.DefaultQPalXAuthenticationSuccessFailureHandler")
public class DefaultQPalXAuthenticationSuccessFailureHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {



    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalXAuthenticationSuccessFailureHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        QPalxUserTypeE qPalxUserTypeE = getQPalxUserTypeE(authentication);
        System.out.println("\n\nExecuting success handler logic For qPalxUserTypeE: "+qPalxUserTypeE);

        String targetURL = "/";

        switch (qPalxUserTypeE) {
            case STUDENT:
                targetURL = "/";
                break;
            case ADMINISTRATOR:
                targetURL = "/";
                break;
            default:
                break;
        }

        LOGGER.info("QPalX authentication completed succesfully, determining user's landing page");
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, targetURL);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        QPalXUserLoginExpiredException qPalXUserLoginException = (QPalXUserLoginExpiredException) exception.getCause();
        SubscriptionValidationResult validationResult = qPalXUserLoginException.getWebQPalXUser().get().getSubscriptionValidationResult();
        SubscriptionStatusE subscriptionStatusE = validationResult.getSubscriptionStatusE();

        String redirectPage = "";
        switch (subscriptionStatusE) {
            case EXPIRED:
                redirectPage = "/GatewayAccessFailure";
                break;
            case INVALID:
                redirectPage = "/GatewayAccessFailure";
                break;
            default:
                break;
        }


        System.out.println("\nSubscription Failure Result: "+qPalXUserLoginException.getWebQPalXUser().get().getSubscriptionValidationResult().getValidationResultMessage()+"\n");
        //String targetURL = "/QPalXGateway?authenticationStatus=invalid";
        httpServletRequest.getSession().setAttribute("Validation_Failure_User", qPalXUserLoginException.getWebQPalXUser().get());
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, redirectPage);
    }

    private QPalxUserTypeE getQPalxUserTypeE(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<? extends GrantedAuthority> userAuthority = authorities.stream()
                .filter((authority) -> authority.getAuthority().equals(QPalxUserTypeE.STUDENT.toString()) || authority.getAuthority().equals(QPalxUserTypeE.ADMINISTRATOR.toString()))
                .findFirst();

        if(userAuthority.isPresent()) {
            QPalxUserTypeE actualQPalxUserTypeE =  QPalxUserTypeE.valueOf(userAuthority.get().getAuthority());
            return actualQPalxUserTypeE;
        }

        LOGGER.warn("Could not determine the granted authorities of principal user: {}", principal);
        return null;
    }
}
