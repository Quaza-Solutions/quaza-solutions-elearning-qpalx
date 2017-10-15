package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.microlesson;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * Defines API for calculating Students AdaptiveProficiencyRanking details in a given curriculum.
 *
 * @author manyce400
 */
public interface IMicroLessonPerformanceMonitorService {


    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser student, ELearningCourse eLearningCourse);

    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser student, QPalXEMicroLesson microLesson);

    public List<AdaptiveLearningQuiz> findPrerequisiteIncompleteQuizzes(QPalXUser student, QPalXEMicroLesson microLesson, AdaptiveLearningQuiz adaptiveLearningQuiz);

}
