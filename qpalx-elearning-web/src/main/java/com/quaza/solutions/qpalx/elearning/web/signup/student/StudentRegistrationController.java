package com.quaza.solutions.qpalx.elearning.web.signup.student;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.SimplifiedProficiencyRankE;
import com.quaza.solutions.qpalx.elearning.service.geographical.IGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.prepaidsubscription.IQPalxPrepaidIDService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalXUserSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
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
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

/**
 * @author manyce400
 */
@Controller
@SessionAttributes(value = {"RegistrationSelectionWebVO", "QPalXWebUserVO"})
public class StudentRegistrationController {




    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iqPalXMunicipalityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iqPalxSubscriptionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXUserSubscriptionService")
    private IQPalXUserSubscriptionService iqPalXUserSubscriptionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
    private IQPalXTutorialService iqPalXTutorialService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxPrepaidIDService")
    private IQPalxPrepaidIDService iQpalxPrepaidIDService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentRegistrationController.class);


    @RequestMapping(value = "/select-signup-payment", method = RequestMethod.POST)
    public String accessRegistrationPayment(Model model, @ModelAttribute("QPalXWebUserVO") QPalXWebUserVO qPalXWebUserVO) {
        LOGGER.info("Student signup payment requested with qPalXWebUserVO: {}", qPalXWebUserVO);
        return ContentRootE.Student_Signup.getContentRootPagePath("payment-selection");
    }

    @RequestMapping(value = "/customize-proficiency-ranking", method = RequestMethod.POST)
    public String customizeStudentProficiencyRankings(final ModelMap modelMap, Model model, @ModelAttribute("QPalXWebUserVO") QPalXWebUserVO qPalXWebUserVO) {
        LOGGER.info("Processing student signup payment page with qPalXWebUserVO: {}", qPalXWebUserVO);
        model.addAttribute("SimplifiedProficiencyRanks", SimplifiedProficiencyRankE.values());

        LOGGER.info("Processing student signup payment page");
        System.out.println("Value Entered: " + qPalXWebUserVO.getPrepaidValue());
        QPalXMunicipality qPalXMunicipality = new QPalXMunicipality();
        QPalXCountry qPalXCountry = new QPalXCountry();
        qPalXCountry.setCountryCode("USA");
        qPalXMunicipality.setCode("NY");
        qPalXMunicipality.setQPalXCountry(qPalXCountry);
        //result = testCondition ? value1 : value2
        if(qPalXWebUserVO.getIncorrectValueCounter() == 5){
            return "/launch.html";//penalty
        }
        else {
            if (iQpalxPrepaidIDService.redeemCode(qPalXWebUserVO.getPrepaidValue(), qPalXMunicipality)) {
                qPalXWebUserVO.setRedemptionFailure(false);
                model.addAttribute("QPalXWebUserVO", qPalXWebUserVO);
                LOGGER.info("Processing student signup payment page with qPalXWebUserVO: {}", qPalXWebUserVO);
                model.addAttribute("SimplifiedProficiencyRanks", SimplifiedProficiencyRankE.values());
                return ContentRootE.Student_Signup.getContentRootPagePath("proficiency");
            } else {
                qPalXWebUserVO.setRedemptionFailure(true);
                int holder = qPalXWebUserVO.getIncorrectValueCounter();
                holder++;
                qPalXWebUserVO.setIncorrectValueCounter(holder);
                System.out.println("Failed Attempts: " + qPalXWebUserVO.getIncorrectValueCounter());
                model.addAttribute("QPalXWebUserVO", qPalXWebUserVO);
                return ContentRootE.Student_Signup.getContentRootPagePath("payment-selection");
            }
        }
    }

    @RequestMapping(value = "/complete-qpalx-signup", method = RequestMethod.POST)
    public String completeQPalXSignup(final SessionStatus status, @ModelAttribute("QPalXWebUserVO") QPalXWebUserVO qPalXWebUserVO) {
        LOGGER.info("Attempting to create new QPalX subscription using qPalXWebUserVO: {} ", qPalXWebUserVO);

        // default to monthly
        qPalXWebUserVO.setSubscriptionID(3L);

        // save all subscription details
        Optional<QPalXUser> optionalQPalXUser = iqPalXUserSubscriptionService.createNewQPalXUserWithTutorialSubscription(qPalXWebUserVO);
        status.isComplete();

        if(optionalQPalXUser.isPresent()) {
            LOGGER.info("QPalXUser subscription has been succesfully processed, returning to QPalX home page to signup...");
            return ContentRootE.Home.getContentRootPagePath("homepage");
        }

        return ContentRootE.Home.getContentRootPagePath("homepage");
    }

}
