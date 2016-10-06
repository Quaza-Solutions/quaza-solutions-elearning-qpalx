package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;

/**
 * Value object interface for QPalXEMicroLesson domain object.
 *
 * @author manyce400
 */
public interface IQPalXEMicroLessonVO extends ILMSMediaContentVO {


    public String getMicroLessonName();

    public String getMicroLessonDescription();

    public Long getQPalXELessonID();

    public ELearningMediaContent getELearningMediaContent();

}
