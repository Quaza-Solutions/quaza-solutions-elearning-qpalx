package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.QPalXEMicroLessonProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IQPalXEMicroLessonProgressRepository extends CrudRepository<QPalXEMicroLessonProgress, Long> {


    @Query("Select               qPalXEMicroLessonProgress From QPalXEMicroLessonProgress qPalXEMicroLessonProgress "+
            "Where               qPalXEMicroLessonProgress.qPalxUserID =?1 "+
            "And                 qPalXEMicroLessonProgress.microLessonID =?2"
    )
    public QPalXEMicroLessonProgress findByUserAndMicroLessonID(Long qPalxUserID, Long microLessonID);

}
