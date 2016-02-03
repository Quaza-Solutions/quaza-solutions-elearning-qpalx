package com.quaza.solutions.qpalx.elearning.web.student.home;

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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author manyce400
 */
@Controller
public class QPalXUserHomeController {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXUserHomeController.class);


    /**
     * Routes users to the main QPalX User page.  No security authentication is enforced at this level
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/QPalXGateway", method = RequestMethod.GET)
    public String indexMain(Model model, @RequestParam("authenticationStatus") String authenticationStatus) {
        LOGGER.info("Redirecting to QPalX GatewayMain");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth = " + auth);


        // Figure out who the currently logged on user is
        String qPalXUserLoggedIn = auth.getPrincipal() != null ? auth.getPrincipal().toString() : "";
        if(qPalXUserLoggedIn.equals("anonymousUser") || qPalXUserLoggedIn.equals("")) {
            LOGGER.info("No valid QPalXUser has been logged in");
            model.addAttribute("authenticationStatus", authenticationStatus);
            return "index";
        } else {
            WebQPalXUser webQPalXUser = (WebQPalXUser) auth.getPrincipal();
            webQPalXUser.setIGeographicalDateTimeFormatter(iGeographicalDateTimeFormatter);
            model.addAttribute("WebQPalXUser", webQPalXUser);
            System.out.println("webQPalXUser = " + webQPalXUser);
            return "qpalx-student/home/student-home";
        }
    }

}
