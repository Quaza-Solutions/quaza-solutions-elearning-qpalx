package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;

/**
 * @author manyce400
 */
public interface IGeographicalRegionService {


    public GeographicalRegion findGeographicalRegionByCode(String regionCode);

    public GeographicalRegion findGeographicalRegionByName(String regionName);

}
