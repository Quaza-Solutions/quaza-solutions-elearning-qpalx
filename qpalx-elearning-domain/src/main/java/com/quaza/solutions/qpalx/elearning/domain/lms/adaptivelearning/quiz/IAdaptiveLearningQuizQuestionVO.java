package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;

import java.util.Set;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizQuestionVO extends ILMSMediaContentVO {

    public String getQuestionTitle();

    public String getQuestionFeedBack();

    public Long getID();

    public void setID(Long id);

    public AdaptiveLearningQuizQuestionTypeE getAdaptiveLearningQuizQuestionTypeE();

    public ELearningMediaContent getQuizQuestionMultiMedia();

    public Set<IAdaptiveLearningQuizQuestionAnswerVO> getIAdaptiveLearningQuizQuestionAnswerVOs();

}
