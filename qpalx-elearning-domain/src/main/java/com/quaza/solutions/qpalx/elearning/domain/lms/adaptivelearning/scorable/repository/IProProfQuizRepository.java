package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.ProProfQuiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IProProfQuizRepository extends CrudRepository<ProProfQuiz, Long> {


    @Query("Select               proProfQuiz From ProProfQuiz proProfQuiz "+
            "Where               proProfQuiz.scorableActivityID =?1 "
    )
    public  ProProfQuiz findByScorableActivityID(Long scorableActivityID);
}
