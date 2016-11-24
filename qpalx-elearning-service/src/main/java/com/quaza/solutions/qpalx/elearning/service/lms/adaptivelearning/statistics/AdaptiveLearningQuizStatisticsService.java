package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.AdaptiveLearningQuizProgress;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IAdaptiveLearningQuizProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningQuizRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizStatisticsService")
public class AdaptiveLearningQuizStatisticsService implements IAdaptiveLearningQuizStatisticsService {




    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String quizStatisticsSql;

    @Value("classpath:/sql/MicroLessonQuizzesStatistics.sql")
    private Resource sqlResource;


    @Autowired
    private IAdaptiveLearningQuizProgressRepository iAdaptiveLearningQuizProgressRepository;

    @Autowired
    private IAdaptiveLearningQuizRepository iAdaptiveLearningQuizRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizStatisticsService.class);


    @Transactional
    @Override
    public void recordAdaptiveLearningQuizProgress(Long scoreableActivityID, QPalXUser qPalXUser) {
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


    @Override
    public List<AdaptiveLessonQuizStatistics> findMicroLessonStudentQuizStatistics(QPalXUser qPalXUser, QPalXEMicroLesson qPalXEMicroLesson) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");

        LOGGER.info("Finding Quizzes with statistics with microLessoID: {} for qPalXUser: {}", qPalXEMicroLesson.getId(), qPalXUser.getEmail());
        LOGGER.debug("Running SQL:=>  {}", quizStatisticsSql);

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), qPalXEMicroLesson.getId()};
        List<AdaptiveLessonQuizStatistics> results = jdbcTemplate.query(quizStatisticsSql, uniqueIDs, AdaptiveLessonQuizStatistics.newRowMapper());
        return results;
    }

    @PostConstruct
    private void loadLessonStatisticsSQL() throws IOException {
        LOGGER.info("Loading lessons statistic sql from resource: {}", sqlResource);
        quizStatisticsSql  = Resources.toString(sqlResource.getURL(), Charsets.UTF_8);
    }
}
