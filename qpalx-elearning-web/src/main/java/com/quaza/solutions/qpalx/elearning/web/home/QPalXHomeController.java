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

/**
 * Main Controller that handles all access to main QPalX home and login pages.  User authentication is
 * performed along with Spring Security to make sure that a Login is presented if user are not properly
 * authenticated.
 *
 * @author manyce400
 */
@Controller
public class QPalXHomeController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXHomeController.class);


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexMain(Model model) {
        LOGGER.info("Redirecting request to main QPalX welcome page...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Figure out who the currently logged on user is
        String qPalXUserLoggedIn = auth.getPrincipal() != null ? auth.getPrincipal().toString() : "";
        if(qPalXUserLoggedIn.equals("anonymousUser") || qPalXUserLoggedIn.equals("")) {
            LOGGER.debug("No valid QPalXUser has been logged in");
            return "index";
        } else {
            WebQPalXUser webQPalXUser = (WebQPalXUser) auth.getPrincipal();
            QPalXUser qPalXUser = webQPalXUser.getQPalXUser();

            if (QPalxUserTypeE.STUDENT == qPalXUser.getUserType()) {
                webQPalXUser.setIGeographicalDateTimeFormatter(iGeographicalDateTimeFormatter);
                model.addAttribute("WebQPalXUser", webQPalXUser);
                System.out.println("webQPalXUser = " + webQPalXUser);
                return "qpalx-student/home/student-home";
            } else {
                return "qpalx-student/home/admin-home";
            }
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String qpalxLogin(Model model) {
        LOGGER.info("Redirecting user request to QPalX application login form");
        return "login";
    }
}
