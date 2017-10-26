package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.algorithm;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm.ProficiencyAlgorithmExecutionInfo;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IMultiplexAdaptiveProficiencyAlgorithm extends IAdaptiveProficiencyAlgorithm {


    public List<ProficiencyAlgorithmExecutionInfo> calculateAllAlgorithmScore(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking);

    public void addIAdaptiveProficiencyAlgorithm(IAdaptiveProficiencyAlgorithm iAdaptiveProficiencyAlgorithm);

    public void removeIAdaptiveProficiencyAlgorithm(IAdaptiveProficiencyAlgorithm iAdaptiveProficiencyAlgorithm);

    public default ProficiencyAlgorithmExecutionInfo calculateAlgorithmScore(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking) {
        throw new UnsupportedOperationException("Multiplex implementations of IAdaptiveProficiencyAlgorithm do not return a single ProficiencyAlgorithmExecutionInfo");
    }


}
