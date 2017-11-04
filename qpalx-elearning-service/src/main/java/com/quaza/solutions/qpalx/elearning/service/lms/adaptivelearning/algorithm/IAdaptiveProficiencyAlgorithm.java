package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm.ProficiencyAlgorithmExecutionInfo;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;

/**
 * Defines an Algorithm
 *
 * @author manyce400
 */
public interface IAdaptiveProficiencyAlgorithm {


    public ProficiencyAlgorithmExecutionInfo executeAlgorithm(QPalXUser qPalXUser, ELearningCourse eLearningCourse, TutorialLevelCalendar selectedTutorialLevelCalendar);

    public ProficiencyAlgorithmExecutionInfo executeAlgorithm(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking);


}
