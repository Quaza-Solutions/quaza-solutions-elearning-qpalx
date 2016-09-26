package com.quaza.solutions.qpalx.elearning.web.quiz.reporting;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
}
