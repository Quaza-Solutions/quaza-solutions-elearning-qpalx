package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;

/**
 * @author manyce400
 */
public interface IQuestionBankVO extends ILMSMediaContentVO {


    public String getQuestionTitle();

    public String getQuestionDescription();

    public Long getQPalXELessonID();

    public ELearningMediaContent getELearningMediaContent();
}
