package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.AdaptiveLearningQuizProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizProgressRepository extends CrudRepository<AdaptiveLearningQuizProgress, Long> {


    @Query("Select               adaptiveLearningQuizProgress From AdaptiveLearningQuizProgress adaptiveLearningQuizProgress "+
            "Where               adaptiveLearningQuizProgress.qPalxUserID =?1 "+
            "And                 adaptiveLearningQuizProgress.microLessonID =?2"
    )
    public List<AdaptiveLearningQuizProgress> findAdaptiveLearningQuizProgress(Long qpalxUserID, Long microLessonID);

}
