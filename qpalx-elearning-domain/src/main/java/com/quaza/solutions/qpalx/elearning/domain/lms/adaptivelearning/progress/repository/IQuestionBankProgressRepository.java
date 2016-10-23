package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.QuestionBankProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQuestionBankProgressRepository extends CrudRepository<QuestionBankProgress, Long> {


    @Query("Select               count(*) From QuestionBankProgress questionBankProgress "+
            "Where               questionBankProgress.qPalxUserID =?1 "+
            "And                 questionBankProgress.qPalxELessonID =?2"
    )
    public Long countQuestionBankProgressByLesson(Long qPalxUserID, Long qPalxELessonID);


    @Query("Select               questionBankProgress From QuestionBankProgress questionBankProgress "+
            "Where               questionBankProgress.qPalxUserID =?1 "+
            "And                 questionBankProgress.qPalxELessonID =?2"
    )
    public List<QuestionBankProgress> findQuestionBankProgress(Long qPalxUserID, Long qPalxELessonID);

}
