package com.quaza.solutions.qpalx.elearning.web.adaptivelearning.progress;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz.IAdaptiveLearningQuizStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author manyce400
 */
@RestController
public class AdaptiveQuizScoreProgressController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StatisticsExecutorService")
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningExperienceService")
    private IAdaptiveLearningExperienceService iAdaptiveLearningExperienceService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizStatisticsService")
    private IAdaptiveLearningQuizStatisticsService iAdaptiveLearningQuizStatisticsService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveQuizScoreProgressController.class);


    @RequestMapping(value = "/track-microlesson-quiz", method = RequestMethod.GET)
    public void trackAdaptiveExternalQuizScore(@RequestParam("user_name") String userName, @RequestParam("total_marks") String totalMarks, @RequestParam("attempt_date") String attemptDate,
                                               @RequestParam("user_obtained_marks") String userObtainedMarks, @RequestParam("user_percent_marks") String userPercentMarks, @RequestParam("user_totalcorrect_answers") String userTotalCorrectAnswers,
                                               @RequestParam("user_totalwrong_answers") String userTotalWrongAnswers, @RequestParam("user_Id") String userId, @RequestParam("user_Email") String userEmail, @RequestParam("user_Address") String userAddress,
                                               @RequestParam("user_City") String userCity, @RequestParam("user_State") String userState, @RequestParam("user_Zipcode") String userZipcode, @RequestParam("user_Country") String userCountry, @RequestParam("user_Phone") String userPhone,
                                               @RequestParam("quiz_id") String quizId, @RequestParam("quiz_name") String quizName, @RequestParam("time_taken") String timeTaken, @RequestParam("user_total_unanswered") String userTotalUnanswered,
                                               @RequestParam("quiz_title") String quizTitle, @RequestParam("status") String status) {
        LOGGER.info("Recording new quiz score for userName: {} userPercentMarks: {}", userName, userPercentMarks);

        // Look up the user that just took this quiz mapping back using User Success ID.
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserBySuccessID(userName);
        LOGGER.info("Found qPalXUser with userName: {}", userName);

        if(qPalXUser != null) {
            Runnable quizScoreCaptureTask = () -> {
                LOGGER.info("Running capture of quiz score task....");

                // Record the AdaptiveLearning Experience
                QPalXTutorialContentTypeE qPalXTutorialContentTypeE = QPalXTutorialContentTypeE.Quiz;
                Double proficiencyScore = Double.valueOf(userPercentMarks);
                Long scoreableActivityID = Long.valueOf(quizId);
                iAdaptiveLearningExperienceService.buildAndSaveAdaptiveLearningExperience(qPalXUser, qPalXTutorialContentTypeE, proficiencyScore, scoreableActivityID);

                // Record AdaptiveLearning Quiz progress statistics
                iAdaptiveLearningQuizStatisticsService.recordAdaptiveLearningQuizProgress(scoreableActivityID, qPalXUser);
            };

            listeningExecutorService.submit(quizScoreCaptureTask);
        }
    }


    @RequestMapping(value = "/test-quiz-save", method = RequestMethod.GET)
    public void testRecordQuiz(@RequestParam("user_name") String userName, @RequestParam("user_percent_marks") String userPercentMarks, @RequestParam("quiz_id") String quizId) {
        LOGGER.info("Recording new quiz score for userName: {} userPercentMarks: {}", userName, userPercentMarks);

        // Look up the user that just took this quiz mapping back using User Success ID.
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserBySuccessID(userName);
        LOGGER.info("Found qPalXUser with userName: {}", qPalXUser.getEmail());

        if(qPalXUser != null) {
            Runnable quizScoreCaptureTask = () -> {
                LOGGER.info("Running capture of quiz score task....");

                // Record the AdaptiveLearning Experience
                QPalXTutorialContentTypeE qPalXTutorialContentTypeE = QPalXTutorialContentTypeE.Quiz;
                Double proficiencyScore = Double.valueOf(userPercentMarks);
                Long scoreableActivityID = Long.valueOf(quizId);
                //iAdaptiveLearningExperienceService.buildAndSaveAdaptiveLearningExperience(qPalXUser, qPalXTutorialContentTypeE, proficiencyScore, scoreableActivityID);

                // Record AdaptiveLearning Quiz progress statistics
                //iAdaptiveLearningQuizStatisticsService.recordAdaptiveLearningQuizProgress(scoreableActivityID, qPalXUser);
            };

            listeningExecutorService.submit(quizScoreCaptureTask);
        }
    }

}
