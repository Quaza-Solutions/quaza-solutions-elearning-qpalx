package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.UserSubscriptionProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IUserSubscriptionProfileRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.repository.IQPalxUserRepository;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultQPalxUserService")
public class DefaultQPalxUserService implements IQPalxUserService {


    @Autowired
    private IQPalxUserRepository iqPalxUserRepository;

    @Autowired
    private IUserSubscriptionProfileRepository iUserSubscriptionProfileRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iqPalXMunicipalityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iqPalxSubscriptionService;


    public static final int INSTITUTION_AFFILIATED_STUDENT_ID_LENGTH = 5;

    public static final int STAND_ALONE_STUDENT_ID_LENGTH = 7;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalxUserService.class);



    @Transactional
    @Override
    public void saveQPalXUser(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Saving qPalXUser: {}", qPalXUser);
        iqPalxUserRepository.save(qPalXUser);
    }

    @Transactional
    @Override
    public void deleteQPalXUser(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(qPalXUser.getId(), "qPalXUser does not have a valid unique ID");
        LOGGER.info("Deleting qPalXUser: {}", qPalXUser);
        iqPalxUserRepository.delete(qPalXUser.getId());
    }

    @Override
    public QPalXUser findQPalXUserByEmail(String userEmail) {
        Assert.notNull(userEmail, "userEmail cannot be null");
        LOGGER.info("Finding QPalXUser with email:> {}", userEmail);
        QPalXUser qPalXUser = iqPalxUserRepository.findQPalxUserByEmail(userEmail);
        return qPalXUser;
    }

    @Override
    public QPalXUser findQPalXUserBySuccessID(String successID) {
        Assert.notNull(successID, "successID cannot be null");
        LOGGER.info("Finding QPalXUser with email:> {}", successID);
        QPalXUser qPalXUser = iqPalxUserRepository.findQPalxUserBySuccessID(successID);
        return qPalXUser;
    }

    @Override
    public String generateQPalXUserSuccessID(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Generating SuccessID for qPalXUser:> {}", qPalXUser);

        Optional<UserSubscriptionProfile> activeUserSubscriptionProfile = Optional.empty();
        if (qPalXUser.getId() != null) {
            LOGGER.info("QPalXUser is a persisted user, checking db for active subscription profile..");
            activeUserSubscriptionProfile = iqPalxSubscriptionService.findActiveUserSubscriptionProfile(qPalXUser);
        } else {
            // Newly created QPalXUser as such we expect there to be only one new UserSubscriptionProfile created
            LOGGER.info("QPalXUser is a non persisted user, attempting to retrieve subscription profile fron QPalXUser object");
            activeUserSubscriptionProfile = Optional.of(qPalXUser.getUserSubscriptionProfiles().iterator().next());
        }

        if(activeUserSubscriptionProfile.isPresent()) {
            QPalXMunicipality municipality = qPalXUser.getQPalXMunicipality();
            QPalXEducationalInstitution educationalInstitution = activeUserSubscriptionProfile.get().getEducationalInstitution();

            if(municipality != null && educationalInstitution != null) {
                return generateQPalxStudentSID(municipality, educationalInstitution);
            } else if(municipality != null) {
                return generateQPalxStudentSID(municipality);
            } else {
                // We dont have the basic requirements to generate Success ID.  Throw exception
                throw new UnsupportedOperationException("Cannot generate Success ID.  Both Country and Municipality are required");
            }
        }

        throw new UnsupportedOperationException("Cannot generate Success ID for User without a valid QPalX Subscription");
    }


    @Override
    public List<QPalXUser> findAllQPalXUsersWithPageableResults(Pageable pageable) {
        Pageable pageable1 = new PageRequest(1, 10);
        return iqPalxUserRepository.findAllQPalXUsersWithPageableResults(pageable1);
    }

    public String generateQPalxStudentSID(QPalXMunicipality municipality) {
        QPalXCountry country = municipality.getQPalXCountry();
        String uniqueStudentID = generateUniqueQPalXStudentID(STAND_ALONE_STUDENT_ID_LENGTH);
        StringBuffer sID = new StringBuffer();
        sID.append(country.getCountryCode())
                .append("-")
                .append(municipality.getCode())
                .append("-")
                .append(uniqueStudentID)
                .toString();
        return sID.toString();
    }

    /**
     * Student S-ID = CountryCode-MunicipalCode-SchoolID-UniqueStudentID
     * Example: GH-ACC-005678-23450 GH-ACC-MKT105-298407
     */
    public String generateQPalxStudentSID(QPalXMunicipality municipality, QPalXEducationalInstitution educationalInstitution) {
        QPalXCountry country = municipality.getQPalXCountry();
        String uniqueStudentID = generateUniqueQPalXStudentID(INSTITUTION_AFFILIATED_STUDENT_ID_LENGTH);
        StringBuffer sID = new StringBuffer();
        sID.append(country.getCountryCode())
                .append("-")
                .append(municipality.getCode())
                .append("-")
                .append(educationalInstitution.getCode())
                .append("-")
                .append(uniqueStudentID)
                .toString();
        return sID.toString();
    }

    private String generateUniqueQPalXStudentID(int sIDLength) {
        Random random = new Random();
        char[] digits = new char[sIDLength];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < sIDLength; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }

        return ""+Long.parseLong(new String(digits));
    }

}