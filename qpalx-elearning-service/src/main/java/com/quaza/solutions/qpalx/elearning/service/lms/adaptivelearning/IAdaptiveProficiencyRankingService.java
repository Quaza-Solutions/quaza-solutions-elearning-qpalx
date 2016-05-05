package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;

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
     * @param proficiencyRankingTriggerTypeE
     * @param initialAdaptiveProficiencyRankingVOs
     */
    public void createStudentAdaptiveProficiencyRankings(QPalXUser qPalXUser, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs);


    public void createStudentAdaptiveProficiencyRanking(QPalXUser qPalXUser, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE, ELearningCurriculum eLearningCurriculum, ProficiencyScoreRangeE proficiencyScoreRangeE);

    public void createStudentAdaptiveProficiencyRanking(QPalXUser qPalXUser, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE, ELearningCurriculum eLearningCurriculum, ProficiencyRankingScaleE proficiencyRankingScaleE);


    /**
     * Find the student user's current proficiency ranking for the given curriculum passed in as argument.
     *
     * @param qPalXUser
     * @param eLearningCurriculum
     * @return AdaptiveProficiencyRanking
     */
    public AdaptiveProficiencyRanking findCurrentStudentAdaptiveProficiencyRankingForCurriculum(final QPalXUser qPalXUser, final ELearningCurriculum eLearningCurriculum);
}
