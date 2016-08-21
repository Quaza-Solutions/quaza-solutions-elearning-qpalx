package com.quaza.solutions.qpalx.elearning.web.service.utils;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
public class DefaultRedirectStrategyExecutor implements IRedirectStrategyExecutor {




    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultRedirectStrategyExecutor.class);


    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String targetURL) {
        LOGGER.debug("Sending redirect to targetURL: {}", targetURL);

        try {
            redirectStrategy.sendRedirect(request, response, targetURL);
        } catch (IOException e) {
            LOGGER.error("Exception occurred while redirecting to targetURL: {}", targetURL, e);
        }
    }
}
