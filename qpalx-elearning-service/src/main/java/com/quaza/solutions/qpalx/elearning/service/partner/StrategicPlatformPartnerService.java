package com.quaza.solutions.qpalx.elearning.service.partner;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.partner.StrategicPlatformPartner;
import com.quaza.solutions.qpalx.elearning.domain.partner.repository.IStrategicPlatformPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.StrategicPlatformPartnerService")
public class StrategicPlatformPartnerService implements IStrategicPlatformPartnerService {



    @Autowired
    private IStrategicPlatformPartnerRepository iStrategicPlatformPartnerRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StrategicPlatformPartnerService.class);


    @Override
    public StrategicPlatformPartner findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding StrategicPlatformPartner with id: {}", id);
        return iStrategicPlatformPartnerRepository.findOne(id);
    }

    @Override
    public List<StrategicPlatformPartner> findAll() {
        LOGGER.info("Finding and returning all StrategicPlatformPartner..");
        Iterable<StrategicPlatformPartner> results = iStrategicPlatformPartnerRepository.findAll();
        return ImmutableList.copyOf(results);
    }
}
