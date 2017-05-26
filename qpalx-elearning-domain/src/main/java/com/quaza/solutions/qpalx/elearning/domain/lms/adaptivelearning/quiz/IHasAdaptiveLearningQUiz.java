package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz;

import com.quaza.solutions.qpalx.elearning.domain.lms.content.hierarchy.IHierarchicalLMSContent;

/**
 * @author manyce400
 */
public interface IHasAdaptiveLearningQUiz extends IHierarchicalLMSContent {

    public AdaptiveLearningQuiz getAdaptiveLearningQuiz();

}
