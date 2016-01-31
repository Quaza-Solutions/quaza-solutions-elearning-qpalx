package com.quaza.solutions.qpalx.elearning.service.ci.adaptivelearning;

import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveLearningProfileService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseActivityService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

/**
 * Uses SpringJUnit testing to ci AdaptiveLearningProfileService.
 *
 * We use the main class QPalXServiceApplicationBootstrapper from qpalx-elearning-service module
 * in order to to bootstrap all beans in QPalX application to run this test.
 *
 * No mocking is done in CI tests as we invoke real functionalit against the real database.
 *
 * @author manyce400
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {QPalXServiceApplicationBootstrapper.class})
public class AdaptiveLearningProfileServiceCITest {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService ieLearningCurriculumService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseActivityService")
    private IELearningCourseActivityService ieLearningCourseActivityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningProfileService")
    private IAdaptiveLearningProfileService iAdaptiveLearningProfileService;


    // Testing creation, saving of plain AdaptiveLearningProfile for a User with no collection fields persisted.
    //@Test
    public void testCreateAndSaveAdaptiveLearningProfile() {
        // load an already previously saved QPalxUser
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserByEmail("manyce400@gmail.com");
        Optional<AdaptiveLearningProfile> adaptiveLearningProfile = iAdaptiveLearningProfileService.buildNewAdaptiveLearningProfileForQPalxUser(qPalXUser);

        // Assert that AdaptiveLearningProfile was actually created
        Assert.assertTrue("QPalxUser AdaptiveLearningProfile was created", adaptiveLearningProfile.isPresent());

        // Save to database if assertion does not fail
        iAdaptiveLearningProfileService.save(adaptiveLearningProfile.get());

        // Verify that an AdaptiveLearningProfile has been saved for this user
        Optional<AdaptiveLearningProfile> savedAdaptiveLearningProfile = iAdaptiveLearningProfileService.findAdaptiveLearningProfileForQPalxUser(qPalXUser);

        // Assert that the saved AdaptiveLearningProfile was found
        Assert.assertTrue("QPalxUser recently saved AdaptiveLearningProfile was found", savedAdaptiveLearningProfile.isPresent());
        System.out.println("savedAdaptiveLearningProfile = " + savedAdaptiveLearningProfile);

        iAdaptiveLearningProfileService.delete(savedAdaptiveLearningProfile.get());
    }

    // Testing creation, saving of AdaptiveLearningProfile for a User with AdaptiveProficiencyRankings persisted
    //@Test
    public void testCreateAndSaveAdaptiveLearningProfileWithAdaptiveProficiencyRanking() {
        // load an already previously saved QPalxUser
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserByEmail("manyce400@gmail.com");
        Optional<AdaptiveLearningProfile> adaptiveLearningProfile = iAdaptiveLearningProfileService.buildNewAdaptiveLearningProfileForQPalxUser(qPalXUser);

        // Assert that AdaptiveLearningProfile was actually created
        Assert.assertTrue("QPalxUser AdaptiveLearningProfile was created", adaptiveLearningProfile.isPresent());

        // build AdaptiveProficiencyRanking and add to users learning profile
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumName("Mathematics");
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .adaptiveLearningProfile(adaptiveLearningProfile.get())
                .proficiencyRankingScaleE(ProficiencyRankingScaleE.TEN)
                .eLearningCurriculum(eLearningCurriculum)
                .proficiencyRankingRecordDateTime(new DateTime())
                .build();

        adaptiveLearningProfile.get().addAdaptiveProficiencyRanking(adaptiveProficiencyRanking);

        // Save to database if assertion does not fail
        iAdaptiveLearningProfileService.save(adaptiveLearningProfile.get());

        // Verify that an AdaptiveLearningProfile has been saved for this user
        Optional<AdaptiveLearningProfile> savedAdaptiveLearningProfile = iAdaptiveLearningProfileService.findAdaptiveLearningProfileForQPalxUser(qPalXUser);

        // Assert that the saved AdaptiveLearningProfile was found
        Assert.assertTrue("QPalxUser recently saved AdaptiveLearningProfile was found", savedAdaptiveLearningProfile.isPresent());
        System.out.println("savedAdaptiveLearningProfile = " + savedAdaptiveLearningProfile);
    }

    // Testing creation, saving of AdaptiveLearningProfile for a User with AdaptiveLearningExperience persisted
    @Test
    public void testCreateAndSaveAdaptiveLearningProfileWithAdaptiveLearningExperience() {
        // load an already previously saved QPalxUser
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserByEmail("manyce400@gmail.com");
        Optional<AdaptiveLearningProfile> adaptiveLearningProfile = iAdaptiveLearningProfileService.buildNewAdaptiveLearningProfileForQPalxUser(qPalXUser);

        // Assert that AdaptiveLearningProfile was actually created
        Assert.assertTrue("QPalxUser AdaptiveLearningProfile was created", adaptiveLearningProfile.isPresent());

        ELearningCourseActivity eLearningCourseActivity = ieLearningCourseActivityService.findByID(1L);
        System.out.println("eLearningCourseActivity = " + eLearningCourseActivity);

        // build AdaptiveProficiencyRanking and add to users learning profile
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumName("Mathematics");
        AdaptiveLearningExperience adaptiveLearningExperience = AdaptiveLearningExperience.builder()
                .adaptiveLearningProfile(adaptiveLearningProfile.get())
                .eLearningCourseActivity(eLearningCourseActivity)
                .proficiencyScore(80D)
                .learningExperienceStartDate(new DateTime())
                .learningExperienceCompletedDate(new DateTime())
                .qpalxUser(qPalXUser)
                .build();


        adaptiveLearningProfile.get().addAdaptiveLearningExperience(adaptiveLearningExperience);

        // Save to database if assertion does not fail
        iAdaptiveLearningProfileService.save(adaptiveLearningProfile.get());

        // Verify that an AdaptiveLearningProfile has been saved for this user
        Optional<AdaptiveLearningProfile> savedAdaptiveLearningProfile = iAdaptiveLearningProfileService.findAdaptiveLearningProfileForQPalxUser(qPalXUser);

        // Assert that the saved AdaptiveLearningProfile was found
        Assert.assertTrue("QPalxUser recently saved AdaptiveLearningProfile was found", savedAdaptiveLearningProfile.isPresent());
        System.out.println("savedAdaptiveLearningProfile = " + savedAdaptiveLearningProfile);
    }

}
