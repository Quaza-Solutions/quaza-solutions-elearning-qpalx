package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.AdaptiveLearningQuizProgress;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IAdaptiveLearningQuizProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningQuizRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizStatisticsService")
public class AdaptiveLearningQuizStatisticsService implements IAdaptiveLearningQuizStatisticsService {




    @Autowired
    private IAdaptiveLearningQuizProgressRepository iAdaptiveLearningQuizProgressRepository;

    @Autowired
    private IAdaptiveLearningQuizRepository iAdaptiveLearningQuizRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizStatisticsService.class);


    @Transactional
    @Override
    public void recordAdaptiveLearningQuizStatistic(Long scoreableActivityID, QPalXUser qPalXUser) {
        Assert.notNull(scoreableActivityID, "scoreableActivityID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Attempting to save Quiz statistics for scoreableActivityID: {}", scoreableActivityID);

        // Find the AdaptiveLearningQuiz for this scoreableActivityID
        AdaptiveLearningQuiz adaptiveLearningQuiz = iAdaptiveLearningQuizRepository.findByScorableActivityID(scoreableActivityID);

        if(adaptiveLearningQuiz != null) {
            QPalXEMicroLesson microLesson = adaptiveLearningQuiz.getqPalXEMicroLesson();

            // Find existing quiz progress and record new attempt
            AdaptiveLearningQuizProgress adaptiveLearningQuizProgress = iAdaptiveLearningQuizProgressRepository.findExistingAdaptiveLearningQuizProgress(qPalXUser.getId(), adaptiveLearningQuiz.getId(), microLesson.getId());

            if (adaptiveLearningQuizProgress == null) {
                LOGGER.info("No prior Quiz progress was found for scoreableActivityID: {} recording new....", scoreableActivityID);
                adaptiveLearningQuizProgress = AdaptiveLearningQuizProgress.builder()
                        .adaptiveLearningQuizID(adaptiveLearningQuiz.getId())
                        .qPalxUserID(qPalXUser.getId())
                        .microLessonID(microLesson.getId())
                        .lastAttemptEntryDate(new DateTime())
                        .numberOfAttempts(1)
                        .build();
            } else {
                LOGGER.info("Prior Quiz progress was found for scoreableActivityID: {} updating statistics...", scoreableActivityID);
                adaptiveLearningQuizProgress.incrementNumberOfAttempts();
                adaptiveLearningQuizProgress.setLastAttemptEntryDate(new DateTime());
            }

            iAdaptiveLearningQuizProgressRepository.save(adaptiveLearningQuizProgress);
        }
    }


}
