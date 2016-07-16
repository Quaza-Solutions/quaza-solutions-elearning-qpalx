package com.quaza.solutions.qpalx.elearning.web.home;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.service.geographical.IGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IStudentCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * Main Controller that handles all access to main QPalX home and login pages.  User authentication is
 * performed along with Spring Security to make sure that a Login is presented if user are not properly
 * authenticated.
 *
 * @author manyce400
 */
@Controller
public class ApplicationHomeController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StudentCurriculumService")
    private IStudentCurriculumService iStudentCurriculumService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultGeographicalDateTimeFormatter")
    private IGeographicalDateTimeFormatter iGeographicalDateTimeFormatter;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ApplicationHomeController.class);


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String execQPalxMainHomePage(final Model model) {
        LOGGER.info("Received request to access main QPalx home page...");
        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        if(optionalUser.isPresent()) {
            // TODO complete implementation
            LOGGER.info("Current user logged in with email:> {}", optionalUser.get().getEmail());

            if (QPalxUserTypeE.STUDENT == optionalUser.get().getUserType()) {
                addQPalXUserDetailsToResponse(model, CurriculumType.CORE, optionalUser.get());
                return ContentRootE.Student_Home.getContentRootPagePath("home");
            }

            LOGGER.info("Only Student QPalX users currently supported");
            return ContentRootE.Home.getContentRootPagePath("launch");
        } else {
            LOGGER.info("Valid logged in QPalxUser session not found, redirecting to main home page.");
            return ContentRootE.Home.getContentRootPagePath("launch");
        }
    }

    @RequestMapping(value = "/select-curriculum", method = RequestMethod.GET)
    public String execQPalxMainHomePageWithCurriculumType(final Model model, @RequestParam("curricumlumType") String curricumlumType) {
        LOGGER.info("Received request to access main QPalx home page with curricumlumType:> {}", curricumlumType);
        CurriculumType curriculumType = CurriculumType.valueOf(curricumlumType);
        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        if(optionalUser.isPresent() && curriculumType != null) {
            LOGGER.info("Current user logged in with email:> {}", optionalUser.get().getEmail());

            if (QPalxUserTypeE.STUDENT == optionalUser.get().getUserType()) {
                addQPalXUserDetailsToResponse(model, curriculumType, optionalUser.get());
                return ContentRootE.Student_Home.getContentRootPagePath("home");
            }

            LOGGER.info("Only Student QPalX users currently supported");
            return ContentRootE.Home.getContentRootPagePath("launch");
        } else {
            LOGGER.info("Valid logged in QPalxUser session not found, redirecting to main home page.");
            return ContentRootE.Home.getContentRootPagePath("launch");
        }
    }

    private void addQPalXUserDetailsToResponse(final Model model, CurriculumType curriculumType, QPalXUser qPalXUser) {
        List<ELearningCurriculum> eLearningCurricula = null;
        switch (curriculumType) {
            case CORE:
                eLearningCurricula = iStudentCurriculumService.findAllStudentCoreELearningCurriculum(qPalXUser);
                break;
            case ELECTIVE:
                eLearningCurricula = iStudentCurriculumService.findAllStudentElectiveELearningCurriculum(qPalXUser);
                break;
            default:
                break;

        }

        model.addAttribute("CurriculumType", curriculumType.toString());
        model.addAttribute("LoggedInQPalXUser", qPalXUser);
        model.addAttribute("StudentUserCurricula", eLearningCurricula);

    }


}
