package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.FactorAffectingProficiencyRanking;

import java.util.List;

/**
 * @author manyce400
 */
public interface IFactorAffectingProficiencyRankingRepository { //extends CrudRepository<FactorAffectingProficiencyRanking, Long> {


//    @Query("Select               factorAffectingProficiencyRanking From FactorAffectingProficiencyRanking factorAffectingProficiencyRanking "+
//            "INNER JOIN FETCH    factorAffectingProficiencyRanking.adaptiveProficiencyRanking adaptiveProficiencyRanking " +
//            "Where               adaptiveProficiencyRanking =?1 "
//    )
    public List<FactorAffectingProficiencyRanking> findAllForAdaptiveProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking);


}
