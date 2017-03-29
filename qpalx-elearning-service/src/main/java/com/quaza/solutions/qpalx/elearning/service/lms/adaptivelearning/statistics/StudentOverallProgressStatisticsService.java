package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.StudentOverallProgressStatisticsService")
public class StudentOverallProgressStatisticsService implements IStudentOverallProgressStatisticsService {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String ovearallProgressStatisticsSql;

    @Value("classpath:/sql/StudentOverallProgress.sql")
    private Resource overallStudentProgressSqlResource;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentOverallProgressStatisticsService.class);

    @Override
    public List<StudentOverallProgressStatistics> getStudentOverallProgressStatistics(QPalXUser qPalXUser, CurriculumType curriculumType) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(curriculumType, "curriculumType cannot be null");

        LOGGER.info("Finding all Student overall progress statistics for qPalXUser: {} with curriculumType: {}", qPalXUser.getEmail(), curriculumType);

        Object [] params = new Object[] {
                qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString(),
                qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString(),
                qPalXUser.getId(), qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString()
        };

        List<StudentOverallProgressStatistics> overallProgressStatisticsList =  jdbcTemplate.query(ovearallProgressStatisticsSql, params, StudentOverallProgressStatistics.newRowMapper());
        return overallProgressStatisticsList;
    }

    @PostConstruct
    private void loadOverallProgressStatistics() throws IOException {
        LOGGER.info("Loading lessons statistic sql from resource: {}", overallStudentProgressSqlResource);
        ovearallProgressStatisticsSql  = Resources.toString(overallStudentProgressSqlResource.getURL(), Charsets.UTF_8);
    }

}