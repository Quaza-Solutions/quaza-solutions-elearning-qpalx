package com.quaza.solutions.qpalx.elearning.ci.lms.curriculum;

import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author manyce400
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {QPalXServiceApplicationBootstrapper.class})
public class DefaultELearningCourseServiceCITest {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService ieLearningCurriculumService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    @Test
    public void testSaveELearningCourse() {
        // Link newly created course to Mathematics curriculum
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumName("Mathematics");
        
        // Build an ELearningCourse and persist to database
        ELearningCourse eLearningCourse = ELearningCourse.builder()
                .courseActive(true)
                .courseName("Algebra 1")
                .courseDescription("Entry Level Algebra Course")
                .proficiencyRankingScaleFloor(ProficiencyRankingScaleE.ONE)
                .proficiencyRankingScaleCeiling(ProficiencyRankingScaleE.FOUR)
                .eLearningCurriculum(eLearningCurriculum)
                .entryDate(new DateTime())
                .build();
        
        // Save and verify that its been saved
        ieLearningCourseService.saveELearningCourse(eLearningCourse);
        System.out.println("eLearningCourse = " + eLearningCourse);
    }

}
