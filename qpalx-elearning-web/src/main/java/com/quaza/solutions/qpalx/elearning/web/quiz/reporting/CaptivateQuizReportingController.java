package com.quaza.solutions.qpalx.elearning.web.quiz.reporting;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IStudentCurriculumService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import com.quaza.solutions.qpalx.elearning.web.utils.IRedirectStrategyExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Controller that parses and processes Adobe captivate Quiz results for a given user.
 *
 * @author manyce400
 */
@Controller
public class CaptivateQuizReportingController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StudentCurriculumService")
    private IStudentCurriculumService iStudentCurriculumService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
    private IRedirectStrategyExecutor iRedirectStrategyExecutor;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CaptivateQuizReportingController.class);

    @RequestMapping(value = "/save-captivate-quiz-score", method = RequestMethod.POST)
    public void testSubmit(Model model, @RequestParam Map<String,String> allRequestParams) {
        LOGGER.info("Attempting to save Captivate Quiz score with map: {}", allRequestParams);

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();
        if (optionalUser.isPresent()) {
            LOGGER.info("detected user that submitted the quiz to be: {}", optionalUser.get().getEmail());
        } else {
            LOGGER.info("No valid logged in user detected for submitted quiz");
        }
    }

    @RequestMapping(value = "/get-proprof-quiz", method = RequestMethod.GET)
    public void accessProProfsQuizPage(Model model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing request to access ProProfs quiz page");

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();
        if (optionalUser.isPresent()) {
            LOGGER.info("detected user that submitted the quiz to be: {}", optionalUser.get().getEmail());
            String targetURL = "/view-quiz?user_name=" + optionalUser.get().getEmail() + "&user_email=" + optionalUser.get().getEmail() + "&user_id=" + optionalUser.get().getEmail();
            iRedirectStrategyExecutor.sendRedirect(request, response, targetURL);
        }
    }

    @RequestMapping(value = "/view-quiz", method = RequestMethod.GET)
    public String accessProProfsQuizPage(Model model,
                                       @RequestParam("user_name") String userName, @RequestParam("user_email") String userEmail, @RequestParam("user_id") String userID) {
        LOGGER.info("Forwarding request to pro profs quiz");

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        addQPalXUserDetailsToResponse(model, CurriculumType.CORE, optionalUser.get());

        model.addAttribute("Quiz", "https://www.proprofs.com/quiz-school/story.php?title=mtcxmdqxmw8z2o&id=1710156&ew=430");

        return ContentRootE.Student_Home.getContentRootPagePath("video-widget");
    }

//    public void testSubmit(Model model,
//                           @RequestParam("CompanyName") String companyName,
//                           @RequestParam("DepartmentName") String departmentName,
//                           @RequestParam("CourseName") String courseName,
//                           @RequestParam("Filename") String fileName,
//                           @RequestParam("Filedata") MultipartFile file) {
//        LOGGER.info("Attempting to save Captivate Quiz score with companyName: {}", companyName);
//
//        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();
//        if (optionalUser.isPresent()) {
//            LOGGER.info("detected user that submitted the quiz to be: {}", optionalUser.get().getEmail());
//        } else {
//            LOGGER.info("No valid logged in user detected for submitted quiz");
//        }
//    }


//
//    public void testSubmit(Model model, @RequestParam("filename") String fileName) {
//        LOGGER.info("Attempting to save Captivate Quiz score with fileName: {}", fileName);
//
//        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();
//        if (optionalUser.isPresent()) {
//            LOGGER.info("detected user that submitted the quiz to be: {}", optionalUser.get().getEmail());
//        } else {
//            LOGGER.info("No valid logged in user detected for submitted quiz");
//        }
//    }


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
        //model.addAttribute("LoggedInQPalXUser", qPalXUser);
        model.addAttribute("StudentUserCurricula", eLearningCurricula);

    }
}
