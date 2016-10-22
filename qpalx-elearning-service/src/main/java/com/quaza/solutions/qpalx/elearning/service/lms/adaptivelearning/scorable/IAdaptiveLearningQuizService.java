package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IAdaptiveLearningQuizVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizService {

    public AdaptiveLearningQuiz findByID(Long id);

    public AdaptiveLearningQuiz findByScorableActivityID(Long scorableActivityID);

    public List<AdaptiveLearningQuiz> findAllByQPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson);

    public void buildAndSaveAdaptiveLearningQuiz(IAdaptiveLearningQuizVO proProfQuizVO);

}