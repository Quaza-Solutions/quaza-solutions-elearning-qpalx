package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizRepository extends CrudRepository<AdaptiveLearningQuiz, Long> {


    @Query("Select               adaptiveLearningQuiz From AdaptiveLearningQuiz adaptiveLearningQuiz "+
            "Where               adaptiveLearningQuiz.qPalXEMicroLesson.id =?1 " +
            "Order By            adaptiveLearningQuiz.elementOrder ASC"
    )
    public List<AdaptiveLearningQuiz> findQuizzesForMicroLesson(Long microLessonID);

}
