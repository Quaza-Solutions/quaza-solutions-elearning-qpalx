package com.quaza.solutions.qpalx.elearning.web.student.subscription;

import org.springframework.stereotype.Service;

import java.util.LinkedList;

/**
 * Provides helper functionality to easily access QPalX Subscription pages
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.QPalXStudentSubscriptionHTMLPath")
public class QPalXStudentSubscriptionHTMLPath {



    private static final String STUDENT_SUBSCRIPTION_HTML_ROOT = "qpalx-student/subscription/";

    private final LinkedList<String> visitedSubscriptionPaths;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXStudentSubscriptionHTMLPath.class);

    public QPalXStudentSubscriptionHTMLPath() {
        visitedSubscriptionPaths = new LinkedList<>();
    }

    public String visitSubscriptionPage(String page) {
        String pageToVisit = new StringBuffer(STUDENT_SUBSCRIPTION_HTML_ROOT)
                .append(page)
                .toString();
        visitedSubscriptionPaths.add(pageToVisit);
        LOGGER.debug("Forwarding user to subscription page: {}", pageToVisit);
        return pageToVisit;
    }
}
