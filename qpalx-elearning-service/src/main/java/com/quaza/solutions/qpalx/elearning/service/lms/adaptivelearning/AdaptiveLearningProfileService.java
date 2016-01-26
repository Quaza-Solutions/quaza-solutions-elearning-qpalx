package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository.IAdaptiveLearningProfileRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.services.AdaptiveLearningProfileService")
public class AdaptiveLearningProfileService implements IAdaptiveLearningProfileService {


    @Autowired
    public IAdaptiveLearningProfileRepository iAdaptiveLearningProfileRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningProfileService.class);


    @Override
    public AdaptiveLearningProfile findAdaptiveLearningProfileForQPalxUser(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding AdaptiveLearningProfile for QPalxUser: {}", qPalXUser.getEmail());
        AdaptiveLearningProfile adaptiveLearningProfile = iAdaptiveLearningProfileRepository.findAdaptiveLearningProfileForQPalxUser(qPalXUser);
        return adaptiveLearningProfile;
    }
}
