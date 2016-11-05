package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.QuestionBankProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IQuestionBankProgressRepository extends CrudRepository<QuestionBankProgress, Long> {


    @Query("Select               questionBankProgress From QuestionBankProgress questionBankProgress "+
            "Where               questionBankProgress.questionBankItemID =?1 "+
            "And                 questionBankProgress.qPalxUserID =?2"
    )
    public QuestionBankProgress findByQuestionBankItemAndUser(Long questionBankItemID, Long qPalXUserID);

}
