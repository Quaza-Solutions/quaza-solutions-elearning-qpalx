package com.quaza.solutions.qpalx.elearning.domain.lms.assessment;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.IAdaptiveLearningQuizVO;

/**
 * @author manyce400
 */
public interface ICourseAssessmentFocusAreaVO extends IAdaptiveLearningQuizVO {

    public Long getCourseAssessmentFocusAreaID();

    public Long getELearningCourseID();

}