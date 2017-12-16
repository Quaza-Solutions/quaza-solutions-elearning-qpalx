package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXEMicroLessonService {



    public QPalXEMicroLesson findByID(Long id);

    public List<QPalXEMicroLesson> findQPalXEMicroLessons(QPalXELesson qPalXELesson);

    public void createAndSaveQPalXEMicroLesson(IQPalXEMicroLessonVO iqPalXEMicroLessonVO);

    public void updateAndSaveQPalXEMicroLesson(QPalXEMicroLesson qPalXEMicroLesson, IQPalXEMicroLessonVO iqPalXEMicroLessonVO);

    public boolean isMicroLessonDeletable(QPalXEMicroLesson qPalXEMicroLesson);

    public void delete(QPalXEMicroLesson qPalXEMicroLesson);

    public void moveUp(QPalXEMicroLesson qPalXEMicroLesson);

    public void moveDown(QPalXEMicroLesson qPalXEMicroLesson);

}
