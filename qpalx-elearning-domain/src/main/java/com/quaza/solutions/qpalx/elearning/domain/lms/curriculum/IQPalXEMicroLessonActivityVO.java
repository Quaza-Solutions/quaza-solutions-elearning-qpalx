package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;

/**
 * Value object interface for QPalXEMicroLessonActivity domain object.
 *
 * @author manyce400
 */
public interface IQPalXEMicroLessonActivityVO extends ILMSMediaContentVO {

    public String getMicroLessonActivityName();

    public String getMicroLessonActivityDescription();

    public Long getQPalXEMicroLessonID();

    public ELearningMediaContent getELearningMediaContent();

    public String getMicroLessonActivityType();

    public MicroLessonActivityTypeE getMicroLessonActivityTypeE();

}
