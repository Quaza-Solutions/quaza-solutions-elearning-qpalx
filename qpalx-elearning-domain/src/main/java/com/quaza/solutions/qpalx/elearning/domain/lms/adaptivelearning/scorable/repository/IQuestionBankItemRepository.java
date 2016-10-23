package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by manyce400 on 10/22/16.
 */
public interface IQuestionBankItemRepository extends CrudRepository<QuestionBankItem, Long> {


    @Query("Select               count(*) From QuestionBankItem questionBankItem "+
            "INNER JOIN          questionBankItem.qPalXELesson qPalXELesson " +
            "Where               qPalXELesson =?1 "
    )
    public Long countQuestionBankItemByLesson(QPalXELesson qPalXELesson);

}
