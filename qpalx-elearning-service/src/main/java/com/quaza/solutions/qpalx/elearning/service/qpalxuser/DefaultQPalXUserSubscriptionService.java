package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.payment.electronic.repository.IEPaymentServiceTransactionRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.IQPalXUserVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserSexE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfileBuilder;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IUserSubscriptionProfileRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.repository.IQPalxUserRepository;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionStatusE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.IQPalXTutorialService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultQPalXUserSubscriptionService")
public class DefaultQPalXUserSubscriptionService implements IQPalXUserSubscriptionService {



    @Autowired
    private IUserSubscriptionProfileRepository iUserSubscriptionProfileRepository;


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
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
    private IQPalxUserService iqPalxUserService;

    @Autowired
    private IQPalxUserRepository iqPalxUserRepository;


    @Autowired
    private IEPaymentServiceTransactionRepository iePaymentServiceTransactionRepository;



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalXUserSubscriptionService.class);

    @Override
    public Optional<QPalXUser> createNewQPalXUserWithTutorialSubscription(IQPalXUserVO iqPalXUserVO) {
        Assert.notNull(iqPalXUserVO, "iqPalXUserVO cannot be null");
        LOGGER.info("Creating QPalXUser from iqPalXUserVO:> {}", iqPalXUserVO);

        // Get  user municipality
        QPalXMunicipality municipality = iqPalXMunicipalityService.findQPalXMunicipalityByID(iqPalXUserVO.getMunicipalityID());

        // Get user's tutorial level
        QPalXTutorialLevel qPalXTutorialLevel = iqPalXTutorialService.findQPalXTutorialLevelByID(iqPalXUserVO.getTutorialLevelID());

        QPalXUser qPalXUser = QPalXUser.builder()
                .firstName(iqPalXUserVO.getFirstName())
                .lastName(iqPalXUserVO.getLastName())
                .email(iqPalXUserVO.getEmail())
                .password(iqPalXUserVO.getPassword())
                .municipality(municipality)
                .qPalXTutorialLevel(qPalXTutorialLevel)
                .accountLockedStatus(false) // By default always create a new account unlocked
                .qPalxUserSexE(QPalxUserSexE.Male)
                .qPalxUserTypeE(QPalxUserTypeE.STUDENT)
                .build();

        // Create new User Subscription profile
        Optional<UserSubscriptionProfile> userSubscriptionProfile = addQPalXUserTutorialSubscriptionProfile(iqPalXUserVO.getSubscriptionID(), qPalXUser);
        if(userSubscriptionProfile.isPresent()) {
            // Add new subscription details for user.  Required for generating a Success ID
            qPalXUser.addUserSubscriptionProfile(userSubscriptionProfile.get());

            // Generate a new Success ID for User and persist the user
            String successID = iqPalxUserService.generateQPalXUserSuccessID(qPalXUser);
            LOGGER.info("SuccessID: {} has been created for user", successID);
            qPalXUser.setSuccessID(successID);
            iqPalxUserService.saveQPalXUser(qPalXUser);
            return Optional.of(qPalXUser);
        }

        LOGGER.warn("Cannot create new QPalXUser from: {} with subscription, problems encountered during creation of UserSubscriptionProfile", iqPalXUserVO);
        return Optional.empty();
    }

    @Override
    @Transactional
    public boolean renewQPalXUserSubscription(QPalXUser qPalXUser, QPalXSubscription subscription) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(subscription, "subscription cannot be null");

        System.out.println("\ndamn it");

        // Need to reload this user and get the UserSubscriptionProfile collection which is lazily loaded
        //qPalXUser = iqPalxUserRepository.findQPalxUserBySuccessIDAndFetchUserSubscriptionProfile(qPalXUser.getSuccessID());

        LOGGER.info("Renewing subscription for Student: {} with new subscription: {}", qPalXUser.getEmail(), subscription.getSubscriptionName());

        Optional<UserSubscriptionProfile> userSubscriptionProfile = addQPalXUserTutorialSubscriptionProfile(subscription.getId(), qPalXUser);
        if(userSubscriptionProfile.isPresent()) {
            // Add new subscription details to QPalXUser Subscription profile and save
            LOGGER.info("Saving QPalX User: {} new subscription information", qPalXUser.getEmail(), subscription.getSubscriptionName());
            iUserSubscriptionProfileRepository.save(userSubscriptionProfile.get());
            return true;
        } else {
            LOGGER.warn("Failed to renew subscription for User: {} with Subscription: {}", qPalXUser.getEmail(), subscription.getSubscriptionName());
            return false;
        }
    }

    @Override
    public Optional<UserSubscriptionProfile> addQPalXUserTutorialSubscriptionProfile(Long subscriptionID, QPalXUser qPalXUser) {
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
        Optional<UserSubscriptionProfile> activeUserSubscriptionProfile = Optional.empty();
        if (qPalXUser.getId() != null) {
            LOGGER.info("QPalXUser is a persisted user, looking up active current subscription...");
            activeUserSubscriptionProfile = iqPalxSubscriptionService.findActiveUserSubscriptionProfile(qPalXUser);
        }

        if(!activeUserSubscriptionProfile.isPresent()) {
            LOGGER.info("Adding new UserSubscriptionProfile with subscriptionID: {} to QPalXUser: {}", subscriptionID, qPalXUser);

            // calculate subscription expiry date
            DateTime subscriptionExpiryDate = iqPalxSubscriptionService.calculateSubscriptionExpiryDateFromToday(subscription);
            LOGGER.info("New subscription for user: {} will expire on: {}", qPalXUser.getEmail(), subscriptionExpiryDate);

            // Build UserSubscriptionProfile
            UserSubscriptionProfileBuilder userSubscriptionProfileBuilder = new UserSubscriptionProfileBuilder()
                    .addQPalXSubscription(subscription)
                    .addQPalXUser(qPalXUser)
                    .addSubscriptionPurchasedDate(new DateTime())
                    .addSubscriptionExpirationDate(subscriptionExpiryDate)
                    .addSubscriptionStatusE(SubscriptionStatusE.ACTIVE);
            return Optional.of(userSubscriptionProfileBuilder.get());
        }

        LOGGER.warn("Found an already active UserSubscriptionProfile: {} cannot create new one", activeUserSubscriptionProfile.get());
        return Optional.empty();
    }


}

