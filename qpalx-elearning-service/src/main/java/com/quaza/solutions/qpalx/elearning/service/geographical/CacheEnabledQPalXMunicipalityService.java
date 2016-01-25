package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.geographical.repository.IQPalXMunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Implementation of IQPalXMunicipalityService which is backed by internal cache for lookup optimization purposes.
 *
 * On any modification to Municipalities cache is rebuilt to be consistent.
 *
 * Created by manyce400 on 12/6/15.
 */
@Service("quaza.solutions.qpalx.elearning.services.CacheEnabledQPalXMunicipalityService")
public class CacheEnabledQPalXMunicipalityService implements IQPalXMunicipalityService {



    @Autowired
    private IQPalXMunicipalityRepository iqPalXMunicipalityRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CacheEnabledQPalXMunicipalityService.class);

    // Internal cache used to store QPalxMunicipalities by ID
    private final Cache<Long, QPalXMunicipality> qPalXMunicipalityByIDCache = CacheBuilder.newBuilder()
            .build();

    // Internal cache used to store QPalxMunicipalities by name
    private final Cache<String, QPalXMunicipality> qPalXMunicipalityByNameCache = CacheBuilder.newBuilder()
            .build();


    @Override
    public QPalXMunicipality findQPalXMunicipalityByID(Long id) {
        Assert.notNull(id, "QPalXMunicipality id cannot be null");
        LOGGER.info("Looking up QPalXMunicipality with id:> {}", id);
        return qPalXMunicipalityByIDCache.getIfPresent(id);
    }

    @Override
    public List<QPalXMunicipality> findAllQPalXMunicipalities() {
        LOGGER.info("Looking up and returning all QPalX Municipalities...");
        Iterable<QPalXMunicipality> iterableMunicipalities = qPalXMunicipalityByIDCache.asMap().values();
        return ImmutableList.copyOf(iterableMunicipalities);
    }

    @PostConstruct
    public void reloadMunicipalityCache() {
        LOGGER.info("loading all QPalXMunicipality from database into cache ...");

        // Load all municipalities and add to internal cache
        Iterable<QPalXMunicipality> qPalXMunicipalities = iqPalXMunicipalityRepository.findAll();
        qPalXMunicipalities.forEach(qPalXMunicipality -> {
            Long id = qPalXMunicipality.getId();
            String municipalityName = qPalXMunicipality.getName();
            qPalXMunicipalityByNameCache.put(municipalityName, qPalXMunicipality);
            qPalXMunicipalityByIDCache.put(id, qPalXMunicipality);
        });
        LOGGER.info("Completed caching all QPalXMunicipality from database.");
    }

}


