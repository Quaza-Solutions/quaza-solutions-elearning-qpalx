package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import java.util.Set;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizVO {

    public String getQuizTitle();

    public String getQuizDescription();

    public Double getMaxPossibleActivityScore();

    public Double getMinimumPassingActivityScore();

    public Long getTimeToCompleteActivity();

    public boolean isActive();

    public Set<IAdaptiveLearningQuizQuestionVO> getIAdaptiveLearningQuizQuestionVOs();

}
