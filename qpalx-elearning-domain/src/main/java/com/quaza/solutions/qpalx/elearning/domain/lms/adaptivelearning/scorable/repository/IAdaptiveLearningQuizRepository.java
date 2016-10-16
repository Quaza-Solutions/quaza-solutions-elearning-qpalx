package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizRepository extends CrudRepository<AdaptiveLearningQuiz, Long> {


    @Query("Select               proProfQuiz From AdaptiveLearningQuiz proProfQuiz "+
            "Where               proProfQuiz.scorableActivityID =?1 "
    )
    public AdaptiveLearningQuiz findByScorableActivityID(Long scorableActivityID);

    @Query("Select               proProfQuiz From AdaptiveLearningQuiz proProfQuiz "+
            "Where               proProfQuiz.qPalXEMicroLesson =?1 "
    )
    public List<AdaptiveLearningQuiz> findByQPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson);
}
