package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizProgressService {


    public double calculateAdaptiveLearningQuizProgressPercent(QPalXEMicroLesson qPalXEMicroLesson, QPalXUser qPalXUser);

    public double calculateAdaptiveLearningQuizProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser);

}
