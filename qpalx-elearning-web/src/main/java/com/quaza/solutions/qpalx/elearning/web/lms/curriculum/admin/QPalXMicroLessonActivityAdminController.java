package com.quaza.solutions.qpalx.elearning.web.lms.curriculum.admin;

import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.utils.IFileUploadUtil;
import com.quaza.solutions.qpalx.elearning.web.utils.IRedirectStrategyExecutor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author manyce400
 */
@Controller
public class QPalXMicroLessonActivityAdminController {




    @Autowired
    @Qualifier("com.quaza.solutions.qpalx.elearning.web.FileUploadUtil")
    private IFileUploadUtil iFileUploadUtil;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
    private IRedirectStrategyExecutor iRedirectStrategyExecutor;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXMicroLessonActivityAdminController.class);


    @RequestMapping(value = "/view-admin-qpalx-micro-elesson-activity", method = RequestMethod.GET)
    public String viewAdminQPalXLessons(final Model model, @RequestParam("microlessonID") String microlessonID) {
        LOGGER.info("Loading and displaying view for all MicroLesson Activities for microlessonID: {}", microlessonID);

        // Add information required for Users account info display panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        // Add all attributes required for content admin tutorial panel
        Long activityID = NumberUtils.toLong(microlessonID);
        return ContentRootE.Content_Admin_Lessons.getContentRootPagePath("view-qpalx-microlesson-activities");
    }
}
