package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXELessonStudentProgressService")
public class QPalXELessonStudentProgressService implements IQPalXELessonStudentProgressService {




    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizProgressService")
    private IAdaptiveLearningQuizProgressService iAdaptiveLearningQuizProgressService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonProgressService")
    private IQPalXEMicroLessonProgressService iqPalXEMicroLessonProgressService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QuestionBankProgressService")
    private IQuestionBankProgressService iQuestionBankProgressService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXELessonStudentProgressService.class);


    @Transactional
    @Override
    public double calculateLessonProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();

        LOGGER.info("Calculation progress for student: {} in lesson: {}", qPalXUser.getEmail(), qPalXELesson.getLessonName());

        // Calculate the adaptive quizzes completion in this lesson
        double quizProgress = iAdaptiveLearningQuizProgressService.calculateAdaptiveLearningQuizProgressPercent(qPalXELesson, qPalXUser);

        // Calculate the micro lessons completion rate in this lesson
        double microLessonProgress = iqPalXEMicroLessonProgressService.calculateQPalXEMicroLessonProgressPercent(qPalXELesson, qPalXUser);

        // Calculate the question bank completion rate in this lesson
        double questionBankProgress = iQuestionBankProgressService.calculateQuestionBankProgressPercent(qPalXELesson, qPalXUser);

        descriptiveStatistics.addValue(quizProgress);
        descriptiveStatistics.addValue(microLessonProgress);
        descriptiveStatistics.addValue(questionBankProgress);

        return descriptiveStatistics.getMean();
    }
}
