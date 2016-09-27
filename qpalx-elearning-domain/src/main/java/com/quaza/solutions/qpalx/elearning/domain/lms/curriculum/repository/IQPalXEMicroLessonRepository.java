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


    @Query("Select              qPalXEMicroLesson From QPalXEMicroLesson qPalXEMicroLesson "+
            "INNER JOIN FETCH    qPalXEMicroLesson.qPalXELesson qPalXELesson " +
            "Where               qPalXELesson =?1 "
    )
    public List<QPalXEMicroLesson> findAllQPalxMicroLessons(QPalXELesson qPalXELesson);

}
