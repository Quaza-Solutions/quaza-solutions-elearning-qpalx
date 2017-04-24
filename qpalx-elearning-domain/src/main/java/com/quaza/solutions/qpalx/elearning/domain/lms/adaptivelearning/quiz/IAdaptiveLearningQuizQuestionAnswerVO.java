package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizQuestionAnswerVO {


    public Long getID();

    public void setID(Long id);

    public String getQuizQuestionAnswerText();

    public ELearningMediaContent getQuizQuestionAnswerMultiMedia();

    public boolean isCorrectAnswer();

}
