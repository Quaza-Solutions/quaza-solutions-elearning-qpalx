package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.QPalXEMicroLessonProgress;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXEMicroLessonProgressRepository extends CrudRepository<QPalXEMicroLessonProgress, Long> {


    @Query("Select               count(*) From QPalXEMicroLessonProgress qPalXEMicroLessonProgress "+
            "Where               qPalXEMicroLessonProgress.qPalxUserID =?1 "+
            "And                 qPalXEMicroLessonProgress.microLessonID =?2"
    )
    public Long countMicroLessonProgressByMicroLesson(Long qPalxUserID, Long microLessonID);

    @Query("Select               count(*) From QPalXEMicroLessonProgress qPalXEMicroLessonProgress "+
            "Where               qPalXEMicroLessonProgress.qPalxUserID =?1 "+
            "And                 qPalXEMicroLessonProgress.qPalxELessonID =?2"
    )
    public Long countMicroLessonProgressByLesson(Long qPalxUserID, Long qPalxELessonID);


    @Query("Select               qPalXEMicroLessonProgress From QPalXEMicroLessonProgress qPalXEMicroLessonProgress "+
            "Where               qPalXEMicroLessonProgress.qPalxUserID =?1 "+
            "And                 qPalXEMicroLessonProgress.qPalxELessonID =?2"
    )
    public List<QPalXEMicroLessonProgress> findMicroLessonProgress(Long qPalxUserID, Long qPalxELessonID);


}
