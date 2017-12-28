package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.repository.IEPaymentServiceTransactionRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.*;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentSubscriptionProfileBuilder;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IStudentSubscriptionProfileRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.repository.IQPalxUserRepository;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.SimplifiedProficiencyRankE;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.institutions.DefaultQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.CacheEnabledELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.IStudentEnrollmentRecordService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author manyce400
 */
@Service(DefaultQPalXUserSubscriptionService.BEAN_NAME)
public class DefaultQPalXUserSubscriptionService implements IQPalXUserSubscriptionService {



    @Autowired
    private IQPalxUserRepository iqPalxUserRepository;

    @Autowired
    private IStudentSubscriptionProfileRepository iStudentSubscriptionProfileRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iqPalXMunicipalityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iqPalxSubscriptionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
    private IQPalXTutorialService iqPalXTutorialService;

    @Autowired
    @Qualifier(DefaultQPalxUserService.BEAN_NAME)
    private IQPalxUserService iqPalxUserService;

    @Autowired
    @Qualifier(DefaultQPalXEducationalInstitutionService.SPRING_BEAN)
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultStudentEnrollmentRecordService")
    private IStudentEnrollmentRecordService iStudentEnrollmentRecordService;

    @Autowired
    private IEPaymentServiceTransactionRepository iePaymentServiceTransactionRepository;

    @Autowired
    @Qualifier(CacheEnabledELearningCurriculumService.BEAN_NAME)
    private IELearningCurriculumService ieLearningCurriculumService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService")
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    public static final String BEAN_NAME = "quaza.solutions.qpalx.elearning.service.DefaultQPalXUserSubscriptionService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalXUserSubscriptionService.class);


    @Override
    public void updateQPalXUserInfo(QPalXUser qPalXUser, IQPalXUserVO iqPalXUserVO) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(iqPalXUserVO, "iqPalXUserVO cannot be null");

        LOGGER.info("Updating account info details for user: {} ...", qPalXUser.getEmail());

