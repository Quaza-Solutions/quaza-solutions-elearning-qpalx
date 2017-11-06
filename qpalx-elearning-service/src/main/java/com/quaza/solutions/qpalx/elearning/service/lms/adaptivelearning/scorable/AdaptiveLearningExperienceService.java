package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.EmbedableScorableELearningActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningExperienceRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(AdaptiveLearningExperienceService.SPRING_BEAN_NAME)
public class AdaptiveLearningExperienceService implements IAdaptiveLearningExperienceService {



    @Autowired
    private IAdaptiveLearningExperienceRepository iAdaptiveLearningExperienceRepository;

    public static final String SPRING_BEAN_NAME = "quaza.solutions.qpalx.elearning.service.AdaptiveLearningExperienceService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningExperienceService.class);


    @Override
    public AdaptiveLearningExperience findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding AdaptiveLearningExperience with id: {}", id);
        return iAdaptiveLearningExperienceRepository.findOne(id);
    }

    @Transactional
    @Override
    public List<AdaptiveLearningExperience> findAllAccrossELearningCurriculum(ELearningCurriculum eLearningCurriculum, QPalXUser qPalXUser) {
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Finding all AdaptiveLearningExperience across Curriculum: {} for Student: {}", eLearningCurriculum.getCurriculumName(), qPalXUser.getEmail());

        List<AdaptiveLearningExperience> results = iAdaptiveLearningExperienceRepository.findAllAccrossELearningCurriculum(eLearningCurriculum.getId(), qPalXUser.getId());
        return results;
    }

    @Override
    public List<AdaptiveLearningExperience> findAllWithScorableActivityID(Long scorableActivityID, QPalXUser qPalXUser) {
        Assert.notNull(scorableActivityID, "scorableActivityID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Finding all AdaptiveLearningExperience for qpalXUser: {} with scorableActivityID: {}", qPalXUser.getEmail(), scorableActivityID);
        List<AdaptiveLearningExperience> results = iAdaptiveLearningExperienceRepository.findAllWithScorableActivityID(scorableActivityID, qPalXUser.getId());
        return results;
    }

    @Override
    public List<AdaptiveLearningExperience> findAllQuizLearningExperiencesForStudent(Long scorableActivityID, QPalXUser qPalXUser) {
        Assert.notNull(scorableActivityID, "scorableActivityID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Finding all Quiz AdaptiveLearningExperience for qpalXUser: {} with scorableActivityID: {}", qPalXUser.getEmail(), scorableActivityID);
        List<AdaptiveLearningExperience> results = iAdaptiveLearningExperienceRepository.findAllQuizLearningExperiencesForStudent(scorableActivityID, qPalXUser.getId());
        return results;
    }

    @Transactional
    @Override
    public void deleteAllQuizLearningExperiences(Long scorableActivityID) {
        Assert.notNull(scorableActivityID, "scorableActivityID cannot be null");
        LOGGER.debug("Deleting all Quiz Learning Experiences for Quiz with ID: {}", scorableActivityID);
        List<AdaptiveLearningExperience> results = iAdaptiveLearningExperienceRepository.findAllForQuizID(scorableActivityID);
        if (results != null && results.size() > 0) {
            iAdaptiveLearningExperienceRepository.delete(results);
        }
    }

    @Transactional
    @Override
    public void deleteAllQuizLearningExperiencesForStudent(Long scorableActivityID, QPalXUser qPalXUser) {
        Assert.notNull(scorableActivityID, "scorableActivityID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Deleting all Quiz AdaptiveLearningExperience for qpalXUser: {} with scorableActivityID: {}", qPalXUser.getEmail(), scorableActivityID);

        List<AdaptiveLearningExperience> results = iAdaptiveLearningExperienceRepository.findAllQuizLearningExperiencesForStudent(scorableActivityID, qPalXUser.getId());
        if (results != null && results.size() > 0) {
            iAdaptiveLearningExperienceRepository.delete(results);
        }
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
