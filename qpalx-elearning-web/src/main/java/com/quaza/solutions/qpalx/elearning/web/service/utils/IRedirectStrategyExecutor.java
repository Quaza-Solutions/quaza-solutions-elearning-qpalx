package com.quaza.solutions.qpalx.elearning.web.service.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author manyce400
 */
public interface IRedirectStrategyExecutor {


    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url);
}
