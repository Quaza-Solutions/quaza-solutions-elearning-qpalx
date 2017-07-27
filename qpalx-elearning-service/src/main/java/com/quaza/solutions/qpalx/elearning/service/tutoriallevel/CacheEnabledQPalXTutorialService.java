package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.IQPalXTutorialLevelRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.ITutorialGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * QPalx tutorial service which is backed by an internal cache for optimization purposes.
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXTutorialService")
public class CacheEnabledQPalXTutorialService implements IQPalXTutorialService {


    @Autowired
    private IQPalXTutorialLevelRepository iqPalXTutorialLevelRepository;

    @Autowired
    private ITutorialGradeRepository iTutorialGradeRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CacheEnabledQPalXTutorialService.class);

    @Override
    public StudentTutorialLevel findQPalXTutorialLevelByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding StudentTutorialLevel with id: {}", id);
        StudentTutorialLevel studentTutorialLevel = iqPalXTutorialLevelRepository.findOne(id);
        return studentTutorialLevel;
    }

    @Override
    public StudentTutorialGrade findTutorialGradeByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding StudentTutorialGrade with id: {}", id);
        return iTutorialGradeRepository.findOne(id);
    }

    @Override
    public List<StudentTutorialGrade> findAllStudentTutorialGrade() {
        LOGGER.info("Finding all configured StudentTutorialGrade...");
        Iterable<StudentTutorialGrade> studentTutorialGrades  = iTutorialGradeRepository.findAll();
        return ImmutableList.copyOf(studentTutorialGrades);
    }

    @Override
    public List<StudentTutorialLevel> findAllQPalXTutorialLevels() {
        LOGGER.debug("Finding all StudentTutorialLevel's....");
        Iterable<StudentTutorialLevel> qPalXTutorialLevels = iqPalXTutorialLevelRepository.findAll();
        return ImmutableList.copyOf(qPalXTutorialLevels);
    }

    @Override
    public List<StudentTutorialLevel> findAllQPalXCountryTutorialLevels(final QPalXCountry qPalXCountry) {
        Assert.notNull(qPalXCountry, "qPalXCountry cannot be null");
        LOGGER.info("Finding all StudentTutorialLevel's in qPalXCountry: {}", qPalXCountry);
        return iqPalXTutorialLevelRepository.findAllQPalXCountryTutorialLevels(qPalXCountry);
    }
}
