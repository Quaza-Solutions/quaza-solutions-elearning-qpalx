package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXCountryBuilder;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXMunicipalityBuilder;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXSubscriptionBuilder;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXUserBuilder;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

/**
 *
 * @author manyce400
 */
public class DefaultQPalXUserSubscriptionServiceTest {


    @InjectMocks
    private DefaultQPalXUserSubscriptionService defaultQPalXUserSubscriptionService;

    @Mock
    private IQPalXMunicipalityService iqPalXMunicipalityService;

    @Mock
    private IQPalxSubscriptionService iqPalxSubscriptionService;

    @Mock
    private IQPalXTutorialService iqPalXTutorialService;

    @Mock
    private IQPalxUserService iqPalxUserService;

    private MockQPalXUserBuilder mockQPalXUserBuilder = new MockQPalXUserBuilder();


    @Before
    public void beforeTest() {
        // Use Mockito to initialize all mocks before any test runs
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testAddQPalXUserTutorialSubscriptionProfileWhenUserCountryDoesNotMatchSubscription() {
        // Build a mock QPalXUser with their Municipality in New York by default
        QPalXMunicipality accraMunicipality = MockQPalXMunicipalityBuilder.buildNewYorkQPalXMunicipalityBuilder();
        QPalXUser newYorkBasedQPalXUser = MockQPalXUserBuilder.buildMockQPalXUserBuilder(accraMunicipality);

        // Create a QPalXSubscription that is only valid in Ghana and return that when service is invoked
        QPalXCountry ghana = MockQPalXCountryBuilder.buildMockGhanaCountryBuilder();
        QPalXSubscription mockSubscription = MockQPalXSubscriptionBuilder.buildMockTestQPalXSubscription(ghana);
        Mockito.when(iqPalxSubscriptionService.findQPalXSubscriptionByID(1L)).thenReturn(mockSubscription);

        // Execute call and verify that no UserSubscriptionProfile has been created since the user's Country will not match that on the subscription.
        // newYorkBasedQPalXUser country is USA while Subscription is specifically for Ghana.  Users can only subscribe to Subscriptions in their own Country.
        Optional<UserSubscriptionProfile> subscriptionProfile = defaultQPalXUserSubscriptionService.addQPalXUserTutorialSubscriptionProfile(1L, newYorkBasedQPalXUser);
        Assert.assertEquals(Optional.empty(), subscriptionProfile);
    }

    @Test
    public void testAddQPalXUserTutorialSubscriptionProfileWhenUserHasActiveTutorialSubscription() {
        // Build a mock QPalXUser with their Municipality in New York by default
        QPalXMunicipality accraMunicipality = MockQPalXMunicipalityBuilder.buildNewYorkQPalXMunicipalityBuilder();
        QPalXUser newYorkBasedQPalXUserBuilder = MockQPalXUserBuilder.buildMockQPalXUserBuilder(accraMunicipality);

        // Create a QPalXSubscription that is only valid in USA and return that when service is invoked
        QPalXCountry usa = MockQPalXCountryBuilder.buildMockUSACountryBuilder();
        QPalXSubscription mockSubscription = MockQPalXSubscriptionBuilder.buildMockTestQPalXSubscription(usa);
        Mockito.when(iqPalxSubscriptionService.findQPalXSubscriptionByID(1L)).thenReturn(mockSubscription);

        // Assign an active UserSubscriptionProfile to simulate a scenario where this user is already a registered user
        // This will require us setting an ID to signify that this user is already persistent in the database.
        newYorkBasedQPalXUserBuilder.setId(1L);

        MockQPalXUserBuilder.assignActiveUserSubscriptionProfile(newYorkBasedQPalXUserBuilder);
        UserSubscriptionProfile userSubscriptionProfile = newYorkBasedQPalXUserBuilder.getUserSubscriptionProfiles().iterator().next();
        Mockito.when(iqPalxSubscriptionService.findActiveUserSubscriptionProfile(newYorkBasedQPalXUserBuilder)).thenReturn(Optional.of(userSubscriptionProfile));

        // Verify that no UserSubscriptionProfile created since user already has an active UserSubscriptionProfile
        Optional<UserSubscriptionProfile> subscriptionProfile = defaultQPalXUserSubscriptionService.addQPalXUserTutorialSubscriptionProfile(1L, newYorkBasedQPalXUserBuilder);
        Assert.assertEquals(Optional.empty(), subscriptionProfile);
    }

    @Test
    public void testAddQPalXUserTutorialSubscriptionProfile() {
        // Build a mock QPalXUser with their Municipality in New York by default
        QPalXMunicipality newYorkMunicipality = MockQPalXMunicipalityBuilder.buildNewYorkQPalXMunicipalityBuilder();
        QPalXUser newYorkBasedQPalXUserBuilder = MockQPalXUserBuilder.buildMockQPalXUserBuilder(newYorkMunicipality);

        // Create a QPalXSubscription that is only valid in USA and return that when service is invoked
        QPalXCountry usa = MockQPalXCountryBuilder.buildMockUSACountryBuilder();
        QPalXSubscription mockSubscription = MockQPalXSubscriptionBuilder.buildMockTestQPalXSubscription(usa);
        Mockito.when(iqPalxSubscriptionService.findQPalXSubscriptionByID(1L)).thenReturn(mockSubscription);

        // mock out call to return an  empty current subscription and active profile
        Mockito.when(iqPalxSubscriptionService.findActiveUserSubscriptionProfile(newYorkBasedQPalXUserBuilder)).thenReturn(Optional.empty());

        // Verify that an active UserSubscriptionProfile created
        Optional<UserSubscriptionProfile> subscriptionProfile = defaultQPalXUserSubscriptionService.addQPalXUserTutorialSubscriptionProfile(1L, newYorkBasedQPalXUserBuilder);
        Assert.assertTrue(subscriptionProfile.isPresent());
        Assert.assertTrue(subscriptionProfile.get().getSubscriptionStatusE() == SubscriptionStatusE.ACTIVE);
    }

    @Test
    public void testCreateNewQPalXUserWithTutorialSubscription() {
        // Build a mock QPalXUser with their Municipality in New York by default
        QPalXMunicipality newYorkMunicipality = MockQPalXMunicipalityBuilder.buildNewYorkQPalXMunicipalityBuilder();
        Mockito.when(iqPalXMunicipalityService.findQPalXMunicipalityByID(1L)).thenReturn(newYorkMunicipality);

        QPalXUser newYorkBasedQPalXUserBuilder = MockQPalXUserBuilder.buildMockQPalXUserBuilder(newYorkMunicipality);

        // Create a QPalXSubscription that is only valid in USA and return that when service is invoked
        QPalXCountry usa = MockQPalXCountryBuilder.buildMockUSACountryBuilder();
        QPalXSubscription mockSubscription = MockQPalXSubscriptionBuilder.buildMockTestQPalXSubscription(usa);
        Mockito.when(iqPalxSubscriptionService.findQPalXSubscriptionByID(1L)).thenReturn(mockSubscription);

        // mock out call to return an  empty current subscription and active profile
        Mockito.when(iqPalxSubscriptionService.findActiveUserSubscriptionProfile(Mockito.any())).thenReturn(Optional.empty());

        // Mock out Success ID creation
        Mockito.when(iqPalxUserService.generateQPalXUserSuccessID(Mockito.any())).thenReturn("US-NYC-12567");

        // Mock out TutorialService
        QPalXTutorialLevel tutorialLevel = new QPalXTutorialLevel();
        tutorialLevel.setTutorialLevel("Advanced");
        Mockito.when(iqPalXTutorialService.findQPalXTutorialLevelByID(1L)).thenReturn(tutorialLevel);

        Optional<QPalXUser> user = defaultQPalXUserSubscriptionService.createNewQPalXUserWithTutorialSubscription(new MockIQPalXUserVO(newYorkBasedQPalXUserBuilder));
        System.out.println("user = " + user);
    }

    private  class MockIQPalXUserVO implements IQPalXUserVO {

        private final QPalXUser qPalXUser;

        public MockIQPalXUserVO(QPalXUser qPalXUser) {
            this.qPalXUser = qPalXUser;
        }

        @Override
        public String getFirstName() {
            return qPalXUser.getFirstName();
        }

        @Override
        public String getLastName() {
            return qPalXUser.getLastName();
        }

        @Override
        public String getEmail() {
            return qPalXUser.getEmail();
        }

        @Override
        public String getPassword() {
            return qPalXUser.getPassword();
        }

        @Override
        public Long getMunicipalityID() {
            return 1L;
        }

        @Override
        public Long getSubscriptionID() {
            return 1L;
        }

        @Override
        public Long getEducationalInstitutionID() {
            return 1L;
        }

        @Override
        public Long getTutorialLevelID() {
            return 1L;
        }

        @Override
        public String getMPowerAccountAlias() {
            return "manyce400";
        }

        @Override
        public Long getPaymentSystemID() {
            return 1L;
        }
    }
}
