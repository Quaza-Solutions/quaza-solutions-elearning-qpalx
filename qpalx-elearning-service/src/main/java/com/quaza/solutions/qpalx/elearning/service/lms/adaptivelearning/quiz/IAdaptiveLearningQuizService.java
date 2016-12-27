package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.IAdaptiveLearningQuizVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizService {


    public AdaptiveLearningQuiz findByID(Long id);

    public void save(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public void delete(AdaptiveLearningQuiz adaptiveLearningQuiz);

    public List<AdaptiveLearningQuiz> findQuizzesForMicroLesson(QPalXEMicroLesson qPalXEMicroLesson);

    public void createAndSaveAdaptiveLearningQuiz(QPalXEMicroLesson qPalXEMicroLesson, IAdaptiveLearningQuizVO iAdaptiveLearningQuizVO);

}
