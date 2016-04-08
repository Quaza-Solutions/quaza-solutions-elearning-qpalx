package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.Set;

/**
 *
 * @author manyce400 
 */
public interface IAdaptiveProficiencyRankingService {


    /**
     * Build and save inital AdaptiveProficiencyRanking for a QPalXUser.  This is done as part of initial platform signup.
     *
     * @param qPalXUser
     * @param adaptiveLearningProfile
     * @param initialAdaptiveProficiencyRankingVOs
     */
    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, AdaptiveLearningProfile adaptiveLearningProfile, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs);
}
