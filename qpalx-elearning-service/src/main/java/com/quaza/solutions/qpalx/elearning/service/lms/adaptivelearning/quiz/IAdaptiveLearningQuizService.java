package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.*;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizService {


    public AdaptiveLearningQuiz findByID(Long id);

    public AdaptiveLearningQuizQuestion findByQuizQuestionID(Long id);

    public void saveQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public void saveQuizQuestion(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion);

    public void updateFrom(IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO);

    public void updateFrom(IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO);

    public void delete(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public void delete(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion);

    public void delete(AdaptiveLearningQuizQuestionAnswer adaptiveLearningQuizQuestionAnswer);

    public List<AdaptiveLearningQuiz> findQuizzesForMicroLesson(QPalXEMicroLesson qPalXEMicroLesson);

    public void moveAdaptiveLearningQuizDown(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public void moveAdaptiveLearningQuizUp(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public void saveAdaptiveLearningQuizDetails(QPalXEMicroLesson qPalXEMicroLesson, IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO, IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO, Integer questionOrder);

    public int getMaxQuestionOrder(AdaptiveLearningQuiz adaptiveLearningQuiz);

}
