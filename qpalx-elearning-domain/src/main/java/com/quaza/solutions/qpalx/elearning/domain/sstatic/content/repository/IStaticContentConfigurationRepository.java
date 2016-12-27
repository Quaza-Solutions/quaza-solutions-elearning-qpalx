package com.quaza.solutions.qpalx.elearning.domain.sstatic.content.repository;

import com.quaza.solutions.qpalx.elearning.domain.sstatic.content.StaticContentConfiguration;
import com.quaza.solutions.qpalx.elearning.domain.sstatic.content.StaticContentConfigurationTypeE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IStaticContentConfigurationRepository extends CrudRepository<StaticContentConfiguration, Long> {


    @Query("Select staticContentConfiguration From StaticContentConfiguration staticContentConfiguration Where staticContentConfiguration.staticContentConfigurationType = ?1")
    public StaticContentConfiguration findStaticContentConfigurationByContentName(StaticContentConfigurationTypeE staticContentConfigurationTypeE);


}
