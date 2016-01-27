package com.quaza.solutions.qpalx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class acts as a bootstrapper for Services application.  This will not be required if running within a
 * different application module.
 *
 * This is necessary to bootstrap components defined in service for Spring Unit testing of this module.
 *
 * Datasource auto configuration is explicitly disabled here.
 *
 * @author manyce400
 */
@SpringBootApplication
public class QPalXServiceApplicationBootstrapper {

    public static void main(String[] args) {
        SpringApplication.run(QPalXServiceApplicationBootstrapper.class, args);

    }
}
