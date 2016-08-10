package com.quaza.solutions.qpalx.elearning.ci.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author manyce400
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {QPalXServiceApplicationBootstrapper.class})
public class DefaultAdaptiveProficiencyRankingServiceCITest {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService ieLearningCurriculumService;

//    @Autowired
//    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService")
//    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;


    @Test
    public void testFindCurrentStudentAdaptiveProficiencyRankingForCurriculum() {
        // First look up a user
//        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserByEmail("mjones@gmail.com");
//        System.out.println("qPalXUser = " + qPalXUser);
//
//        // Lookup a curriculum
//        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumID(1L);
//        System.out.println("eLearningCurriculum = " + eLearningCurriculum);
        
        // Find this students current ranking in the curriculum.
//        AdaptiveProficiencyRanking adaptiveProficiencyRanking = iAdaptiveProficiencyRankingService.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);
//        System.out.println("adaptiveProficiencyRanking = " + adaptiveProficiencyRanking);
    }

//    @Test
    public void testCeateStudentAdaptiveProficiencyRanking() {
        // First look up a user
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserByEmail("mjones@gmail.com");

        // Lookup a curriculum
//        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumID(1L);
//        iAdaptiveProficiencyRankingService.createStudentAdaptiveProficiencyRanking(qPalXUser, ProficiencyRankingTriggerTypeE.ON_DEMAND, eLearningCurriculum, ProficiencyRankingScaleE.SEVEN);

    }
}
