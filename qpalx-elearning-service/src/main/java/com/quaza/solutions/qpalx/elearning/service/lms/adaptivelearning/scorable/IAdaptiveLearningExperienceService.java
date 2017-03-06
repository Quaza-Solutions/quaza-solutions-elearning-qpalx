package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningExperienceService {


    public AdaptiveLearningExperience findByID(Long id);

    public List<AdaptiveLearningExperience> findAllAccrossELearningCurriculum(ELearningCurriculum eLearningCurriculum);

    public void buildAndSaveAdaptiveLearningExperience(QPalXUser qPalXUser, QPalXTutorialContentTypeE qPalXTutorialContentTypeE, Double proficiencyScore, Long scoreableActivityID);
}
