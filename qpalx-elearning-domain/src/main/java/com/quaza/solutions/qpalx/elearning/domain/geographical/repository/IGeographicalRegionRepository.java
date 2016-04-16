package com.quaza.solutions.qpalx.elearning.domain.geographical.repository;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IGeographicalRegionRepository extends CrudRepository<GeographicalRegion, Long> {


    @Query("Select  geographicalRegion From GeographicalRegion geographicalRegion Where geographicalRegion.regionCode =?1")
    public GeographicalRegion findGeographicalRegionByCode(String regionCode);

    @Query("Select  geographicalRegion From GeographicalRegion geographicalRegion Where geographicalRegion.regionName =?1")
    public GeographicalRegion findGeographicalRegionByName(String regionName);
}