        qPalXUser.setEmail(iqPalXUserVO.getEmail());
        qPalXUser.setMobilePhoneNumber(iqPalXUserVO.getMobilePhoneNumber());
        qPalXUser.setPassword(iqPalXUserVO.getPassword());
        iqPalxUserRepository.save(qPalXUser);
    }

    @Override
    @Transactional
    public Optional<QPalXUser> createNewQPalXUser(IQPalXUserVO iqPalXUserVO, QPalxUserTypeE qPalxUserTypeE) {
        Assert.notNull(iqPalXUserVO, "iqPalXUserVO cannot be null");
        Assert.notNull(qPalxUserTypeE, "qPalxUserTypeE cannot be null");
        LOGGER.info("Creating new QPalXUser without a subscription for qPalxUserTypeE: {}", qPalxUserTypeE);

        // Get  user municipality
        QPalXMunicipality municipality = iqPalXMunicipalityService.findQPalXMunicipalityByID(iqPalXUserVO.getMunicipalityID());

        QPalxUserSexE qPalxUserSexE = QPalxUserSexE.valueOf(iqPalXUserVO.getUserSex());

        QPalXUser qPalXUser = QPalXUser.builder()
                .firstName(iqPalXUserVO.getFirstName())
                .lastName(iqPalXUserVO.getLastName())
                .email(iqPalXUserVO.getEmail())
                .password(iqPalXUserVO.getPassword())
                .municipality(municipality)
                .mobilePhoneNumber(iqPalXUserVO.getMobilePhoneNumber())
                .accountLockedStatus(false) // By default always create a new account unlocked
                .qPalxUserSexE(qPalxUserSexE)
                .qPalxUserTypeE(qPalxUserTypeE)
                .build();

        iqPalxUserService.saveQPalXUser(qPalXUser);
        return Optional.of(qPalXUser);
    }

    @Override
    @Transactional
    public Optional<QPalXUser> createNewQPalXUserWithTutorialSubscription(IQPalXUserVO iqPalXUserVO) {
        Assert.notNull(iqPalXUserVO, "iqPalXUserVO cannot be null");
        Assert.notNull(iqPalXUserVO.getUserSex(), "user sex cannot be null");
        LOGGER.info("Creating new Student QPalXUser with subscription from iqPalXUserVO:> {}", iqPalXUserVO);

        // Get  user municipality
        QPalXMunicipality municipality = iqPalXMunicipalityService.findQPalXMunicipalityByID(iqPalXUserVO.getMunicipalityID());

        QPalxUserSexE qPalxUserSexE = QPalxUserSexE.valueOf(iqPalXUserVO.getUserSex());

        QPalXUser qPalXUser = QPalXUser.builder()
                .firstName(iqPalXUserVO.getFirstName())
                .lastName(iqPalXUserVO.getLastName())
                .email(iqPalXUserVO.getEmail())
                .password(iqPalXUserVO.getPassword())
                .municipality(municipality)
                .mobilePhoneNumber(iqPalXUserVO.getMobilePhoneNumber())
                .accountLockedStatus(false) // By default always create a new account unlocked
                .qPalxUserSexE(qPalxUserSexE)
                .qPalxUserTypeE(QPalxUserTypeE.STUDENT)
                .build();

        // Create new User Subscription profile
        Optional<StudentSubscriptionProfile> userSubscriptionProfile = addQPalXUserTutorialSubscriptionProfile(iqPalXUserVO.getSubscriptionID(), qPalXUser);
        if(userSubscriptionProfile.isPresent()) {
            // Add new subscription details for user.  Required for generating a Success ID
            qPalXUser.addUserSubscriptionProfile(userSubscriptionProfile.get());

            // Generate a new Success ID for User and persist the user
            String successID = iqPalxUserService.generateQPalXUserSuccessID(qPalXUser);
            LOGGER.info("SuccessID: {} has been created for user", successID);
            qPalXUser.setSuccessID(successID);
            iqPalxUserService.saveQPalXUser(qPalXUser);

            // Create StudentEnrolmentRecord
            StudentTutorialGrade studentTutorialGrade = iqPalXTutorialService.findTutorialGradeByID(iqPalXUserVO.getTutorialGradeID());
            QPalXEducationalInstitution qPalXEducationalInstitution = null;
            if (iqPalXUserVO.getEducationalInstitutionID() != null) {
                qPalXEducationalInstitution = iqPalXEducationalInstitutionService.findByID(iqPalXUserVO.getEducationalInstitutionID());
            }
            iStudentEnrollmentRecordService.createStudentEnrolmentRecord(qPalXUser, studentTutorialGrade, qPalXEducationalInstitution);

            // Build and save the Student's default initial AdaptiveProficiency rankinngs
            buildAndSaveInitialAdaptiveProficiencyRaning(qPalXUser, studentTutorialGrade);
            return Optional.of(qPalXUser);
        }

        LOGGER.warn("Cannot create new QPalXUser from: {} with subscription, problems encountered during creation of StudentSubscriptionProfile", iqPalXUserVO);
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<StudentSubscriptionProfile> renewQPalXUserSubscription(String email, QPalXSubscription subscription) {
        Assert.notNull(email, "email cannot be null");
        Assert.notNull(subscription, "subscription cannot be null");
        QPalXUser qPalXUser = iqPalxUserService.findQPalXUserByEmail(email);
        return renewQPalXUserSubscription(qPalXUser, subscription);
    }

    @Override
    @Transactional
    public Optional<StudentSubscriptionProfile> renewQPalXUserSubscription(QPalXUser qPalXUser, QPalXSubscription subscription) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(subscription, "subscription cannot be null");

        // Close out the currently Active StudentSubscriptionProfile
        iqPalxSubscriptionService.inValidateCurrentUserSubscriptionProfile(qPalXUser);
        LOGGER.info("Renewing subscription for Student: {} with new subscription: {}", qPalXUser.getEmail(), subscription.getSubscriptionName());

        Optional<StudentSubscriptionProfile> studentSubscriptionProfile = addQPalXUserTutorialSubscriptionProfile(subscription.getId(), qPalXUser);
        if(studentSubscriptionProfile.isPresent()) {
            iqPalxSubscriptionService.saveStudentSubscriptionProfile(studentSubscriptionProfile.get());
            return studentSubscriptionProfile;
        }

        LOGGER.warn("Failed to renew subscription for User: {} with Subscription: {}", qPalXUser.getEmail(), subscription.getSubscriptionName());
        return Optional.empty();
    }

    @Override
    public Optional<StudentSubscriptionProfile> addQPalXUserTutorialSubscriptionProfile(Long subscriptionID, QPalXUser qPalXUser) {
        Assert.notNull(subscriptionID, "subscriptionID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        // First check validate that the subscription country matches the current country based off user's municipality
        QPalXSubscription subscription = iqPalxSubscriptionService.findQPalXSubscriptionByID(subscriptionID);
        QPalXCountry subscriptionCountry = subscription.getSubscriptionQPalXCountry();
        QPalXCountry qPalXUserCountry = qPalXUser.getQPalXMunicipality().getQPalXCountry();

        if(!subscriptionCountry.equals(qPalXUserCountry)) {
            LOGGER.warn("Cannot add UserSubscription profile. Subscription Country: {} does not match the QPalX User's Country: {}", subscriptionCountry.getCountryName(), qPalXUserCountry.getCountryName());
            return Optional.empty();
        }


        // IF this is an already existing user with an ID, make sure that this user does not already have a valid active subscription to prevent duplicate data.
        Optional<StudentSubscriptionProfile> activeUserSubscriptionProfile = Optional.empty();
        if (qPalXUser.getId() != null) {
            LOGGER.info("QPalXUser is a persisted user, looking up active current subscription...");
            activeUserSubscriptionProfile = iqPalxSubscriptionService.findActiveUserSubscriptionProfile(qPalXUser);
        }

        if(!activeUserSubscriptionProfile.isPresent()) {
            LOGGER.info("Adding new StudentSubscriptionProfile with subscriptionID: {} to QPalXUser: {}", subscriptionID, qPalXUser);

            // calculate subscription expiry date
            DateTime subscriptionExpiryDate = iqPalxSubscriptionService.calculateSubscriptionExpiryDateFromToday(subscription);
            LOGGER.info("New subscription for user: {} will expire on: {}", qPalXUser.getEmail(), subscriptionExpiryDate);

            // Build StudentSubscriptionProfile
            StudentSubscriptionProfileBuilder userSubscriptionProfileBuilder = new StudentSubscriptionProfileBuilder()
                    .addQPalXSubscription(subscription)
                    .addQPalXUser(qPalXUser)
                    .addSubscriptionPurchasedDate(new DateTime())
                    .addSubscriptionExpirationDate(subscriptionExpiryDate)
                    .addSubscriptionStatusE(SubscriptionStatusE.ACTIVE);
            return Optional.of(userSubscriptionProfileBuilder.get());
        }

        LOGGER.warn("Found an already active StudentSubscriptionProfile: {} cannot create new one", activeUserSubscriptionProfile.get());
        return Optional.empty();
    }

    void buildAndSaveInitialAdaptiveProficiencyRaning(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade) {
        LOGGER.info("Building and saving all student users Core and Elective Curriculum proficiency rankings which will be defaulted to Beginner.");

        // Find all Core and Elective curriculum for the selected StudentTutorialGrade
        List<ELearningCurriculum> coreELearningCurricula = ieLearningCurriculumService.findAllCurriculumByTutorialGradeAndType(CurriculumType.CORE, studentTutorialGrade);
        List<ELearningCurriculum> electiveELearningCurricula = ieLearningCurriculumService.findAllCurriculumByTutorialGradeAndType(CurriculumType.ELECTIVE, studentTutorialGrade);

        // Build and save all Core Curriculum proficiency rankings
        for(ELearningCurriculum eLearningCurriculum : coreELearningCurricula) {
            iAdaptiveProficiencyRankingService.buildAndSaveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum, SimplifiedProficiencyRankE.Beginner);
        }

        // Build and save all Elective Curriculum proficiency rankings
        for(ELearningCurriculum eLearningCurriculum : electiveELearningCurricula) {
            iAdaptiveProficiencyRankingService.buildAndSaveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum, SimplifiedProficiencyRankE.Beginner);
        }
    }


    void addEducationalInstitutionDetails(Long educationalInstitutionID, QPalXUser qPalXUser) {
        Assert.notNull(educationalInstitutionID, "educationalInstitutionID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser");
        LOGGER.debug("Adding Educational institution details for user: {}", qPalXUser.getEmail());

        QPalXEducationalInstitution qPalXEducationalInstitution = iqPalXEducationalInstitutionService.findByID(educationalInstitutionID);
        if(qPalXEducationalInstitution != null) {
            UserEducationalInstitutions userEducationalInstitutions = new UserEducationalInstitutions();
            userEducationalInstitutions.setQpalxUser(qPalXUser);
            userEducationalInstitutions.setqPalXEducationalInstitution(qPalXEducationalInstitution);
            userEducationalInstitutions.setEffectiveDate(new DateTime());
        }
    }


}

