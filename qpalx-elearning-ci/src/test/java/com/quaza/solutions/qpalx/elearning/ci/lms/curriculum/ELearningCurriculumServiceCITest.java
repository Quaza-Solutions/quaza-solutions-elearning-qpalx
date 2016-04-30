package com.quaza.solutions.qpalx.elearning.ci.lms.curriculum;

import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author manyce400
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {QPalXServiceApplicationBootstrapper.class})
public class ELearningCurriculumServiceCITest {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
    private IQPalXTutorialService iqPalXTutorialService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService ieLearningCurriculumService;
    

    @Test
    public void testFindByELearningCurriculumID() {
        // Validates that we can find a ELearningCurriculum from the table with ID 1
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumID(1L);
        System.out.println("eLearningCurriculum = " + eLearningCurriculum);
        Assert.assertNotNull(eLearningCurriculum);
    }

    @Test
    public void testFindByELearningCurriculumName() {
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumName("English");
        System.out.println("eLearningCurriculum = " + eLearningCurriculum);
        Assert.assertNotNull(eLearningCurriculum);
    }

    @Test
    public void testFindAllCurriculumByTypeCore() {
        // verify that we can find CORE curricula in the system
        List<ELearningCurriculum> eLearningCurriculaCore = ieLearningCurriculumService.findAllCurriculumByType(CurriculumType.CORE);
        System.out.println("eLearningCurriculaCore = " + eLearningCurriculaCore);
        Assert.assertNotNull(eLearningCurriculaCore);

        eLearningCurriculaCore.forEach(eLearningCurriculum -> {
            CurriculumType curriculumType = eLearningCurriculum.getCurriculumType();
            // verify that all the curriculumType loaded are core.
            Assert.assertEquals(CurriculumType.CORE, curriculumType);
        });
    }

    @Test
    public void testFindAllCurriculumByTypeElective() {
        // verify that we can find CORE curricula in the system
        List<ELearningCurriculum> eLearningCurriculaElective = ieLearningCurriculumService.findAllCurriculumByType(CurriculumType.ELECTIVE);
        System.out.println("eLearningCurriculaCore = " + eLearningCurriculaElective);
        Assert.assertNotNull(eLearningCurriculaElective);

        eLearningCurriculaElective.forEach(eLearningCurriculum -> {
            CurriculumType curriculumType = eLearningCurriculum.getCurriculumType();
            // verify that all the curriculumType loaded are core.
            Assert.assertEquals(CurriculumType.ELECTIVE, curriculumType);
        });
    }

    @Test
    public void testFindAllCurriculumByTutorialLevel() {
        // Find a QPalXTUtorialLevel
        StudentTutorialLevel studentTutorialLevel = iqPalXTutorialService.findQPalXTutorialLevelByID(4L); // SHS in WAF region
        Assert.assertNotNull(studentTutorialLevel);
        List<ELearningCurriculum> eLearningCurricula = ieLearningCurriculumService.findAllCurriculumByTutorialLevel(studentTutorialLevel);
        Assert.assertNotNull(eLearningCurricula);
    }
}
