package com.quaza.solutions.qpalx.elearning.service.sstatic.content;

import com.quaza.solutions.qpalx.elearning.domain.sstatic.content.StaticContentConfiguration;
import com.quaza.solutions.qpalx.elearning.domain.sstatic.content.StaticContentConfigurationTypeE;

/**
 * @author manyce400
 */
public interface IStaticContentConfigurationService {

    public StaticContentConfiguration findByID(Long id);

    public StaticContentConfiguration findStaticContentConfigurationByContentName(StaticContentConfigurationTypeE staticContentConfigurationTypeE);

}
