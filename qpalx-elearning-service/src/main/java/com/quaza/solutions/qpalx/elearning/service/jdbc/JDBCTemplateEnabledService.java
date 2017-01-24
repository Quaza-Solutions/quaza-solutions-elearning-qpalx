package com.quaza.solutions.qpalx.elearning.service.jdbc;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author manyce400
 */
public abstract class JDBCTemplateEnabledService {


    protected String externalSQLString;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JDBCTemplateEnabledService.class);


    @PostConstruct
    private void initializeExternalSQLString() throws IOException {
        LOGGER.info("Initializing external sql string.....");
        Resource externalSQLResource = getExternalSQLResource();

        if(externalSQLResource != null) {
            LOGGER.info("Extracting SQL string using externalSQLResource: {}", externalSQLResource);
            externalSQLString = Resources.toString(externalSQLResource.getURL(), Charsets.UTF_8);
        } else {
            LOGGER.error("Failed to load externalSQLString, SQL Resource could not be found.");
        }
    }

    protected abstract Resource getExternalSQLResource();
}
