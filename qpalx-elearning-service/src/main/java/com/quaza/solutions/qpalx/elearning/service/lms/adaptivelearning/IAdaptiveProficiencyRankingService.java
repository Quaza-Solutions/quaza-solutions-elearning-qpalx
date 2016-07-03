package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.Set;

/**
 *
 * @author manyce400 
 */
public interface IAdaptiveProficiencyRankingService {


    /**
     * Build inital AdaptiveProficiencyRanking for a QPalXUser.  This is done as part of initial platform signup.
     *
     * @param qPalXUser
     * @param initialAdaptiveProficiencyRankingVOs
     */
    public AdaptiveProficiencyRanking buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs);
}
