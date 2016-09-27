package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

/**
 * Value object interface for QPalXEMicroLessonActivity domain object.
 *
 * @author manyce400
 */
public interface IQPalXEMicroLessonActivityVO {

    public String getMicroLessonActivityName();

    public String getMicroLessonActivityDescription();

    public Long getQPalXEMicroLessonID();

    public ELearningMediaContent getELearningMediaContent();

    public String getMicroLessonActivityType();

    public MicroLessonActivityTypeE getMicroLessonActivityTypeE();

}
