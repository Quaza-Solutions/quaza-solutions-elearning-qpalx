package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;

import java.util.List;

/**
 * @author manyce400
 */
public interface IEditableAdaptiveLearningQuizQuestionVO extends ILMSMediaContentVO {

    public Long getID();

    public void setID(Long id);

    public String getQuestionTitle();

    public String getQuestionFeedBack();

    public Integer getCorrectAnswerIndex();

    public AdaptiveLearningQuizQuestionTypeE getAdaptiveLearningQuizQuestionTypeE();

    public ELearningMediaContent getQuizQuestionMultiMedia();

    public void setQuizQuestionAnswerMultiMedia(ELearningMediaContent quizQuestionMultiMedia);

    public List<IAdaptiveLearningQuizQuestionAnswerVO> getQuestionAnswersWithEdits();

    public boolean isQuizEditValid();

}
