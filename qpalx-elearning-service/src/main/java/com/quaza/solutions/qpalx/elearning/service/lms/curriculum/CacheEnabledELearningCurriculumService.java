package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCurriculumRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
public class CacheEnabledELearningCurriculumService implements IELearningCurriculumService {


    @Autowired
    private IELearningCurriculumRepository ieLearningCurriculumRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CacheEnabledELearningCurriculumService.class);

    @Override
    public ELearningCurriculum findByELearningCurriculumName(String curriculumName) {
        Assert.notNull(curriculumName, "curriculumName cannot be null");
        LOGGER.debug("Finding ELearningCurriculum with curriculumName: {}", curriculumName);
        return ieLearningCurriculumRepository.findByELearningCurriculumName(curriculumName);
    }

    @Override
    public List<ELearningCurriculum> findAllCurriculumByType(CurriculumType curriculumType) {
        return null;
    }

    @Override
    public List<ELearningCurriculum> findAllCurriculumByRegionAndType(CurriculumType curriculumType, GeographicalRegion geographicalRegion) {
        return null;
    }

    @Override
    public List<ELearningCurriculum> findAllCurriculumByRegionTutorialLevelAndType(CurriculumType curriculumType, QPalXTutorialLevel qPalXTutorialLevel, GeographicalRegion geographicalRegion) {
        return null;
    }
}
