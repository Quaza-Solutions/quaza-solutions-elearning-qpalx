package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.FactorAffectingProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface ICummulativeProficiencyRankingService {

    public List<FactorAffectingProficiencyRanking> computeAndSaveStudentAdaptiveProficiencyRankingInCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum);

}
