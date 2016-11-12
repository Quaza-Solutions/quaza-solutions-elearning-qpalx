package com.quaza.solutions.qpalx.elearning.web.adaptivelearning.progress;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author manyce400
 */
@Controller
public class AdaptiveQuizScoreProgressController {



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveQuizScoreProgressController.class);


    @RequestMapping(value = "/track-microlesson-quiz", method = RequestMethod.GET)
    public void trackAdaptiveExternalQuizScore(@RequestParam("user_name") String userName, @RequestParam("total_marks") String totalMarks, @RequestParam("attempt_date") String attemptDate,
                                               @RequestParam("user_obtained_marks") String userObtainedMarks, @RequestParam("user_percent_marks") String userPercentMarks, @RequestParam("user_totalcorrect_answers") String userTotalCorrectAnswers,
                                               @RequestParam("user_totalwrong_answers") String userTotalWrongAnswers, @RequestParam("user_Id") String userId, @RequestParam("user_Email") String userEmail, @RequestParam("user_Address") String userAddress,
                                               @RequestParam("user_City") String userCity, @RequestParam("user_State") String userState, @RequestParam("user_Zipcode") String userZipcode, @RequestParam("user_Country") String userCountry, @RequestParam("user_Phone") String userPhone,
                                               @RequestParam("quiz_id") String quizId, @RequestParam("quiz_name") String quizName, @RequestParam("time_taken") String timeTaken, @RequestParam("user_total_unanswered") String userTotalUnanswered,
                                               @RequestParam("quiz_title") String quizTitle, @RequestParam("status") String status) {
        LOGGER.info("Recording new quiz score for userName: {} userPercentMarks: {}", userName, userPercentMarks);
    }

}
