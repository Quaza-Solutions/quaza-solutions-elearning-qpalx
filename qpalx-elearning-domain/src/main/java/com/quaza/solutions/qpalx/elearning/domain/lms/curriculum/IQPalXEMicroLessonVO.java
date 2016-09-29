package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

/**
 * Value object interface for QPalXEMicroLesson domain object.
 *
 * @author manyce400
 */
public interface IQPalXEMicroLessonVO {


    public String getMicroLessonName();

    public String getMicroLessonDescription();

    public Long getQPalXELessonID();

    public ELearningMediaContent getELearningMediaContent();

}
