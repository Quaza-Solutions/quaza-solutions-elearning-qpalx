package com.quaza.solutions.qpalx.elearning.service.councurrent;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * @author manyce400
 */
@Configuration
public class ApplicationConcurrencyConfig {


    public static final String BEAN_NAME = "quaza.solutions.qpalx.elearning.service.StatisticsExecutorService";


    @Bean(name = "quaza.solutions.qpalx.elearning.service.StatisticsExecutorService")
    public ListeningExecutorService configureApplicationExecutorService() {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
                Executors.newFixedThreadPool(5, ApplicationThreadFactoryBuilder.buildThreadFactory("STATISTICS-QPalX-ThreadPool-%d"))
        );
        return executorService;
    }
}
