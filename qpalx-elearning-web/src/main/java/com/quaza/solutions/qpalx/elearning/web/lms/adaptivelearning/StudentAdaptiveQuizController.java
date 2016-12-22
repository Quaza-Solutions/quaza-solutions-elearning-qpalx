package com.quaza.solutions.qpalx.elearning.web.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author manyce400
 */
@Controller
public class StudentAdaptiveQuizController {



//    @Autowired
//    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizService")
//    private IAdaptiveLearningQuizService iAdaptiveLearningQuizService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentAdaptiveQuizController.class);

    @RequestMapping(value = "/view-microlesson-quiz", method = RequestMethod.GET)
    public String viewAdaptiveLessons(final Model model, @RequestParam("quizID") String quizID,
                                      @RequestParam("user_name") String userName, @RequestParam("user_email") String userEmail, @RequestParam("user_id") String userID) {
//        LOGGER.info("Loading up external AdaptiveQuiz with ID: {}", quizID);
//
//        Long id = NumberUtils.toLong(quizID);
//        AdaptiveLearningQuiz adaptiveLearningQuiz = iAdaptiveLearningQuizService.findByID(id);
//        model.addAttribute(LessonsAdminAttributesE.AdaptiveLearningQuiz.toString(), adaptiveLearningQuiz);

        return ContentRootE.Student_Adaptive_Learning.getContentRootPagePath("view-adaptive-quiz");
    }
}
