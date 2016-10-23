package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizRepository extends CrudRepository<AdaptiveLearningQuiz, Long> {


    @Query("Select               count(*) From AdaptiveLearningQuiz adaptiveLearningQuiz "+
            "INNER JOIN          adaptiveLearningQuiz.qPalXEMicroLesson qPalXEMicroLesson " +
            "INNER JOIN          qPalXEMicroLesson.qPalXELesson qPalXELesson " +
            "Where               qPalXELesson =?1 "
    )
    public Long countAdaptiveLearningQuizByLesson(QPalXELesson qPalXELesson);


    @Query("Select               adaptiveLearningQuiz From AdaptiveLearningQuiz adaptiveLearningQuiz "+
            "Where               adaptiveLearningQuiz.scorableActivityID =?1 "
    )
    public AdaptiveLearningQuiz findByScorableActivityID(Long scorableActivityID);

    @Query("Select               adaptiveLearningQuiz From AdaptiveLearningQuiz adaptiveLearningQuiz "+
            "Where               adaptiveLearningQuiz.qPalXEMicroLesson =?1 "
    )
    public List<AdaptiveLearningQuiz> findByQPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson);
}
