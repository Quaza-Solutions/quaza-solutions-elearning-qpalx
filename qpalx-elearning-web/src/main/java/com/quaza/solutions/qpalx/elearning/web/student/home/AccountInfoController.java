package com.quaza.solutions.qpalx.elearning.web.student.home;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalXUserSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.CurriculumDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.DomainDataDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.WebOperationErrorAttributesE;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.QPalXWebUserVO;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import com.quaza.solutions.qpalx.elearning.web.utils.IFileUploadUtil;
import com.quaza.solutions.qpalx.elearning.web.utils.IRedirectStrategyExecutor;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
@Controller
public class AccountInfoController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iqPalxSubscriptionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iqPalXMunicipalityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
    private IQPalXTutorialService iqPalXTutorialService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService")
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXUserSubscriptionService")
    private IQPalXUserSubscriptionService iqPalXUserSubscriptionService;

    @Autowired
    @Qualifier("com.quaza.solutions.qpalx.elearning.web.FileUploadUtil")
    private IFileUploadUtil iFileUploadUtil;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
    private IRedirectStrategyExecutor iRedirectStrategyExecutor;


    DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AccountInfoController.class);


    @RequestMapping(value = "/account-info", method = RequestMethod.GET)
    public String viewOverallQPalXUserAccount(final Model model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Loading Student account information page details....");

        // Add display attributes for Student information panel.
        qPalXUserInfoPanelService.addUserInfoAttributes(model);
        model.addAttribute(CurriculumDisplayAttributeE.DisplayUserInfo.toString(), Boolean.TRUE.toString());

        // IF this is a result of a redirect add any error messages to model
        iRedirectStrategyExecutor.addWebOperationRedirectErrorsToModel(model, request);

        // Get current Student subscription details
        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();
        Optional<StudentSubscriptionProfile> activeUserSubscriptionProfile = iqPalxSubscriptionService.findActiveUserSubscriptionProfile(optionalUser.get());
        model.addAttribute(DomainDataDisplayAttributeE.SubscriptionInfo.toString(), activeUserSubscriptionProfile.get());

        DateTime subscriptionPurchaseDate = activeUserSubscriptionProfile.get().getSubscriptionPurchasedDate();
        model.addAttribute(DomainDataDisplayAttributeE.SubscriptionPurchaseDate.toString(), subscriptionPurchaseDate.toString(dtf));

        // Calculate number of days till expiration
        int daysTillExpiration = iqPalxSubscriptionService.calculateNumberOfDaysTillExpiration(activeUserSubscriptionProfile.get());
        if (daysTillExpiration > 1) {
            model.addAttribute(DomainDataDisplayAttributeE.DaysTillSubscriptionExpire.toString(), daysTillExpiration + " Days");
        } else {
            // Only display subscription renewal link if there is 1 day till it expires.
            model.addAttribute(DomainDataDisplayAttributeE.DaysTillSubscriptionExpire.toString(), daysTillExpiration + " Day");
            model.addAttribute(DomainDataDisplayAttributeE.DisplaySubscriptionRenewal.toString(), "TRUE");
        }

        // Add all details required to show what current user settings are.
        model.addAttribute("QPalXWebUserVO", new QPalXWebUserVO());
        addQPalXUserAccountInfoAttributes(model);

        return ContentRootE.Student_Home.getContentRootPagePath("account-info");
    }


    @RequestMapping(value = "/update-student-photo", method = RequestMethod.POST)
    public void updateAccountWithPhoto(@RequestParam("image-data") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Uploading and saving user photo information: {}", multipartFile);
        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        iFileUploadUtil.uploadQPalxUserPhoto(optionalUser.get(), multipartFile);
        String targetURL = "/account-info";
        iRedirectStrategyExecutor.sendRedirect(request, response, targetURL);
    }


    @RequestMapping(value = "/update-account-info", method = RequestMethod.POST)
    public void executeUpdateQPalXUserAccount(final Model model, @ModelAttribute("QPalXWebUserVO") QPalXWebUserVO qPalXWebUserVO, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Attempting to update QPalX user information with qPalXWebUserVO: {}", qPalXWebUserVO);

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        // Validate passwords, needs to match
        if(!qPalXWebUserVO.getPassword().equals(qPalXWebUserVO.getPasswordConfirm())) {
            LOGGER.info("Submitted password does not match confirm value, redirecting back to account info page");
            String targetURL = "/account-info";
            String errorMessage = "Submitted password does not match confirmation password";
            iRedirectStrategyExecutor.sendRedirectWithError(targetURL, errorMessage, WebOperationErrorAttributesE.Invalid_FORM_Submission, request, response);
        } else {
            // Save the user details update and redirect to main home page
            //iqPalXUserSubscriptionService.updateQPalXUserInfo(optionalUser.get(), qPalXWebUserVO);
            String targetURL = "/";
            iRedirectStrategyExecutor.sendRedirect(request, response, targetURL);
        }
    }

    private void addQPalXUserAccountInfoAttributes(final Model model) {
        List<QPalXMunicipality> municipalities = iqPalXMunicipalityService.findAllQPalXMunicipalities();
        List<StudentTutorialLevel> studentTutorialLevels = iqPalXTutorialService.findAllQPalXTutorialLevels();
        List<StudentTutorialGrade> studentTutorialGrades = iqPalXTutorialService.findAllStudentTutorialGrade();
        List<QPalXEducationalInstitution> qPalXEducationalInstitutions = iqPalXEducationalInstitutionService.findAll();
        model.addAttribute("QPalXMunicipalities", municipalities);
        model.addAttribute("StudentTutorialLevels", studentTutorialLevels);
        model.addAttribute("StudentTutorialGrades", studentTutorialGrades);
        model.addAttribute("QPalXEducationalInstitutions", qPalXEducationalInstitutions);
    }
}
