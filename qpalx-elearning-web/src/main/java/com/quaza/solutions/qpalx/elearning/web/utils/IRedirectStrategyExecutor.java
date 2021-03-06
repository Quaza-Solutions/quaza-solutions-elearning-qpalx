package com.quaza.solutions.qpalx.elearning.web.utils;

import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.WebOperationErrorAttributesE;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author manyce400
 */
public interface IRedirectStrategyExecutor {

    public void addWebOperationRedirectErrorsToModel(Model model, HttpServletRequest request);

    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url);

    public void sendRedirectWithError(String targetURL, String errorMessage, WebOperationErrorAttributesE webOperationErrorAttributesE, HttpServletRequest request, HttpServletResponse response);

}
