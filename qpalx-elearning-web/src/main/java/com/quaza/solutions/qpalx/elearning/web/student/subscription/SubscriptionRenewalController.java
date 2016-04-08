package com.quaza.solutions.qpalx.elearning.web.student.subscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower.MPowerOnSitePaymentCharge;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.mpower.MPowerPaymentActionResponse;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.service.geographical.IGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.payment.electronic.mpower.MPowerQPalXPaymentService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalXUserSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.QPalXWebUserVO;
import com.quaza.solutions.qpalx.elearning.web.qpalxuser.WebQPalXUser;
import com.quaza.solutions.qpalx.elearning.web.utils.IFileUploadUtil;
import org.joda.time.DateTime;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller handling all functionality around Student subscription renewals.
 *
 *
 * @author manyce400
 */
@Controller
@SessionAttributes(value = {"QPalXUserSubscriptionForm", "MPowerOnSitePaymentCharge", "qPalXWebUserVO"})
public class SubscriptionRenewalController {




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

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SubscriptionRenewalController.class);


    @RequestMapping(value = "/GatewayAccessFailure", method = RequestMethod.GET)
    public String indexMain(ModelMap modelMap, Model model, HttpServletRequest httpServletRequest) {
        WebQPalXUser validationFailureUser = (WebQPalXUser) httpServletRequest.getSession().getAttribute("Validation_Failure_User");
        QPalXUser qPalXUser = validationFailureUser.getQPalXUser();

        //build from the validation failure user
        QPalXWebUserVO qPalXWebUserVO = QPalXWebUserVO.builder()
                .firstName(qPalXUser.getFirstName())
                .lastName(qPalXUser.getLastName())
                .email(qPalXUser.getEmail())
                .municipalityID(qPalXUser.getQPalXMunicipality().getId())
                .build();


        List<QPalXSubscription> subscriptions = iqPalxSubscriptionService.findAllQPalXSubscriptionsByCountryCode("GH");
        model.addAttribute("QPalXUserSubscriptions", subscriptions);

        modelMap.addAttribute("QPalXUserSubscriptionForm", qPalXWebUserVO);
        return qPalXStudentSubscriptionHTMLPath.visitSubscriptionPage("Subscription_Renewal");
    }


    @RequestMapping(value = "/ConfirmSubscriptionRenewal", method = RequestMethod.POST)
    public String confirmSubscriptionRenewal(@ModelAttribute("QPalXUserSubscriptionForm") QPalXWebUserVO userSubscriptionInfo, Model model) {
        LOGGER.info("Displaying QPalX Subscription confirmation page with UserSubscriptionInfo: {} ", userSubscriptionInfo);

        QPalXSubscription subscription = iqPalxSubscriptionService.findQPalXSubscriptionByID(userSubscriptionInfo.getSubscriptionID());
        QPalXMunicipality municipality = iqPalXMunicipalityService.findQPalXMunicipalityByID(userSubscriptionInfo.getMunicipalityID());

        // Calculate subscription expiration date and display it in the users Local Date Timezone
        DateTime subscriptionExpiryDateFromToday = iqPalxSubscriptionService.calculateSubscriptionExpiryDateFromToday(subscription);
        String subscriptionExpiryAsString = iGeographicalDateTimeFormatter.getDisplayDateTimeWithTimeZone(subscriptionExpiryDateFromToday, municipality);

        //model.addAttribute("QPalXUserSubscriptionForm", userSubscriptionInfo);
        model.addAttribute("UserSelectedMunicipality", municipality);
        model.addAttribute("UserSelectedSubscription", subscription);
        model.addAttribute("UserSubscriptionExpiryDate", subscriptionExpiryAsString);
        return qPalXStudentSubscriptionHTMLPath.visitSubscriptionPage("Subscription_Renewal_Confirm");
    }


    @RequestMapping(value = "/GetSubscriptionRenewalToken", method = RequestMethod.POST)
    public String executeSubscriptionRenewal(@ModelAttribute("QPalXUserSubscriptionForm") QPalXWebUserVO userSubscriptionInfo, final ModelMap modelMap) {
        LOGGER.info("Processing QPalX User subscription with UserSubscriptionInfo: {} ", userSubscriptionInfo);

        QPalXSubscription subscription = iqPalxSubscriptionService.findQPalXSubscriptionByID(userSubscriptionInfo.getSubscriptionID());
        QPalXMunicipality municipality = iqPalXMunicipalityService.findQPalXMunicipalityByID(userSubscriptionInfo.getMunicipalityID());


        // Invoke MPower to debit for payment.
        MPowerOnSitePaymentCharge paymentCharge = mPowerQPalXPaymentService.executePaymentForSubscription(userSubscriptionInfo, subscription);
        System.out.println("paymentCharge = " + paymentCharge);
        modelMap.addAttribute("MPowerOnSitePaymentCharge", paymentCharge);
        return qPalXStudentSubscriptionHTMLPath.visitSubscriptionPage("Subscription_Renewal_Token_Confirm");
    }

    @RequestMapping(value = "/SubscribptionRenewalComplete", method = RequestMethod.POST)
    public String processQPalXUserSubscription(final SessionStatus status,
                                             @ModelAttribute("QPalXUserSubscriptionForm") QPalXWebUserVO userSubscriptionInfo,
                                             @ModelAttribute("MPowerOnSitePaymentCharge") MPowerOnSitePaymentCharge paymentCharge,
                                             HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        LOGGER.info("Completing QPalXSubscription using UserSubscriptionInfo: {}", userSubscriptionInfo);


        QPalXSubscription subscription = iqPalxSubscriptionService.findQPalXSubscriptionByID(userSubscriptionInfo.getSubscriptionID());
        WebQPalXUser validationFailureUser = (WebQPalXUser) httpServletRequest.getSession().getAttribute("Validation_Failure_User");
        QPalXUser qPalXUser = validationFailureUser.getQPalXUser();
        System.out.println("qPalXUser = " + qPalXUser);


        String userAuthenticationToken = userSubscriptionInfo.getmPowerAuthorizationToken();
        paymentCharge.setUserPaymentConfirmToken(userAuthenticationToken);
        MPowerPaymentActionResponse mPowerPaymentActionResponse = mPowerQPalXPaymentService.completeSubscriptionPaymentPhaseII(paymentCharge);
        System.out.println("mPowerPaymentActionResponse = " + mPowerPaymentActionResponse);

        // TODO check case where user payment response from MPower was not successful and redirect accordingly

        // now persist all this information
        iqPalXUserSubscriptionService.renewQPalXUserSubscription(qPalXUser, subscription);
        status.setComplete();
        return  "/index";
    }

}
