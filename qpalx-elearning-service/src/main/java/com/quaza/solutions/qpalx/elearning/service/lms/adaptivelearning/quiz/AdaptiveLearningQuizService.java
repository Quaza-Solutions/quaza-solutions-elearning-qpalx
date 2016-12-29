package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.*;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository.IAdaptiveLearningQuizQuestionAnswerRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository.IAdaptiveLearningQuizQuestionRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository.IAdaptiveLearningQuizRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizService2")
public class AdaptiveLearningQuizService implements IAdaptiveLearningQuizService {



    @Autowired
    private IAdaptiveLearningQuizQuestionRepository iAdaptiveLearningQuizQuestionRepository;

    @Autowired
    private IAdaptiveLearningQuizQuestionAnswerRepository iAdaptiveLearningQuizQuestionAnswerRepository;

    @Autowired
    private IAdaptiveLearningQuizRepository iAdaptiveLearningQuizRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizService.class);


    @Override
    public AdaptiveLearningQuiz findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding AdaptiveLearningQuiz with id: {}", id);
        return iAdaptiveLearningQuizRepository.findOne(id);
    }

    @Override
    public void save(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");
        LOGGER.debug("Saving AdaptiveLearningQuiz: {}", adaptiveLearningQuiz);
        iAdaptiveLearningQuizRepository.save(adaptiveLearningQuiz);
    }

    @Override
    public void delete(AdaptiveLearningQuiz adaptiveLearningQuiz) {
        Assert.notNull(adaptiveLearningQuiz, "adaptiveLearningQuiz cannot be null");
        LOGGER.debug("Deleting AdaptiveLearningQuiz: {}", adaptiveLearningQuiz);
        iAdaptiveLearningQuizRepository.delete(adaptiveLearningQuiz);
    }

    @Override
    public List<AdaptiveLearningQuiz> findQuizzesForMicroLesson(QPalXEMicroLesson qPalXEMicroLesson) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        LOGGER.debug("Finding all AdaptiveLearningQuiz for MicroLesson: {}", qPalXEMicroLesson.getMicroLessonName());
        return iAdaptiveLearningQuizRepository.findQuizzesForMicroLesson(qPalXEMicroLesson.getId());
    }

    @Transactional
    @Override
    public void createAndSaveAdaptiveLearningQuiz(QPalXEMicroLesson qPalXEMicroLesson, IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        Assert.notNull(iAdaptiveLearningQuizVO, "iAdaptiveLearningQuizVO cannot be null");
        Assert.notNull(iAdaptiveLearningQuizVO.getIAdaptiveLearningQuizQuestionVOs(), "IAdaptiveLearningQuizQuestionVOs cannot be null");

        LOGGER.info("Creating and saving new AdaptiveLearningQuiz under microLesson: {}", qPalXEMicroLesson.getMicroLessonName());

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

        // First save the Adaptive LearningQuiz independently
        LOGGER.info("Saving all details for new AdaptiveLearningQuiz: {}", adaptiveLearningQuiz.getQuizTitle());
        iAdaptiveLearningQuizRepository.save(adaptiveLearningQuiz);

        // Now we can build and save all other entities as well
        saveAdaptiveLearningQuizQuestions(adaptiveLearningQuiz, iAdaptiveLearningQuizVO);
    }

    void saveAdaptiveLearningQuizQuestions(AdaptiveLearningQuiz adaptiveLearningQuiz, IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO) {
        Set<IAdaptiveLearningQuizQuestionVO> adaptiveLearningQuizQuestionVOS = iAdaptiveLearningQuizVO.getIAdaptiveLearningQuizQuestionVOs();

        for (IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO : adaptiveLearningQuizQuestionVOS) {
            AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion = AdaptiveLearningQuizQuestion.builder()
                    .questionTitle(iAdaptiveLearningQuizQuestionVO.getQuestionTitle())
                    .questionFeedBack(iAdaptiveLearningQuizQuestionVO.getQuestionFeedBack())
                    .quizQuestionAnswerMultiMedia(iAdaptiveLearningQuizQuestionVO.getQuizQuestionAnswerMultiMedia())
                    .adaptiveLearningQuizQuestionTypeE(iAdaptiveLearningQuizQuestionVO.getAdaptiveLearningQuizQuestionTypeE())
                    .entryDate(new DateTime())
                    .adaptiveLearningQuiz(adaptiveLearningQuiz)
                    .build();

            // Save the Quiz Question before building and saving all answers
            iAdaptiveLearningQuizQuestionRepository.save(adaptiveLearningQuizQuestion);

            // Add all question answers to question
            saveAdaptiveLearningQuizQuestionAnswers(adaptiveLearningQuizQuestion, iAdaptiveLearningQuizQuestionVO);
        }
    }

    void saveAdaptiveLearningQuizQuestionAnswers(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion, IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO) {
        LOGGER.debug("Adding all answers to quiz question: {}", adaptiveLearningQuizQuestion.getQuestionTitle());
        Set<IAdaptiveLearningQuizQuestionAnswerVO> adaptiveLearningQuizQuestionAnswerVOS = iAdaptiveLearningQuizQuestionVO.getIAdaptiveLearningQuizQuestionAnswerVOs();

        for(IAdaptiveLearningQuizQuestionAnswerVO iAdaptiveLearningQuizQuestionAnswerVO : adaptiveLearningQuizQuestionAnswerVOS) {
            AdaptiveLearningQuizQuestionAnswer adaptiveLearningQuizQuestionAnswer = AdaptiveLearningQuizQuestionAnswer.builder()
                    .quizQuestionAnswerText(iAdaptiveLearningQuizQuestionAnswerVO.getQuizQuestionAnswerText())
                    .quizQuestionAnswerMultiMedia(iAdaptiveLearningQuizQuestionAnswerVO.getQuizQuestionAnswerMultiMedia())
                    .isCorrectAnswer(iAdaptiveLearningQuizQuestionAnswerVO.isCorrectAnswer())
                    .entryDate(new DateTime())
                    .adaptiveLearningQuizQuestion(adaptiveLearningQuizQuestion)
                    .build();

            // Save all the Quiz Question Answers
            iAdaptiveLearningQuizQuestionAnswerRepository.save(adaptiveLearningQuizQuestionAnswer);

            // Add answer to the quiz question
            //adaptiveLearningQuizQuestion.addAdaptiveLearningQuizQuestionAnswer(adaptiveLearningQuizQuestionAnswer);
        }
    }
}
