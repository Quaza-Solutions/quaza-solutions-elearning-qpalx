package com.quaza.solutions.qpalx.elearning.web.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author manyce400
 */
@Controller
public class ApplicationLoginController {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ApplicationLoginController.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String qpalxLogin(Model model) {
        LOGGER.info("Redirecting user request to QPalX application login form");
        return "login";
    }

}
