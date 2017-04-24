package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuizQuestion;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.IAdaptiveLearningQuizQuestionVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.IAdaptiveLearningQuizVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizService {


    public AdaptiveLearningQuiz findByID(Long id);

    public void saveQuiz(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public void saveQuizQuestion(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion);

    public void updateFrom(IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO);

    public void updateFrom(IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO);

    public void delete(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public void delete(AdaptiveLearningQuizQuestion adaptiveLearningQuizQuestion);

    public List<AdaptiveLearningQuiz> findQuizzesForMicroLesson(QPalXEMicroLesson qPalXEMicroLesson);

    public void saveAdaptiveLearningQuizDetails(QPalXEMicroLesson qPalXEMicroLesson, IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO, IAdaptiveLearningQuizQuestionVO iAdaptiveLearningQuizQuestionVO, Integer questionOrder);

}
