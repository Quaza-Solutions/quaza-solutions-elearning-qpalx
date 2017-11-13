package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
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
@Service(StudentOverallProgressStatisticsService.BEAN_NAME)
public class StudentOverallProgressStatisticsService implements IStudentOverallProgressStatisticsService {



    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String ovearallProgressStatisticsSql;

    private String globalStudentProgressSql;

    @Value("classpath:/sql/StudentOverallProgress.sql")
    private Resource overallStudentProgressSqlResource;

    @Value("classpath:/sql/global-performance/student-curriculum-progress.sql")
    private Resource globalStudentProgressSqlResource;


    public static final String BEAN_NAME = "quaza.solutions.qpalx.elearning.service.StudentOverallProgressStatisticsService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentOverallProgressStatisticsService.class);

    @Override
    public List<StudentOverallProgressStatistics> getStudentOverallProgressStatistics(QPalXUser qPalXUser, CurriculumType curriculumType) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(curriculumType, "curriculumType cannot be null");

        LOGGER.info("Finding all Student overall progress statistics for qPalXUser: {} with curriculumType: {}", qPalXUser.getEmail(), curriculumType);

        Object [] params = new Object[] {
                qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString(),
                qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString(),
                qPalXUser.getId(), curriculumType.toString(), qPalXUser.getId(), curriculumType.toString()
        };

        List<StudentOverallProgressStatistics> overallProgressStatisticsList =  jdbcTemplate.query(ovearallProgressStatisticsSql, params, StudentOverallProgressStatistics.newRowMapper());
        return overallProgressStatisticsList;
    }

    @Override
    public StudentOverallProgressStatistics getGlobalStudentOverallProgressStatisticsInCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");

        LOGGER.info("Calculuating student global performance progress for student: {} in curriculum: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName());

        Object [] params = new Object[] {
                qPalXUser.getId(), eLearningCurriculum.getCurriculumType().toString(), eLearningCurriculum.getId(), qPalXUser.getId(), eLearningCurriculum.getCurriculumType().toString(), eLearningCurriculum.getId(),
                qPalXUser.getId(), eLearningCurriculum.getCurriculumType().toString(), eLearningCurriculum.getId(), qPalXUser.getId(), eLearningCurriculum.getCurriculumType().toString(), eLearningCurriculum.getId(),
                qPalXUser.getId(), eLearningCurriculum.getCurriculumType().toString(), eLearningCurriculum.getId(), qPalXUser.getId(), qPalXUser.getId(), eLearningCurriculum.getCurriculumType().toString(), eLearningCurriculum.getId(),
                qPalXUser.getId(), eLearningCurriculum.getCurriculumType().toString(), eLearningCurriculum.getId()
        };

        StudentOverallProgressStatistics studentOverallProgressStatistics = jdbcTemplate.queryForObject(globalStudentProgressSql, params, StudentOverallProgressStatistics.newRowMapper());
        return studentOverallProgressStatistics;
    }

    @PostConstruct
    private void loadOverallProgressStatistics() throws IOException {
        LOGGER.info("Loading lessons statistic sql from ovearallProgressStatisticsSql: {} globalStudentProgressSql: {}", overallStudentProgressSqlResource, globalStudentProgressSqlResource);
        ovearallProgressStatisticsSql  = Resources.toString(overallStudentProgressSqlResource.getURL(), Charsets.UTF_8);
        globalStudentProgressSql = Resources.toString(globalStudentProgressSqlResource.getURL(), Charsets.UTF_8);
    }

}