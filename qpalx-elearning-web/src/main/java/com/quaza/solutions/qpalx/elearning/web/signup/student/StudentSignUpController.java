package com.quaza.solutions.qpalx.elearning.web.signup.student;

import com.quaza.solutions.qpalx.elearning.web.signup.SignUpSelectionWebVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author manyce400
 */
@Controller
public class StudentSignUpController {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentSignUpController.class);


    @RequestMapping(value = "/select-signup-payment", method = RequestMethod.POST)
    public String selectSignUpPaymentPage(final ModelMap modelMap, Model model) {
        LOGGER.info("Processing student signup payment page");
        model.addAttribute("SignUpSelectionWebVO", new SignUpSelectionWebVO());
        return "student-signup/payment";
    }

    @RequestMapping(value = "/customize-proficiency-ranking", method = RequestMethod.POST)
    public String customizeStudentProficiencyRankings(final ModelMap modelMap, Model model) {
        LOGGER.info("Processing student signup payment page");
        model.addAttribute("SignUpSelectionWebVO", new SignUpSelectionWebVO());
        return "student-signup/proficiency";
    }

}
