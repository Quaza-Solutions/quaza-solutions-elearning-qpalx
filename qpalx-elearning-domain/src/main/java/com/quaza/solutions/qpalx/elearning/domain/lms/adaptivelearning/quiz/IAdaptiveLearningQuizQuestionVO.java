package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;

import java.util.Set;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizQuestionVO {

    public String getQuestionTitle();

    public String getQuestionFeedBack();

    public AdaptiveLearningQuizQuestionTypeE getAdaptiveLearningQuizQuestionTypeE();

    public ELearningMediaContent getQuizQuestionAnswerMultiMedia();

    public Set<IAdaptiveLearningQuizQuestionAnswerVO> getIAdaptiveLearningQuizQuestionAnswerVOs();

}
