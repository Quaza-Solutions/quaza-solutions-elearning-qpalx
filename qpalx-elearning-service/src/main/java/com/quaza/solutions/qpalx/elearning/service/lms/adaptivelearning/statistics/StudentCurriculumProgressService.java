package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.service.jdbc.JDBCTemplateEnabledService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400 
 */
@Service(StudentCurriculumProgressService.SPRING_BEAN_NAME)
public class StudentCurriculumProgressService extends JDBCTemplateEnabledService implements IStudentCurriculumProgressService {




    @Value("classpath:/sql/progress/StudentELearningCurriculumProgress.sql")
    private Resource externalSQLResource;

    public static final String SPRING_BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.StudentCurriculumProgressService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentCurriculumProgressService.class);
    
    
    @Override
    public StudentOverallProgressStatistics getStudentOverallProgressStatisticsInCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "student cannot be null");
        Assert.notNull(eLearningCurriculum, "curriculumType cannot be null");

        LOGGER.info("Computing Student current Curriculum progress in Curriculum: {}", eLearningCurriculum.getCurriculumName());

        Object [] params = new Object[] {
                qPalXUser.getId(), eLearningCurriculum.getId(), qPalXUser.getId(), eLearningCurriculum.getId(),
                qPalXUser.getId(), eLearningCurriculum.getId(), qPalXUser.getId(), eLearningCurriculum.getId(), qPalXUser.getId(), eLearningCurriculum.getId(),
                qPalXUser.getId(), qPalXUser.getId(), eLearningCurriculum.getId(), qPalXUser.getId(), eLearningCurriculum.getId()
        };

        StudentOverallProgressStatistics curriculumOverallProgress =  jdbcTemplate.queryForObject(externalSQLString, params, StudentOverallProgressStatistics.newRowMapper());
        return curriculumOverallProgress;
    }


    @Override
    protected Resource getExternalSQLResource() {
        return externalSQLResource;
    }
}
