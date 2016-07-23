package com.quaza.solutions.qpalx.elearning.web.signup.parent;

import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.QPalXWebUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author manyce400
 */
@Controller
@SessionAttributes(value = {"RegistrationSelectionWebVO", "QPalXWebUserVO"})
public class ParentRegistrationController {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ParentRegistrationController.class);


    //@RequestMapping(value = "/select-signup-payment", method = RequestMethod.POST)
    public String selectSignUpPaymentPage(Model model, @ModelAttribute("QPalXWebUserVO") QPalXWebUserVO qPalXWebUserVO) {
        LOGGER.info("Student signup payment requested with qPalXWebUserVO: {}", qPalXWebUserVO);
        return ContentRootE.Student_Signup.getContentRootPagePath("payment");
    }

}
