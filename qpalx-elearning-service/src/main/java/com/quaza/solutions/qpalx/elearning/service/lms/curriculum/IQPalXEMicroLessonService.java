package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXEMicroLessonService {



    public QPalXEMicroLesson findByID(Long id);

    public List<QPalXEMicroLesson> findQPalXEMicroLessons(QPalXELesson qPalXELesson);

}
