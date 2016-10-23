package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IAdaptiveLearningQuizProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningQuizRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizProgressService")
public class AdaptiveLearningQuizProgressService implements IAdaptiveLearningQuizProgressService {


    @Autowired
    private IAdaptiveLearningQuizRepository iAdaptiveLearningQuizRepository;

    @Autowired
    private IAdaptiveLearningQuizProgressRepository adaptiveLearningQuizProgressRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizProgressService.class);


    @Transactional
    @Override
    public double calculateAdaptiveLearningQuizProgressPercent(QPalXEMicroLesson qPalXEMicroLesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Calculation progress for student: {} in micro-lesson: {}", qPalXUser.getEmail(), qPalXEMicroLesson.getMicroLessonName());

        // find all the quizzes in this micro-lesson
        Set<AdaptiveLearningQuiz> adaptiveLearningQuizzes = qPalXEMicroLesson.getAdaptiveLearningQuizzes();

        // Find all the quizzes attempted by user for this micro lesson
        long totalQuizzesTaken = adaptiveLearningQuizProgressRepository.countAdaptiveLearningQuizProgressByMicroLesson(qPalXUser.getId(), qPalXEMicroLesson.getId());

        if(adaptiveLearningQuizzes.size() > 0 && totalQuizzesTaken > 0) {
            int totalMicroLessonAvailableQuizzes = adaptiveLearningQuizzes.size();

            LOGGER.info("Calulating adaptive quiz progress using totalQuizzesTaken: {} and totalMicroLessonAvailableQuizzes: {}", totalQuizzesTaken, totalMicroLessonAvailableQuizzes);
            double progress = totalQuizzesTaken / totalMicroLessonAvailableQuizzes;
            return progress;
        }

        return 0;
    }

    @Transactional
    @Override
    public double calculateAdaptiveLearningQuizProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Calculation progress for student: {} in lesson: {}", qPalXUser.getEmail(), qPalXELesson.getLessonName());

        // Find count of all quizzes for this lesson
        Long totalAdaptiveQuizzes = iAdaptiveLearningQuizRepository.countAdaptiveLearningQuizByLesson(qPalXELesson);

        // Get count of all quizzes this user has taken
        long totalQuizzesTaken = adaptiveLearningQuizProgressRepository.countAdaptiveLearningQuizProgressByLesson(qPalXUser.getId(), qPalXELesson.getId());

        if(totalAdaptiveQuizzes > 0 && totalQuizzesTaken > 0) {
            LOGGER.info("Calulating adaptive quiz progress using totalQuizzesTaken: {} and totalAdaptiveQuizzes: {}", totalQuizzesTaken, totalAdaptiveQuizzes);
            double progress = totalQuizzesTaken / totalAdaptiveQuizzes;
            return progress;
        }

        return 0;
    }
}
