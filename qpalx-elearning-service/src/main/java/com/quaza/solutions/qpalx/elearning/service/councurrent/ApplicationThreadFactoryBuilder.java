package com.quaza.solutions.qpalx.elearning.service.councurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.util.Assert;

import java.util.concurrent.ThreadFactory;

/**
 * Creates custom ThreadFactory to be used across QPalX system.  Each ThreadFactory instance by default is installed with
 * an UncaughtExceptionLogger instance.
 *
 * @author manyce400
 */
public class ApplicationThreadFactoryBuilder {


    public static ThreadFactory buildThreadFactory(String namePattern) {
        Assert.notNull(namePattern, "namePattern cannot be null");
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat(namePattern)
                .setUncaughtExceptionHandler(new UncaughtExceptionLogger())
                .build();
        return threadFactory;
    }

}
