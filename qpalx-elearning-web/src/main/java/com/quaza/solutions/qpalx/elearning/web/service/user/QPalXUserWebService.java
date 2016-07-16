package com.quaza.solutions.qpalx.elearning.web.service.user;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.WebQPalXUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
public class QPalXUserWebService implements IQPalXUserWebService {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXUserWebService.class);


    @Override
    public Optional<QPalXUser> getLoggedInQPalXUser() {
        LOGGER.info("Attempting to retrieve the current logged in QPalX user session");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        WebQPalXUser webQPalXUser = auth.getPrincipal() instanceof  WebQPalXUser ? (WebQPalXUser) auth.getPrincipal() : null;

        if (webQPalXUser != null) {
            QPalXUser qPalXUser = webQPalXUser.getQPalXUser();
            LOGGER.info("Returning logged in user session for:> {}", qPalXUser.getEmail());
            return Optional.of(qPalXUser);
        }

        return Optional.empty();
    }

}
