package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQuestionBankItemRepository extends CrudRepository<QuestionBankItem, Long> {


    @Query("Select               questionBankItem From QuestionBankItem questionBankItem "+
            "INNER JOIN          questionBankItem.qPalXELesson qPalXELesson " +
            "Where               qPalXELesson =?1 "
    )
    public List<QuestionBankItem> findAllQuestionBankItemByLesson(QPalXELesson qPalXELesson);

}
