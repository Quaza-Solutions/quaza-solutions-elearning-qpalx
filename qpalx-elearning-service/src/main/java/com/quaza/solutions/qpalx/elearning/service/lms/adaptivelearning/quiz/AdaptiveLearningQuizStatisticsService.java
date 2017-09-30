package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.quiz;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.AdaptiveLearningQuizProgress;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IAdaptiveLearningQuizProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.AdaptiveLessonQuizStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.quiz.repository.IAdaptiveLearningQuizRepository;
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

    private String lessonAllQuizzesSql;

    @Value("classpath:/sql/MicroLessonQuizzesStatistics.sql")
    private Resource sqlResource;

    @Value("classpath:/sql/quizzes/all-lesson-quizzes-statistics.sql")
    private Resource lessonAllQuizzesSqlResource;


    @Autowired
    private IAdaptiveLearningQuizProgressRepository iAdaptiveLearningQuizProgressRepository;

    @Autowired
    private IAdaptiveLearningQuizRepository iAdaptiveLearningQuizRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizStatisticsService.class);


    @Transactional
    @Override
    public void recordAdaptiveLearningQuizProgress(Long adaptiveLearningQuizID, QPalXUser qPalXUser) {
        Assert.notNull(adaptiveLearningQuizID, "scoreableActivityID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Attempting to save Quiz statistics for adaptiveLearningQuizID: {}", adaptiveLearningQuizID);

        // Find the AdaptiveLearningQuiz for this scoreableActivityID
        AdaptiveLearningQuiz adaptiveLearningQuiz = iAdaptiveLearningQuizRepository.findOne(adaptiveLearningQuizID);

        if(adaptiveLearningQuiz != null) {
            QPalXEMicroLesson microLesson = adaptiveLearningQuiz.getQPalXEMicroLesson();

            // Find existing quiz progress and record new attempt
            AdaptiveLearningQuizProgress adaptiveLearningQuizProgress = iAdaptiveLearningQuizProgressRepository.findExistingAdaptiveLearningQuizProgress(qPalXUser.getId(), adaptiveLearningQuiz.getId(), microLesson.getId());

            if (adaptiveLearningQuizProgress == null) {
                LOGGER.info("No prior Quiz progress was found for scoreableActivityID: {} recording new....", adaptiveLearningQuizID);
                adaptiveLearningQuizProgress = AdaptiveLearningQuizProgress.builder()
                        .adaptiveLearningQuizID(adaptiveLearningQuiz.getId())
                        .qPalxUserID(qPalXUser.getId())
                        .microLessonID(microLesson.getId())
                        .lastAttemptEntryDate(new DateTime())
                        .numberOfAttempts(1)
                        .build();
            } else {
                LOGGER.info("Prior Quiz progress was found for scoreableActivityID: {} updating statistics...", adaptiveLearningQuizID);
                adaptiveLearningQuizProgress.incrementNumberOfAttempts();
                adaptiveLearningQuizProgress.setLastAttemptEntryDate(new DateTime());
            }

            iAdaptiveLearningQuizProgressRepository.save(adaptiveLearningQuizProgress);
        }
    }

    @Override
    public List<AdaptiveLessonQuizStatistics> findStudentQuizzesStatisticsForLesson(QPalXUser qPalXUser, Long eLessonID) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLessonID, "eLessonID cannot be null");

        LOGGER.debug("Finding all Quizzes statistics for student: {} in eLessonID: {}", qPalXUser.getEmail(), eLessonID);

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), eLessonID, qPalXUser.getId()};
        List<AdaptiveLessonQuizStatistics> results = jdbcTemplate.query(lessonAllQuizzesSql, uniqueIDs, AdaptiveLessonQuizStatistics.newRowMapper());
        return results;
    }

    @Override
    public List<AdaptiveLessonQuizStatistics> findMicroLessonStudentQuizStatistics(QPalXUser qPalXUser, Long microLessonID) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(microLessonID, "microLessonID cannot be null");

        LOGGER.info("Finding Quizzes with statistics with microLessoID: {} for qPalXUser: {}", microLessonID, qPalXUser.getEmail());
        LOGGER.debug("Running SQL:=>  {}", quizStatisticsSql);

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), microLessonID, qPalXUser.getId()};
        List<AdaptiveLessonQuizStatistics> results = jdbcTemplate.query(quizStatisticsSql, uniqueIDs, AdaptiveLessonQuizStatistics.newRowMapper());
        return results;
    }

    @PostConstruct
    private void loadLessonStatisticsSQL() throws IOException {
        quizStatisticsSql  = Resources.toString(sqlResource.getURL(), Charsets.UTF_8);
        lessonAllQuizzesSql = Resources.toString(lessonAllQuizzesSqlResource.getURL(), Charsets.UTF_8);
    }
}
