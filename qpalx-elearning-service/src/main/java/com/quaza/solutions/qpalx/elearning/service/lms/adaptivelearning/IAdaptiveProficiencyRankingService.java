package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.SimplifiedProficiencyRankE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

import java.util.List;
import java.util.Set;

/**
 * @author manyce400 
 */
public interface IAdaptiveProficiencyRankingService {


    public void save(AdaptiveProficiencyRanking adaptiveProficiencyRanking);

    public void defaultToLowestProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking);

    public double getAdaptiveProficiencyRankingMinScore(AdaptiveProficiencyRanking adaptiveProficiencyRanking);


    /**
     * Loads and returns all the currently valid and active proficiency rankings for the given student.
     *
     * @param qPalXUser
     * @return
     */
    public List<AdaptiveProficiencyRanking> findStudentAdaptiveProficiencyRankings(QPalXUser qPalXUser);

    public AdaptiveProficiencyRanking findCurrentStudentAdaptiveProficiencyRankingForCurriculum(final QPalXUser qPalXUser, final ELearningCurriculum eLearningCurriculum);

    public AdaptiveProficiencyRanking buildAdaptiveProficiencyRanking(double proficiencyScore, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE);


    /**
     * Build's initial AdaptiveProficiencyRanking for a QPalXUser.  This is done as part of initial platform sign up.
     *
     * @param qPalXUser
     * @param studentTutorialGrade
     * @param initialAdaptiveProficiencyRankingVOs
     */
    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs);

    // Build and save AdaptiveProficiencyRanking for an ELearningCurriculum
    public void buildAndSaveProficiencyRankingForCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, SimplifiedProficiencyRankE simplifiedProficiencyRankE);
}
