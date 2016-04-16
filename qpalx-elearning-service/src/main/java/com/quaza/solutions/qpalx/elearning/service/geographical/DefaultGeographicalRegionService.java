package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.geographical.repository.IGeographicalRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultGeographicalRegionService")
public class DefaultGeographicalRegionService implements IGeographicalRegionService {


    @Autowired
    private IGeographicalRegionRepository iGeographicalRegionRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultGeographicalRegionService.class);

    @Override
    public GeographicalRegion findGeographicalRegionByCode(String regionCode) {
        Assert.notNull(regionCode, "regionCode cannot be null");
        LOGGER.info("Finding GeographicalRegion by regionCode: {}", regionCode);
        return iGeographicalRegionRepository.findGeographicalRegionByCode(regionCode);
    }

    @Override
    public GeographicalRegion findGeographicalRegionByName(String regionName) {
        Assert.notNull(regionName, "regionName cannot be null");
        LOGGER.info("Finding GeographicalRegion by regionName: {}", regionName);
        return iGeographicalRegionRepository.findGeographicalRegionByName(regionName);
    }
}
