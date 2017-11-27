package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.AdaptiveLearningQuizProgress;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IAdaptiveLearningQuizProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.*;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository.IAdaptiveLearningQuizQuestionAnswerRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository.IAdaptiveLearningQuizQuestionRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository.IAdaptiveLearningQuizRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.AdaptiveLearningExperienceService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningExperienceService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * @author manyce400
 */
@Service(AdaptiveLearningQuizService.BEAN_NAME)
public class AdaptiveLearningQuizService implements IAdaptiveLearningQuizService {



    @Autowired
    private IAdaptiveLearningQuizQuestionRepository iAdaptiveLearningQuizQuestionRepository;

    @Autowired
    private IAdaptiveLearningQuizQuestionAnswerRepository iAdaptiveLearningQuizQuestionAnswerRepository;

    @Autowired
    private IAdaptiveLearningQuizProgressRepository iAdaptiveLearningQuizProgressRepository;

    @Autowired
    private IAdaptiveLearningQuizRepository iAdaptiveLearningQuizRepository;

    @Autowired
    @Qualifier(AdaptiveLearningExperienceService.SPRING_BEAN_NAME)
    private IAdaptiveLearningExperienceService iAdaptiveLearningExperienceService;

    public static final String BEAN_NAME = "quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizService2";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizService.class);


    @Override
    public AdaptiveLearningQuiz findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding AdaptiveLearningQuiz with id: {}", id);
        return iAdaptiveLearningQuizRepository.findOne(id);
    }

    @Override
    public AdaptiveLearningQuizQuestion findByQuizQuestionID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding AdaptiveLearningQuizQuestion with id: {}", id);
        return iAdaptiveLearningQuizQuestionRepository.findOne(id);
    }

    @Transactional
    @Override
    public void saveQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");
        LOGGER.debug("Saving AdaptiveLearningQuiz: {}", adaptiveLearningQuiz);
        iAdaptiveLearningQuizRepository.save(adaptiveLearningQuiz);
    }

    @Transactional
    @Override
    public void saveQuizQuestion(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion) {
        Assert.notNull(adaptiveLearningQuizQuestion, "adaptiveLearningQuizQuestion cannot be null");
        LOGGER.debug("Saving adaptiveLearningQuizQuestion: {}", adaptiveLearningQuizQuestion);
        iAdaptiveLearningQuizQuestionRepository.save(adaptiveLearningQuizQuestion);
    }

    @Transactional
    @Override
    public void updateFrom(IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO) {
        Assert.notNull(iAdaptiveLearningQuizQuestionVO, "iAdaptiveLearningQuizQuestionVO cannot be null");
        Assert.notNull(iAdaptiveLearningQuizQuestionVO.getID(), "iAdaptiveLearningQuizQuestionVO id cannot be null");

        LOGGER.debug("Finding and updating Quiz Question with ID: {}", iAdaptiveLearningQuizQuestionVO.getID());

        // Find persisted question with VO ID update and save
        AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion = iAdaptiveLearningQuizQuestionRepository.findOne(iAdaptiveLearningQuizQuestionVO.getID());
        adaptiveLearningQuizQuestion.setQuestionTitle(iAdaptiveLearningQuizQuestionVO.getQuestionTitle());
        adaptiveLearningQuizQuestion.setQuestionFeedBack(iAdaptiveLearningQuizQuestionVO.getQuestionFeedBack());
        adaptiveLearningQuizQuestion.setModifyDate(DateTime.now());
        saveQuizQuestion(adaptiveLearningQuizQuestion);
    }

    @Transactional
    @Override
    public void updateFrom(IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO) {
        Assert.notNull(iAdaptiveLearningQuizVO, "iAdaptiveLearningQuizVO cannot be null");
        Assert.notNull(iAdaptiveLearningQuizVO.getID(), "iAdaptiveLearningQuizVO id cannot be null");

        AdaptiveLearningQuiz adaptiveLearningQuiz = iAdaptiveLearningQuizRepository.findOne(iAdaptiveLearningQuizVO.getID());
        adaptiveLearningQuiz.setQuizTitle(iAdaptiveLearningQuizVO.getQuizTitle());
        adaptiveLearningQuiz.setQuizDescription(iAdaptiveLearningQuizVO.getQuizDescription());
        adaptiveLearningQuiz.setModifyDate(DateTime.now());
        iAdaptiveLearningQuizRepository.save(adaptiveLearningQuiz);
    }

    @Transactional
    @Override
    public void delete(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");
        LOGGER.debug("Deleting AdaptiveLearningQuiz: {}", adaptiveLearningQuiz);

        // Delete all Quiz Learning Experiences if they exist
        iAdaptiveLearningExperienceService.deleteAllQuizLearningExperiences(adaptiveLearningQuiz.getId());

        // Delete all Progress information that students have made on this Quiz since it doesnt exist anymore
        List<AdaptiveLearningQuizProgress> adaptiveLearningQuizProgresses = iAdaptiveLearningQuizProgressRepository.findAllAdaptiveLearningQuizProgressInfo(adaptiveLearningQuiz.getId());
        if(adaptiveLearningQuizProgresses != null && adaptiveLearningQuizProgresses.size() > 0) {
            iAdaptiveLearningQuizProgressRepository.delete(adaptiveLearningQuizProgresses);
        }

        // Get all Quiz Questions and delete them
        Set<AdaptiveLearningQuizQuestion> adaptiveLearningQuizQuestions = adaptiveLearningQuiz.getAdaptiveLearningQuizQuestions();
        for(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion : adaptiveLearningQuizQuestions) {
            delete(adaptiveLearningQuizQuestion);
        }

        // It's now safe to delete the quiz
        iAdaptiveLearningQuizRepository.delete(adaptiveLearningQuiz);
    }

    @Transactional
    @Override
    public void delete(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion) {
        Assert.notNull(adaptiveLearningQuizQuestion, "adaptiveLearningQuizQuestion");
        LOGGER.debug("Deleting adaptiveLearningQuizQuestion: {}", adaptiveLearningQuizQuestion);

        // Delete all Question answers first
        if (adaptiveLearningQuizQuestion.getAdaptiveLearningQuizQuestionAnswers().size() > 0) {
            LOGGER.debug("First deleting all quiz question answers.....");
            iAdaptiveLearningQuizQuestionAnswerRepository.delete(adaptiveLearningQuizQuestion.getAdaptiveLearningQuizQuestionAnswers());
        }

        // Safe to delete Quiz Question now
        iAdaptiveLearningQuizQuestionRepository.delete(adaptiveLearningQuizQuestion);
    }

    @Transactional
    @Override
    public void delete(AdaptiveLearningQuizQuestionAnswer adaptiveLearningQuizQuestionAnswer) {
        Assert.notNull(adaptiveLearningQuizQuestionAnswer, "adaptiveLearningQuizQuestionAnswer cannot be null");
        LOGGER.debug("Deleting adaptiveLearningQuizQuestionAnswer: {}", adaptiveLearningQuizQuestionAnswer);
        iAdaptiveLearningQuizQuestionAnswerRepository.delete(adaptiveLearningQuizQuestionAnswer);
    }

    @Override
    public List<AdaptiveLearningQuiz> findQuizzesForMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        LOGGER.debug("Finding all AdaptiveLearningQuiz for MicroLesson: {}", qPalXEMicroLesson.getMicroLessonName());
        return iAdaptiveLearningQuizRepository.findQuizzesForMicroLesson(qPalXEMicroLesson.getId());
    }


    @Transactional
    @Override
    public void saveAdaptiveLearningQuizDetails(QPalXEMicroLesson qPalXEMicroLesson, IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO, IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO, Integer questionOrder) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        Assert.notNull(iAdaptiveLearningQuizVO, "iAdaptiveLearningQuizVO cannot be null");
        Assert.notNull(iAdaptiveLearningQuizQuestionVO, "iAdaptiveLearningQuizQuestionVO cannot be null");
        Assert.notNull(questionOrder, "questionOrder cannot be null");

        if(iAdaptiveLearningQuizVO.getID() == null) {
            LOGGER.info("Creating and saving new AdaptiveLearningQuiz with Title: {} Under microLesson: {}", iAdaptiveLearningQuizVO.getQuizTitle(), qPalXEMicroLesson.getMicroLessonName());

            // Build AdaptiveLearningQuiz from VO
            AdaptiveLearningQuiz adaptiveLearningQuiz = AdaptiveLearningQuiz.builder()
                    .quizTitle(iAdaptiveLearningQuizVO.getQuizTitle())
                    .quizDescription(iAdaptiveLearningQuizVO.getQuizDescription())
                    .maxPossibleActivityScore(iAdaptiveLearningQuizVO.getMaxPossibleActivityScore())
                    .minimumPassingActivityScore(iAdaptiveLearningQuizVO.getMinimumPassingActivityScore())
                    .timeToCompleteActivity(iAdaptiveLearningQuizVO.getTimeToCompleteActivity())
                    .qPalXEMicroLesson(qPalXEMicroLesson)
                    .entryDate(new DateTime())
                    .active(iAdaptiveLearningQuizVO.isActive())
                    .build();

            saveQuiz(adaptiveLearningQuiz);
            iAdaptiveLearningQuizVO.setID(adaptiveLearningQuiz.getId());
            persistAdaptiveLearningQuizQuestionDetails(adaptiveLearningQuiz, iAdaptiveLearningQuizQuestionVO, questionOrder);
        } else {
            LOGGER.info("Persisted from VO: {}", iAdaptiveLearningQuizVO);
            AdaptiveLearningQuiz savedAdaptiveLearningQuiz = iAdaptiveLearningQuizRepository.findOne(iAdaptiveLearningQuizVO.getID());
            LOGGER.info("Updating previously saved Adaptive Learning Quiz with ID: {} savedAdaptiveLearningQuiz: {}", iAdaptiveLearningQuizVO.getID(), savedAdaptiveLearningQuiz);
            persistAdaptiveLearningQuizQuestionDetails(savedAdaptiveLearningQuiz, iAdaptiveLearningQuizQuestionVO, questionOrder);
        }
    }

    @Override
    public int getMaxQuestionOrder(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");
        LOGGER.debug("Finding max question order across all questions on quiz with ID: {}", adaptiveLearningQuiz.getId());

        // By default all questions should naturally be sorted by Order in asc mode however loop through to be safe
        Set<AdaptiveLearningQuizQuestion> quizQuestions = adaptiveLearningQuiz.getAdaptiveLearningQuizQuestions();
        int maxOrder = 0;

        for (AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion : quizQuestions) {
            int questionOrder = adaptiveLearningQuizQuestion.getQuestionOrder();
            if(questionOrder > maxOrder) {
                maxOrder = questionOrder;
            }
        }

        return maxOrder;
    }

    void persistAdaptiveLearningQuizQuestionDetails(AdaptiveLearningQuiz adaptiveLearningQuiz, IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO, Integer questionOrder) {
        LOGGER.debug("Saving and persisting Question: {} for Quiz: {}", iAdaptiveLearningQuizQuestionVO.getQuestionTitle(), adaptiveLearningQuiz.getQuizTitle());

        if (iAdaptiveLearningQuizQuestionVO.getID() == null) {
            LOGGER.debug("Saving and persisting new Adaptive Quiz details....");

            AdaptiveLearningQuizQuestion persistedAdaptiveLearningQuizQuestion = AdaptiveLearningQuizQuestion.builder()
                    .questionOrder(questionOrder)
                    .questionTitle(iAdaptiveLearningQuizQuestionVO.getQuestionTitle())
                    .questionFeedBack(iAdaptiveLearningQuizQuestionVO.getQuestionFeedBack())
                    .quizQuestionMultiMedia(iAdaptiveLearningQuizQuestionVO.getQuizQuestionMultiMedia())
                    .adaptiveLearningQuizQuestionTypeE(iAdaptiveLearningQuizQuestionVO.getAdaptiveLearningQuizQuestionTypeE())
                    .entryDate(new DateTime())
                    .adaptiveLearningQuiz(adaptiveLearningQuiz)
                    .build();

            saveQuizQuestion(persistedAdaptiveLearningQuizQuestion);
            iAdaptiveLearningQuizQuestionVO.setID(persistedAdaptiveLearningQuizQuestion.getId());
            persistAndSaveNewQuestionAnswers(persistedAdaptiveLearningQuizQuestion, iAdaptiveLearningQuizQuestionVO);
        } else {
            LOGGER.info("Updating and persisting existing Adaptive Quiz details....");
            AdaptiveLearningQuizQuestion persistedAdaptiveLearningQuizQuestion = iAdaptiveLearningQuizQuestionRepository.findOne(iAdaptiveLearningQuizQuestionVO.getID());
            persistedAdaptiveLearningQuizQuestion.setQuestionTitle(iAdaptiveLearningQuizQuestionVO.getQuestionTitle());
            persistedAdaptiveLearningQuizQuestion.setQuestionFeedBack(iAdaptiveLearningQuizQuestionVO.getQuestionFeedBack());
            persistedAdaptiveLearningQuizQuestion.setModifyDate(DateTime.now());
            saveQuizQuestion(persistedAdaptiveLearningQuizQuestion);
            updateAndSaveExistingQuestionAnswers(persistedAdaptiveLearningQuizQuestion, iAdaptiveLearningQuizQuestionVO);
        }
    }

    private void persistAndSaveNewQuestionAnswers(AdaptiveLearningQuizQuestion persistedAdaptiveLearningQuizQuestion, IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO) {
        Set<IAdaptiveLearningQuizQuestionAnswerVO> adaptiveLearningQuizQuestionAnswerVOS = iAdaptiveLearningQuizQuestionVO.getIAdaptiveLearningQuizQuestionAnswerVOs();

        int questionAnswerOrder = 1;
        for(IAdaptiveLearningQuizQuestionAnswerVO iAdaptiveLearningQuizQuestionAnswerVO : adaptiveLearningQuizQuestionAnswerVOS) {
            LOGGER.info("Building and saving new Quiz Question Answer: {} For Quiz: {}", iAdaptiveLearningQuizQuestionAnswerVO.getQuizQuestionAnswerText(), persistedAdaptiveLearningQuizQuestion.getQuestionTitle());
            AdaptiveLearningQuizQuestionAnswer adaptiveLearningQuizQuestionAnswer = AdaptiveLearningQuizQuestionAnswer.builder()
                    .questionAnswerOrder(questionAnswerOrder)
                    .quizQuestionAnswerText(iAdaptiveLearningQuizQuestionAnswerVO.getQuizQuestionAnswerText())
                    .quizQuestionAnswerMultiMedia(iAdaptiveLearningQuizQuestionAnswerVO.getQuizQuestionAnswerMultiMedia())
                    .isCorrectAnswer(iAdaptiveLearningQuizQuestionAnswerVO.isCorrectAnswer())
                    .entryDate(new DateTime())
                    .adaptiveLearningQuizQuestion(persistedAdaptiveLearningQuizQuestion)
                    .build();

            questionAnswerOrder++;

            // Save all the Quiz Question Answers and update the VO object with persisted ID
            iAdaptiveLearningQuizQuestionAnswerRepository.save(adaptiveLearningQuizQuestionAnswer);
        }
    }

    private void updateAndSaveExistingQuestionAnswers(AdaptiveLearningQuizQuestion persistedAdaptiveLearningQuizQuestion, IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO) {
        Set<IAdaptiveLearningQuizQuestionAnswerVO> adaptiveLearningQuizQuestionAnswerVOS = iAdaptiveLearningQuizQuestionVO.getIAdaptiveLearningQuizQuestionAnswerVOs();

        // Convert the Question Answer value objects to an Array, we will use this to update the already persisted question answers
        IAdaptiveLearningQuizQuestionAnswerVO[] quizQuestionAnswersArray = adaptiveLearningQuizQuestionAnswerVOS.toArray(new IAdaptiveLearningQuizQuestionAnswerVO[adaptiveLearningQuizQuestionAnswerVOS.size()]);
        LOGGER.info("Quiz Question Answers array: {}", quizQuestionAnswersArray);

        // Get all the already persisted Question Answers that will need to be updated
        Set<AdaptiveLearningQuizQuestionAnswer> persistedQuizQuestionAnswers = persistedAdaptiveLearningQuizQuestion.getAdaptiveLearningQuizQuestionAnswers();

        int valueObjectIndex = 0;
        for(AdaptiveLearningQuizQuestionAnswer quizQuestionAnswerToUpdate : persistedQuizQuestionAnswers) {

            IAdaptiveLearningQuizQuestionAnswerVO quizQuestionAnswer = quizQuestionAnswersArray[valueObjectIndex];

            AdaptiveLearningQuizQuestionAnswer savedQuestionAnswer = iAdaptiveLearningQuizQuestionAnswerRepository.findOne(quizQuestionAnswerToUpdate.getId());
            LOGGER.info("Updating Quiz Question Answer with ID: {}", savedQuestionAnswer.getId());
            savedQuestionAnswer.setQuizQuestionAnswerText(quizQuestionAnswer.getQuizQuestionAnswerText());
            savedQuestionAnswer.setQuizQuestionAnswerMultiMedia(quizQuestionAnswer.getQuizQuestionAnswerMultiMedia());
            savedQuestionAnswer.setCorrectAnswer(quizQuestionAnswer.isCorrectAnswer());
            iAdaptiveLearningQuizQuestionAnswerRepository.save(savedQuestionAnswer);

            valueObjectIndex++;
        }
    }


}
