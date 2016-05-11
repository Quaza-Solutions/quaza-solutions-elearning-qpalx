package com.quaza.solutions.qpalx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class acts as a bootstrapper for Spring loading and running CI Tests.
 * This is necessary to bootstrap components defined in service for Spring Unit testing of this module.
 *
 * @author manyce400
 */
@SpringBootApplication
public class QPalXServiceApplicationBootstrapper {

    public static void main(String[] args) {
        SpringApplication.run(QPalXServiceApplicationBootstrapper.class, args);

    }
}
