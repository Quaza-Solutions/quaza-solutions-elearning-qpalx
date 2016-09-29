package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

import java.util.List;
import java.util.Set;

/**
 * @author manyce400 
 */
public interface IAdaptiveProficiencyRankingService {


    /**
     * Loads and returns all the currently valid and active proficiency rankings for the given student.
     *
     * @param qPalXUser
     * @return
     */
    public List<AdaptiveProficiencyRanking> findStudentAdaptiveProficiencyRankings(QPalXUser qPalXUser);


    /**
     * Build's initial AdaptiveProficiencyRanking for a QPalXUser.  This is done as part of initial platform sign up.
     *
     * @param qPalXUser
     * @param studentTutorialGrade
     * @param initialAdaptiveProficiencyRankingVOs
     */
    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs);
}
