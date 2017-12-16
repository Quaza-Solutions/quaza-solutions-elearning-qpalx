package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXEMicroLessonRepository extends CrudRepository<QPalXEMicroLesson, Long> {


    @Query("Select               count(*) From QPalXEMicroLesson qPalXEMicroLesson "+
            "INNER JOIN          qPalXEMicroLesson.qPalXELesson qPalXELesson " +
            "Where               qPalXELesson =?1 "
    )
    public Long countQPalXEMicroLessonByLesson(QPalXELesson qPalXELesson);


    @Query("Select               qPalXEMicroLesson From QPalXEMicroLesson qPalXEMicroLesson "+
            "INNER JOIN FETCH    qPalXEMicroLesson.qPalXELesson qPalXELesson " +
            "Where               qPalXELesson =?1 " +
            "Order By            qPalXEMicroLesson.elementOrder ASC"
    )
    public List<QPalXEMicroLesson> findAllQPalxMicroLessons(QPalXELesson qPalXELesson);

}
