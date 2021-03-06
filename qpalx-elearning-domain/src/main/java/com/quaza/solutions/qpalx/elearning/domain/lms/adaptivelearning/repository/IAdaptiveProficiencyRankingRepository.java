package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveProficiencyRankingRepository extends CrudRepository<AdaptiveProficiencyRanking, Long> {


    /**
     * Find and return the list of Student user's currently active proficiency rankings.
     *
     * @param qPalXUser
     * @return List<AdaptiveProficiencyRanking>
     */
    @Query("Select               adaptiveProficiencyRanking From AdaptiveProficiencyRanking adaptiveProficiencyRanking "+
            "INNER JOIN FETCH    adaptiveProficiencyRanking.qpalxUser qpalxUser " +
            "Where               qpalxUser =?1 " +
            "And                 adaptiveProficiencyRanking.proficiencyRankingEffectiveDateTime is not null " +
            "And                 adaptiveProficiencyRanking.proficiencyRankingEndDateTime is null"
    )
    public List<AdaptiveProficiencyRanking> findStudentAdaptiveProficiencyRankings(final QPalXUser qPalXUser);

    /**
     * Find the current Student's proficiency ranking for the given ELearningCurriculum as of right now.
     *
     * @param qPalXUser
     * @param eLearningCurriculum
     * @return
     */
    @Query("Select               adaptiveProficiencyRanking From AdaptiveProficiencyRanking adaptiveProficiencyRanking "+
            "INNER JOIN FETCH    adaptiveProficiencyRanking.qpalxUser qpalxUser " +
            "INNER JOIN FETCH    adaptiveProficiencyRanking.eLearningCurriculum eLearningCurriculum " +
            "Where               qpalxUser =?1 " +
            "And                 eLearningCurriculum =?2 " +
            "And                 adaptiveProficiencyRanking.proficiencyRankingEffectiveDateTime is not null " +
            "And                 adaptiveProficiencyRanking.proficiencyRankingEndDateTime is null"
    )
    public AdaptiveProficiencyRanking findCurrentStudentAdaptiveProficiencyRankingForCurriculum(final QPalXUser qPalXUser, final ELearningCurriculum eLearningCurriculum);
}
