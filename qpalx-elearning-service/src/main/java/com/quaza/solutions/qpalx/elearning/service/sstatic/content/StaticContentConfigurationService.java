package com.quaza.solutions.qpalx.elearning.service.sstatic.content;

import com.quaza.solutions.qpalx.elearning.domain.sstatic.content.StaticContentConfiguration;
import com.quaza.solutions.qpalx.elearning.domain.sstatic.content.StaticContentConfigurationTypeE;
import com.quaza.solutions.qpalx.elearning.domain.sstatic.content.repository.IStaticContentConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.StaticContentConfigurationService")
public class StaticContentConfigurationService implements IStaticContentConfigurationService {



    @Autowired
    private IStaticContentConfigurationRepository iStaticContentConfigurationRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StaticContentConfigurationService.class);


    @Override
    public StaticContentConfiguration findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding StaticContentConfiguration by id: {}", id);
        return iStaticContentConfigurationRepository.findOne(id);
    }

    @Override
    public StaticContentConfiguration findStaticContentConfigurationByContentName(StaticContentConfigurationTypeE staticContentConfigurationTypeE) {
        Assert.notNull(staticContentConfigurationTypeE, "staticContentConfigurationTypeE cannot be null");
        LOGGER.info("Finding StaticContentConfiguration by staticContentConfigurationTypeE: {}", staticContentConfigurationTypeE);
        return iStaticContentConfigurationRepository.findStaticContentConfigurationByContentName(staticContentConfigurationTypeE);
    }

}
