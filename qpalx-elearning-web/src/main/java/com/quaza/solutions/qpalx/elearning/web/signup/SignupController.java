package com.quaza.solutions.qpalx.elearning.web.signup;

import com.quaza.solutions.qpalx.elearning.web.qpalxuser.QPalXWebUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Main controller for handling all QPalX signup request and routing to appropriate Signup processes for
 * Students, Teachers and Admins.
 *
 * @author manyce400
 */
@Controller
@SessionAttributes(value = {"SignUpSelectionWebVO", "QPalXWebUserVO"})
public class SignUpController {




    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SignUpController.class);


    @RequestMapping(value = "/qpalx-sign-up", method = RequestMethod.GET)
    public String signupStartPage(final ModelMap modelMap, Model model) {
        LOGGER.info("Processing QPalX signup request, sending to signup selection page....");
        model.addAttribute("SignUpSelectionWebVO", new SignUpSelectionWebVO());
        return "sign-up";
    }

    @RequestMapping(value = "/sign-up-type-select", method = RequestMethod.POST)
    public String signUpSelectionPage(final ModelMap modelMap, Model model, @ModelAttribute("SignUpSelectionWebVO") SignUpSelectionWebVO signUpSelectionWebVO) {
        LOGGER.info("Verifying selected signup type from signUpSelectionWebVO: {}", signUpSelectionWebVO);
        model.addAttribute("QPalXWebUserVO", new QPalXWebUserVO());
        return "student-signup/sign-up-student";
    }

}
