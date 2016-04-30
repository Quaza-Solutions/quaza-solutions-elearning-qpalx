package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXCountryBuilder;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXMunicipalityBuilder;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXSubscriptionBuilder;
import com.quaza.solutions.qpalx.elearning.service.mock.MockQPalXUserBuilder;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.IStudentEnrolmentRecordService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

/**
 * @RunWith annotation will initialize all Mockito mocks ahead of all test runs
 *
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
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

    @Mock
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;

    @Mock
    private IStudentEnrolmentRecordService iStudentEnrolmentRecordService;


    private MockQPalXUserBuilder mockQPalXUserBuilder = new MockQPalXUserBuilder();



    @Test
    public void testAddQPalXUserTutorialSubscriptionProfileWhenUserCountryDoesNotMatchSubscription() {
        // Build a mock QPalXUser with their Municipality in New York by default
        QPalXMunicipality accraMunicipality = MockQPalXMunicipalityBuilder.buildNewYorkQPalXMunicipalityBuilder();
        QPalXUser newYorkBasedQPalXUser = MockQPalXUserBuilder.buildMockQPalXUserBuilder(accraMunicipality);

        // Create a QPalXSubscription that is only valid in Ghana and return that when service is invoked
        QPalXCountry ghana = MockQPalXCountryBuilder.buildMockGhanaCountryBuilder();
        QPalXSubscription mockSubscription = MockQPalXSubscriptionBuilder.buildMockTestQPalXSubscription(ghana);
        Mockito.when(iqPalxSubscriptionService.findQPalXSubscriptionByID(1L)).thenReturn(mockSubscription);

        // Execute call and verify that no StudentSubscriptionProfile has been created since the user's Country will not match that on the subscription.
        // newYorkBasedQPalXUser country is USA while Subscription is specifically for Ghana.  Users can only subscribe to Subscriptions in their own Country.
        Optional<StudentSubscriptionProfile> subscriptionProfile = defaultQPalXUserSubscriptionService.addQPalXUserTutorialSubscriptionProfile(1L, newYorkBasedQPalXUser);
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

        // Assign an active StudentSubscriptionProfile to simulate a scenario where this user is already a registered user
        // This will require us setting an ID to signify that this user is already persistent in the database.
        newYorkBasedQPalXUserBuilder.setId(1L);

        MockQPalXUserBuilder.assignActiveUserSubscriptionProfile(newYorkBasedQPalXUserBuilder);
        StudentSubscriptionProfile studentSubscriptionProfile = newYorkBasedQPalXUserBuilder.getStudentSubscriptionProfiles().iterator().next();
        Mockito.when(iqPalxSubscriptionService.findActiveUserSubscriptionProfile(newYorkBasedQPalXUserBuilder)).thenReturn(Optional.of(studentSubscriptionProfile));

        // Verify that no StudentSubscriptionProfile created since user already has an active StudentSubscriptionProfile
        Optional<StudentSubscriptionProfile> subscriptionProfile = defaultQPalXUserSubscriptionService.addQPalXUserTutorialSubscriptionProfile(1L, newYorkBasedQPalXUserBuilder);
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

        // Verify that an active StudentSubscriptionProfile created
        Optional<StudentSubscriptionProfile> subscriptionProfile = defaultQPalXUserSubscriptionService.addQPalXUserTutorialSubscriptionProfile(1L, newYorkBasedQPalXUserBuilder);
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
        StudentTutorialGrade studentTutorialGrade = new StudentTutorialGrade();
        Mockito.when(iqPalXTutorialService.findTutorialGradeByID(1L)).thenReturn(studentTutorialGrade);

        Optional<QPalXUser> user = defaultQPalXUserSubscriptionService.createNewQPalXUserWithTutorialSubscription(new MockIQPalXUserVO(newYorkBasedQPalXUserBuilder));
        Assert.assertNotNull(user.get());

        // Validate all properties of user returned are as expected.
        Assert.assertEquals(newYorkMunicipality,  user.get().getQPalXMunicipality());
        Assert.assertEquals("US-NYC-12567", user.get().getSuccessID());
        //Assert.assertEquals(studentTutorialGrade, user.get().getStudentTutorialGradeLevels().iterator().next().getStudentTutorialGrade());
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
        public Long getTutorialGradeID() {
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

        @Override
        public String getMobilePhoneNumber() {
            return qPalXUser.getMobilePhoneNumber();
        }

    }
}
