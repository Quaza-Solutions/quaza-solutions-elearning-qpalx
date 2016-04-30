package com.quaza.solutions.qpalx.elearning.web.student.subscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.service.geographical.IGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.payment.electronic.mpower.MPowerQPalXPaymentService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalXUserSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.QPalXWebUserVO;
import com.quaza.solutions.qpalx.elearning.web.utils.IFileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Main Controller class that handles all QPalX Subscriptions.
 *
 * @SessionAttributes annotation is used to store User's Subscription Information In Session till completion of subscription.
 * http://stackoverflow.com/questions/4623667/abstractwizardformcontroller-using-annotated-controllers for AbstractWizard functionality in Spring Boot
 *
 * Created by manyce400 on 12/5/15.
 */
@Controller
@SessionAttributes(value = {"QPalXUserSubscriptionForm", "MPowerOnSitePaymentCharge", "qPalXWebUserVO"})
public class QPalXStudentSubscriptionController {



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
    @Qualifier("com.quaza.solutions.qpalx.elearning.web.FileUploadUtil")
    private IFileUploadUtil iFileUploadUtil;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.services.MPowerQPalXPaymentService")
    MPowerQPalXPaymentService mPowerQPalXPaymentService;




    private QPalXStudentSubscriptionHTMLPath qPalXStudentSubscriptionHTMLPath = new QPalXStudentSubscriptionHTMLPath();

    private static final String STUDENT_SUBSCRIPTION_HTML_ROOT = "qpalx-student/subscription/";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXStudentSubscriptionController.class);


    /**
     * Start QPalX User subscription transaction process.
     *
     * @param model
     * @return subscription-user-info.html page
     */
    @RequestMapping(value = "/QGatewaySignup", method = RequestMethod.GET)
    public String startQPalXUserSubscriptionInfo(final ModelMap modelMap, Model model) {
        LOGGER.info("Starting new QPalX User Subscription...");

        // get country and subscription data
        List<QPalXMunicipality> municipalities = iqPalXMunicipalityService.findAllQPalXMunicipalities();
        List<QPalXSubscription> subscriptions = iqPalxSubscriptionService.findAllQPalXSubscriptionsByCountryCode("GH");
        LOGGER.info("Found number of municipalities: {} number of subscriptions: {}", municipalities.size(), subscriptions.size());

        model.addAttribute("QPalXMunicipalities", municipalities);
        model.addAttribute("QPalXUserSubscriptions", subscriptions);
        modelMap.addAttribute("QPalXUserSubscriptionForm", new QPalXWebUserVO());
        return qPalXStudentSubscriptionHTMLPath.visitSubscriptionPage("QPalx-Student-Info");
    }

    @RequestMapping(value = "/QPalxSubscribptionEducatioProfile", method = RequestMethod.POST)
    public String startQPalXUserSubscriptionEducationProfile(@ModelAttribute("QPalXUserSubscriptionForm") QPalXWebUserVO userSubscriptionInfo, Model model,
                                                             @RequestParam("name") String fileName, @RequestParam("file") MultipartFile imageFile) {
        LOGGER.info("Starting user QPalX Subscription Education profile collection: {} ", userSubscriptionInfo);
        
        //attempt to upload image
        String imageUploadedFile = iFileUploadUtil.uploadAndScaleImageFile(imageFile);
        System.out.println("imageUploadedFile = " + imageUploadedFile);

        userSubscriptionInfo.setStudentPhotoFile(imageUploadedFile);

        // Verify that we dont already have a QPalXUser with the given email address
        QPalXUser existingUser = iqPalxUserService.findQPalXUserByEmail(userSubscriptionInfo.getEmail());
        if(existingUser != null) {
            LOGGER.info("Existing user with email address:> {} is already subscribed for QPalX", userSubscriptionInfo.getEmail());
            model.addAttribute("SubscriptionErrors", "QPalX user already created with the provided email, input different email");
            return qPalXStudentSubscriptionHTMLPath.visitSubscriptionPage("QPalx-Student-Info");
        }

        List<StudentTutorialLevel> studentTutorialLevels = iqPalXTutorialService.findAllQPalXTutorialLevels();
        model.addAttribute("QPalXTutorialLevels", studentTutorialLevels);
        return qPalXStudentSubscriptionHTMLPath.visitSubscriptionPage("UserEducation-Profile");
    }


}
