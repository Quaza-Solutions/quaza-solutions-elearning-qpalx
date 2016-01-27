package com.quaza.solutions.qpalx.elearning.service.ci.adaptivelearning;

import com.quaza.solutions.qpalx.QPalXServiceApplicationBootstrapper;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveLearningProfileService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.IQPalxUserService;
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
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningProfileService")
    private IAdaptiveLearningProfileService iAdaptiveLearningProfileService;


    // Testing creation, saving of plain AdaptiveLearningProfile for a User with no collection fields persisted.
    @Test
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
    }

}
