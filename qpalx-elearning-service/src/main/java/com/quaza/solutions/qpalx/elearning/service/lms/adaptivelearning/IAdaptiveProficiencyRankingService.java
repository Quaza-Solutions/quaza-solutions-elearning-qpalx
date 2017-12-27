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

    public List<AdaptiveProficiencyRanking> findStudentAdaptiveProficiencyRankings(QPalXUser qPalXUser);

    public List<AdaptiveProficiencyRanking> findBelowAverageAdaptiveProficiencyRankings(QPalXUser qPalXUser);

    public AdaptiveProficiencyRanking findCurrentStudentAdaptiveProficiencyRankingForCurriculum(final QPalXUser qPalXUser, final ELearningCurriculum eLearningCurriculum);

    public AdaptiveProficiencyRanking buildAdaptiveProficiencyRanking(double proficiencyScore, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE);

    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs);

    public void buildAndSaveProficiencyRankingForCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, SimplifiedProficiencyRankE simplifiedProficiencyRankE);
}
