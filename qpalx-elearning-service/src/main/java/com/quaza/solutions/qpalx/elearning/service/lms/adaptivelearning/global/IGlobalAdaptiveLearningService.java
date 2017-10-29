package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.global;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * API for calculating and computing Student's global Adaptive Learning Performance and metrics as well as retrieve recommendations
 * from various scoring models on what the Student can do to improve their performance.
 *
 * @author manyce400
 */
public interface IGlobalAdaptiveLearningService {


    public void computeAndTrackGlobalCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum);

    public void computeAndTrackGlobalCurriculumProficiency(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE);

}
