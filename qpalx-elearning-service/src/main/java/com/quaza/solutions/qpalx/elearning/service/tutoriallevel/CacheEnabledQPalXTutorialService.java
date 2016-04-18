package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialGrade;
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
    public QPalXTutorialLevel findQPalXTutorialLevelByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding QPalXTutorialLevel with id: {}", id);
        QPalXTutorialLevel qPalXTutorialLevel = iqPalXTutorialLevelRepository.findOne(id);
        return qPalXTutorialLevel;
    }

    @Override
    public TutorialGrade findTutorialGradeByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding TutorialGrade with id: {}", id);
        return iTutorialGradeRepository.findOne(id);
    }

    @Override
    public List<QPalXTutorialLevel> findAllQPalXTutorialLevels() {
        LOGGER.debug("Finding all QPalXTutorialLevel's....");
        Iterable<QPalXTutorialLevel> qPalXTutorialLevels = iqPalXTutorialLevelRepository.findAll();
        return ImmutableList.copyOf(qPalXTutorialLevels);
    }

    @Override
    public List<QPalXTutorialLevel> findAllGeographicalRegionTutorialLevels(GeographicalRegion geographicalRegion) {
        LOGGER.info("Finding all QPalXTutorialLevel's in geographicalRegion: {}", geographicalRegion);
        return iqPalXTutorialLevelRepository.findAllGeographicalRegionTutorialLevels(geographicalRegion);
    }
}
