package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.QPalXEMicroLessonProgress;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IQPalXEMicroLessonProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLessonStatisticsService")
public class AdaptiveLessonStatisticsService implements IAdaptiveLessonStatisticsService {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String lessonStatisticsSql;

    @Value("classpath:/sql/AdaptiveLessonStatistics.sql")
    private Resource lessonStatisticsSqlResource;

    @Autowired
    private IQPalXEMicroLessonProgressRepository iqPalXEMicroLessonProgressRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StatisticsExecutorService")
    private ListeningExecutorService listeningExecutorService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLessonStatisticsService.class);


    @Transactional
    @Override
    public void recordAdaptiveLessonStatistics(QPalXEMicroLesson qPalXEMicroLesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXEMicroLesson, "qPalXEMicroLesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        //Runnable statisticsRecordTask = () -> {
            LOGGER.info("Recording Lesson statistics on MicroLesson with ID: {} for User: {}", qPalXEMicroLesson.getId(), qPalXUser.getEmail());

            QPalXEMicroLessonProgress qPalXEMicroLessonProgress = iqPalXEMicroLessonProgressRepository.findByUserAndMicroLessonID(qPalXUser.getId(), qPalXEMicroLesson.getId());
            if(qPalXEMicroLessonProgress != null) {
                qPalXEMicroLessonProgress.increaseNumberOfAttempts();
                qPalXEMicroLessonProgress.setLastAttemptEntryDate(new DateTime());
            } else {
                // create a mew one and save
                qPalXEMicroLessonProgress = QPalXEMicroLessonProgress.builder()
                        .microLessonID(qPalXEMicroLesson.getId())
                        .qPalxUserID(qPalXUser.getId())
                        .numberOfAttempts(1)
                        .lastAttemptEntryDate(new DateTime())
                        .build();
            }
        iqPalXEMicroLessonProgressRepository.save(qPalXEMicroLessonProgress);
        //};


        //listeningExecutorService.submit(statisticsRecordTask);
    }

    @Override
    public List<AdaptiveLessonStatistics> findAdaptiveLessonStatisticsByCourseIDAndTutorialLevel(QPalXUser qPalXUser, TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(tutorialLevelCalendar, "tutorialLevelCalendar cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding all AdaptiveLessonStatistics for tutorialLevelCalendar: {} and eLearningCourse: {}", tutorialLevelCalendar.getCalendarItemName(), eLearningCourse.getCourseName());
        LOGGER.info("Running SQL:=>  {}", lessonStatisticsSql);

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), eLearningCourse.getId(), qPalXUser.getId(), eLearningCourse.getId(),  qPalXUser.getId(), eLearningCourse.getId(), eLearningCourse.getId(), tutorialLevelCalendar.getId(), eLearningCourse.getId(), tutorialLevelCalendar.getId(), eLearningCourse.getId(), tutorialLevelCalendar.getId(), qPalXUser.getId()};
        List<AdaptiveLessonStatistics> results = jdbcTemplate.query(lessonStatisticsSql, uniqueIDs, AdaptiveLessonStatistics.newRowMapper());
        return results;
    }


    @PostConstruct
    private void loadLessonStatisticsSQL() throws IOException {
        LOGGER.info("Loading lessons statistic sql from resource: {}", lessonStatisticsSqlResource);
        lessonStatisticsSql  = Resources.toString(lessonStatisticsSqlResource.getURL(), Charsets.UTF_8);
    }

}
