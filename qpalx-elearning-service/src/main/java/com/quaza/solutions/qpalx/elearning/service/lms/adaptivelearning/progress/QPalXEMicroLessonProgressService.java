package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IQPalXEMicroLessonProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXEMicroLessonRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonProgressService")
public class QPalXEMicroLessonProgressService implements IQPalXEMicroLessonProgressService {


    @Autowired
    private IQPalXEMicroLessonRepository iqPalXEMicroLessonRepository;

    @Autowired
    private IQPalXEMicroLessonProgressRepository iqPalXEMicroLessonProgressRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXEMicroLessonProgressService.class);


    @Transactional
    @Override
    public double calculateQPalXEMicroLessonProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Calculation progress for student: {} in lesson: {}", qPalXUser.getEmail(), qPalXELesson.getLessonName());

        // Find all the available MicroLessons available for the QPalXELesson
        Long totalMicroLessonAvailable = iqPalXEMicroLessonRepository.countQPalXEMicroLessonByLesson(qPalXELesson);

        // find all Micro Lessons user has accessesd.
        Long totalMicroLessonsAccessed = iqPalXEMicroLessonProgressRepository.countMicroLessonProgressByLesson(qPalXUser.getId(), qPalXELesson.getId());

        if(totalMicroLessonAvailable > 0 && totalMicroLessonsAccessed > 0) {
            LOGGER.info("Calulating micro lessons progress using totalMicroLessonsAccessed: {} and totalMicroLessonAvailable: {}", totalMicroLessonsAccessed, totalMicroLessonAvailable);
            double progress = totalMicroLessonsAccessed / totalMicroLessonAvailable;
            return progress;
        }

        return 0;
    }
}
