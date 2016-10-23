package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * @author manyce400
 */
public interface IQuestionBankProgressService {


    public double calculateQuestionBankProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser);


}
