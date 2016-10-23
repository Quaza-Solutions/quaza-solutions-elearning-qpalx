package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.progress;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IQuestionBankProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IQuestionBankItemRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QuestionBankProgressService")
public class QuestionBankProgressService implements IQuestionBankProgressService {


    @Autowired
    private IQuestionBankItemRepository iQuestionBankItemRepository;

    @Autowired
    private IQuestionBankProgressRepository iQuestionBankProgressRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QuestionBankProgressService.class);


    @Transactional
    @Override
    public double calculateQuestionBankProgressPercent(QPalXELesson qPalXELesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Calculation question bank progress for student: {} in lesson: {}", qPalXUser.getEmail(), qPalXELesson.getLessonName());

        // find all question banks in this lesson
        Long totalQuestionBanksAvailable = iQuestionBankItemRepository.countQuestionBankItemByLesson(qPalXELesson);

        // find all the question ban items that the student user has accessed.
        Long totalQuestionBanksAccessed = iQuestionBankProgressRepository.countQuestionBankProgressByLesson(qPalXUser.getId(), qPalXELesson.getId());

        if(totalQuestionBanksAvailable > 0 && totalQuestionBanksAccessed > 0) {
            LOGGER.info("Calulating micro lessons progress using totalQuestionBanksAccessed: {} and totalQuestionBanksAvailable: {}", totalQuestionBanksAccessed, totalQuestionBanksAvailable);
            double progress = totalQuestionBanksAccessed / totalQuestionBanksAvailable;
            return progress;
        }

        return 0;
    }
}
