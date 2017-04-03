package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.FactorAffectingProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * Implementation will provide definition of calcuation to generate a score based on their own internal models
 *
 * @author manyce400
 */
public interface IProficiencyRankingScoreModel {


    public FactorAffectingProficiencyRanking computeScore(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking adaptiveProficiencyRanking);

}
