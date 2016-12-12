package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveMicroLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
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
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveMicroLessonStatisticsService")
public class AdaptiveMicroLessonStatisticsService implements IAdaptiveMicroLessonStatisticsService {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String statisticsSQL;

    @Value("classpath:/sql/micro-lessons-details-statistics.sql")
    private Resource sqlResource;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StatisticsExecutorService")
    private ListeningExecutorService listeningExecutorService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveMicroLessonStatisticsService.class);


    @Transactional
    @Override
    public List<AdaptiveMicroLessonStatistics> findAdaptiveMicroLessonStatisticsByLessonAndCourse(QPalXELesson qPalXELesson, QPalXUser qPalXUser) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        ELearningCourse eLearningCourse = qPalXELesson.geteLearningCourse();

        LOGGER.info("Finding all AdaptiveMicroLessonStatistics for qPalXELesson: {} and eLearningCourse: {}", qPalXELesson.getLessonName(), eLearningCourse.getCourseName());

        Long [] uniqueIDs = new Long[] {qPalXUser.getId(), eLearningCourse.getId(),  qPalXELesson.getId(), qPalXELesson.getId(), qPalXUser.getId()};
        List<AdaptiveMicroLessonStatistics> results = jdbcTemplate.query(statisticsSQL, uniqueIDs, AdaptiveMicroLessonStatistics.newRowMapper());
        return results;
    }

    @PostConstruct
    private void loadLessonStatisticsSQL() throws IOException {
        LOGGER.info("Loading lessons statistic sql from resource: {}", sqlResource);
        statisticsSQL  = Resources.toString(sqlResource.getURL(), Charsets.UTF_8);
    }
}
