package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonActivityVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLessonActivity;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXEMicroLessonActivityService {


    public QPalXEMicroLessonActivity findByID(Long id);

    public List<QPalXEMicroLessonActivity> findAllMicroLessonActivities(QPalXEMicroLesson qPalXEMicroLesson);

    public QPalXEMicroLessonActivity buildNewQPalXEMicroLessonActivity(IQPalXEMicroLessonActivityVO iqPalXEMicroLessonActivityVO);
}
