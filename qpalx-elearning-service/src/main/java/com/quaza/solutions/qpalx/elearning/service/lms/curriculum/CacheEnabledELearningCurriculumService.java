package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCurriculumRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
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
    public ELearningCurriculum findByELearningCurriculumID(Long curriculumID) {
        Assert.notNull(curriculumID, "curriculumID cannot be null");
        LOGGER.debug("Finding curriculum by ID: {}", curriculumID);
        return ieLearningCurriculumRepository.findOne(curriculumID);
    }

    @Override
    public ELearningCurriculum findByELearningCurriculumName(String curriculumName) {
        Assert.notNull(curriculumName, "curriculumName cannot be null");
        LOGGER.debug("Finding ELearningCurriculum with curriculumName: {}", curriculumName);
        return ieLearningCurriculumRepository.findByELearningCurriculumName(curriculumName);
    }

    @Override
    public List<ELearningCurriculum> findAllCurriculumByType(CurriculumType curriculumType) {
        Assert.notNull(curriculumType, "curriculumType cannot be null");
        LOGGER.info("Finding all ELearningCurriculum by curriculumType: {}", curriculumType);
        return ieLearningCurriculumRepository.findAllCurriculumByType(curriculumType);
    }


    @Override
    public List<ELearningCurriculum> findAllCurriculumByTutorialGrade(final StudentTutorialGrade studentTutorialGrade) {
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");
        LOGGER.info("Finding all ELearningCurriculum by studentTutorialGrade: {}", studentTutorialGrade);
        return ieLearningCurriculumRepository.findAllCurriculumByTutorialGrade(studentTutorialGrade);
    }

    @Override
    public List<ELearningCurriculum> findAllCurriculumByTutorialGradeAndType(final CurriculumType curriculumType, final StudentTutorialGrade studentTutorialGrade) {
        Assert.notNull(curriculumType, "curriculumType cannot be null");
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");
        LOGGER.info("Finding all ELearningCurriculum by curriculumType: {} and studentTutorialLevel: {}", curriculumType, studentTutorialGrade);
        return ieLearningCurriculumRepository.findAllCurriculumByTutorialGradeAndType(curriculumType, studentTutorialGrade);
    }
}
