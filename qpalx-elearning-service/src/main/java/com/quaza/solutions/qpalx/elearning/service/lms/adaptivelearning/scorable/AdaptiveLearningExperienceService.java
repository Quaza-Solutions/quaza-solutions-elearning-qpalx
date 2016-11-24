package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.EmbedableScorableELearningActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningExperienceRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLearningExperienceService")
public class AdaptiveLearningExperienceService implements IAdaptiveLearningExperienceService {




    @Autowired
    private IAdaptiveLearningExperienceRepository iAdaptiveLearningExperienceRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningExperienceService.class);


    @Override
    public AdaptiveLearningExperience findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding AdaptiveLearningExperience with id: {}", id);
        return iAdaptiveLearningExperienceRepository.findOne(id);
    }

    @Transactional
    @Override
    public void buildAndSaveAdaptiveLearningExperience(QPalXUser qPalXUser, QPalXTutorialContentTypeE qPalXTutorialContentTypeE, Double proficiencyScore, Long scoreableActivityID) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(qPalXTutorialContentTypeE, "qPalXTutorialContentTypeE cannot be null");
        Assert.notNull(proficiencyScore, "proficiencyScore cannot be null");
        Assert.notNull(scoreableActivityID, "scorableActivityID cannot be null");

        LOGGER.info("Saving new AdaptiveLearningExperience for qPalXUser: {}", qPalXUser.getEmail());

        EmbedableScorableELearningActivity embedableScorableELearningActivity = EmbedableScorableELearningActivity.builder()
                .scorableActivityID(scoreableActivityID)
                .qPalXTutorialContentTypeE(qPalXTutorialContentTypeE)
                .build();

        AdaptiveLearningExperience adaptiveLearningExperience = AdaptiveLearningExperience.builder()
                .qpalxUser(qPalXUser)
                .proficiencyScore(proficiencyScore)
                .learningExperienceStartDate(new DateTime())
                .learningExperienceCompletedDate(new DateTime())
                .embedableScorableELearningActivity(embedableScorableELearningActivity)
                .build();

        iAdaptiveLearningExperienceRepository.save(adaptiveLearningExperience);
    }

}
