package com.quaza.solutions.qpalx.elearning.web.signup;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import com.quaza.solutions.qpalx.elearning.web.home.ApplicationHomeController;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.QPalXWebUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * Main controller for handling all QPalX signup request and routing to appropriate Signup processes for
 * Students, Teachers and Admins.
 *
 * @author manyce400
 */
@Controller
@SessionAttributes(value = {"SignUpSelectionWebVO", "QPalXWebUserVO"})
public class SignUpController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iqPalXMunicipalityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
    private IQPalXTutorialService iqPalXTutorialService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SignUpController.class);


    @RequestMapping(value = "/qpalx-sign-up", method = RequestMethod.GET)
    public String signUpStartPage(final ModelMap modelMap, Model model) {
        LOGGER.info("Processing QPalX signup request, sending to signup selection page....");
        model.addAttribute("SignUpSelectionWebVO", new SignUpSelectionWebVO());
        return "sign-up";
    }

    @RequestMapping(value = "/sign-up-type-select", method = RequestMethod.POST)
    public String signUpSelectionPage(final ModelMap modelMap, Model model, @ModelAttribute("SignUpSelectionWebVO") SignUpSelectionWebVO signUpSelectionWebVO) {
        LOGGER.info("Received initial QPalX Signup selection request signUpSelectionWebVO: {}", signUpSelectionWebVO);

        String selectedSignupTypePage = null;
        SignUpTypeE signUpTypeE = signUpSelectionWebVO.getAsSignUpTypeE();
        switch (signUpTypeE) {
            case Student:
                LOGGER.info("Student QPalX signup selected, returning student sign-up form");
                selectedSignupTypePage = "student-signup/sign-up-student";
                addSignupAttributesToResponse(model, modelMap);
                break;
            case Parent:
                LOGGER.info("Parent QPalX signup selected, returning parent sign-up form");
                selectedSignupTypePage = "parent-signup/sign-up-parent";
                addSignupAttributesToResponse(model, modelMap);
                break;
            case Teacher:
                LOGGER.info("Teacher QPalX signup selected which is currently not supported, returning to home page.");
                selectedSignupTypePage = ApplicationHomeController.QPALX_HOME_PAGE;
                break;
            default:
                LOGGER.info("Invalid QPalX signup type selected, returning to home page");
                selectedSignupTypePage = ApplicationHomeController.QPALX_HOME_PAGE;
                break;
        }

        return selectedSignupTypePage;
    }


    private void addSignupAttributesToResponse(final Model model, final ModelMap modelMap) {
        List<QPalXMunicipality> municipalities = iqPalXMunicipalityService.findAllQPalXMunicipalities();
        List<StudentTutorialLevel> studentTutorialLevels = iqPalXTutorialService.findAllQPalXTutorialLevels();
        List<StudentTutorialGrade> studentTutorialGrades = iqPalXTutorialService.findAllStudentTutorialGrade();
        modelMap.addAttribute("QPalXWebUserVO", new QPalXWebUserVO());
        model.addAttribute("QPalXMunicipalities", municipalities);
        model.addAttribute("StudentTutorialLevels", studentTutorialLevels);
        model.addAttribute("StudentTutorialGrades", studentTutorialGrades);
    }

}
