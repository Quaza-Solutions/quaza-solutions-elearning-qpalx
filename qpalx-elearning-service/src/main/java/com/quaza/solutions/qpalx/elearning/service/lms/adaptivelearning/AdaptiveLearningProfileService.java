package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository.IAdaptiveLearningProfileRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLearningProfileService")
public class AdaptiveLearningProfileService implements IAdaptiveLearningProfileService {


    @Autowired
    public IAdaptiveLearningProfileRepository iAdaptiveLearningProfileRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningProfileService.class);


    @Override
    @Transactional
    public void save(AdaptiveLearningProfile adaptiveLearningProfile) {
        Assert.notNull(adaptiveLearningProfile, "adaptiveLearningProfile cannot be null");
        LOGGER.info("Saving new adaptiveLearningProfile to database:> {}", adaptiveLearningProfile);
        iAdaptiveLearningProfileRepository.save(adaptiveLearningProfile);
    }

    @Override
    @Transactional
    public void delete(AdaptiveLearningProfile adaptiveLearningProfile) {
        Assert.notNull(adaptiveLearningProfile, "adaptiveLearningProfile cannot be null");
        LOGGER.info("Deleting adaptiveLearningProfile from database:> {}", adaptiveLearningProfile);
        iAdaptiveLearningProfileRepository.delete(adaptiveLearningProfile);
    }

    @Override
    public Optional<AdaptiveLearningProfile> findAdaptiveLearningProfileForQPalxUser(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding AdaptiveLearningProfile for QPalxUser: {}", qPalXUser.getEmail());
        AdaptiveLearningProfile adaptiveLearningProfile = iAdaptiveLearningProfileRepository.findAdaptiveLearningProfileForQPalxUser(qPalXUser);
        Optional optional = adaptiveLearningProfile != null ? Optional.of(adaptiveLearningProfile) : Optional.empty();
        return optional;
    }

    @Override
    public Optional<AdaptiveLearningProfile> buildNewAdaptiveLearningProfileForQPalxUser(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Attempting to build new AdaptiveLearningProfile for QPalxUser: {}", qPalXUser.getEmail());

        // Check to see IF user already has a profile
        Optional<AdaptiveLearningProfile> userAdaptiveLearningProfile = findAdaptiveLearningProfileForQPalxUser(qPalXUser);
        if(!userAdaptiveLearningProfile.isPresent()) {
            AdaptiveLearningProfile adaptiveLearningProfile = AdaptiveLearningProfile.builder()
                    .learningProfileStartDate(new DateTime())
                    .qpalxUser(qPalXUser)
                    .build();
            LOGGER.info("Completed building new adaptiveLearningProfile: {}", adaptiveLearningProfile);
            return Optional.of(adaptiveLearningProfile);
        }

        LOGGER.warn("QPalxUser with email:> {} already has an AdaptiveLearningProfile, cannot create new one", qPalXUser.getEmail());
        return Optional.empty();
    }
}
