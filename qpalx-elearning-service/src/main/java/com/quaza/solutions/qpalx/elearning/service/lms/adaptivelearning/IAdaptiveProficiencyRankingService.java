package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.Set;

/**
 *
 * @author manyce400 
 */
public interface IAdaptiveProficiencyRankingService {


    /**
     * Build's initial AdaptiveProficiencyRanking for a QPalXUser.  This is done as part of initial platform sign up.
     *
     * @param qPalXUser
     * @param initialAdaptiveProficiencyRankingVOs
     */
    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs);
}
