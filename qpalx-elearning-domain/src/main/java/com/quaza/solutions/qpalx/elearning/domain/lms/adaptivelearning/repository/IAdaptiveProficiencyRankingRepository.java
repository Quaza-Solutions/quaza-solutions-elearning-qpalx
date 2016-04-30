package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IAdaptiveProficiencyRankingRepository extends CrudRepository<AdaptiveProficiencyRanking, Long> {


    @Query("Select               adaptiveProficiencyRanking From AdaptiveProficiencyRanking adaptiveProficiencyRanking "+
            "INNER JOIN FETCH    adaptiveProficiencyRanking.qpalxUser qpalxUser " +
            "INNER JOIN FETCH    adaptiveProficiencyRanking.eLearningCurriculum eLearningCurriculum " +
            "Where               qpalxUser =?1 " +
            "And                 eLearningCurriculum =?2"
    )
    public AdaptiveProficiencyRanking findStudentAdaptiveProficiencyRankingByCurriculum(final QPalXUser qPalXUser, final ELearningCurriculum eLearningCurriculum);
}
