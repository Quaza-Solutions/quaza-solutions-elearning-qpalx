package com.quaza.solutions.qpalx.elearning.web.home;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.service.geographical.IGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.WebQPalXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

/**
 * Main Controller that handles all access to main QPalX home and login pages.  User authentication is
 * performed along with Spring Security to make sure that a Login is presented if user are not properly
 * authenticated.
 *
 * @author manyce400
 */
@Controller
public class ApplicationHomeController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    public static final String QPALX_HOME_PAGE = "launch";

    private static final String STUDENT_HOME_PAGE = "qpalx-student/home/home";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ApplicationHomeController.class);



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String execQPalxMainHomePage(Model model) {
        LOGGER.info("Received request to access main QPalx home page...");
        Optional<QPalXUser> optionalUser = getLoggedInQPalXUser();

        if(optionalUser.isPresent()) {
            // TODO complete implementation
            LOGGER.info("Current user logged in with email:> {}", optionalUser.get().getEmail());

            if (QPalxUserTypeE.STUDENT == optionalUser.get().getUserType()) {
                routeQPalxUserByUserType(optionalUser.get());
            }
            return null;
        } else {
            LOGGER.info("Valid logged in QPalxUser session not found, forwarding to main home page.");
            return QPALX_HOME_PAGE;
        }

    }

    private Optional<QPalXUser> getLoggedInQPalXUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        WebQPalXUser webQPalXUser = auth.getPrincipal() instanceof  WebQPalXUser ? (WebQPalXUser) auth.getPrincipal() : null;

        if (webQPalXUser != null) {
            QPalXUser qPalXUser = webQPalXUser.getQPalXUser();
            return Optional.of(qPalXUser);
        }

        return Optional.empty();
    }

    private String routeQPalxUserByUserType(QPalXUser qPalXUser) {
        QPalxUserTypeE qPalxUserTypeE = qPalXUser.getUserType();
        String userLandingPage = null;

        switch (qPalxUserTypeE) {
            case STUDENT:
                LOGGER.info("Routing to home page for student with email: {}", qPalXUser.getEmail());
                userLandingPage = STUDENT_HOME_PAGE;
                break;
            default:
        }

        return userLandingPage;
    }
}
