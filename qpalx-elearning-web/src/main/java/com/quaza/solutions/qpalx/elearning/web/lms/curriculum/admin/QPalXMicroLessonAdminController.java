package com.quaza.solutions.qpalx.elearning.web.lms.curriculum.admin;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.lms.curriculum.enums.LessonsAdminAttributesE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IContentAdminTutorialGradePanelService;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author manyce400
 */
@Controller
public class QPalXMicroLessonAdminController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
    private IQPalXELessonService iqPalXELessonService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
    private IQPalXEMicroLessonService iqPalXEMicroLessonService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService")
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.ContentAdminTutorialGradePanelService")
    private IContentAdminTutorialGradePanelService contentAdminTutorialGradePanelService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXLessonAdminController.class);

    @RequestMapping(value = "/view-admin-qpalx-micro-elessons", method = RequestMethod.GET)
    public String viewAdminQPalXLessons(final Model model, @RequestParam("qpalxELessonID") String qpalxELessonID) {
        LOGGER.info("Loading and displaying view for all QPalXMicroLessons for qpalxELessonID: {}", qpalxELessonID);

        // Add information required for Users account info display panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        // Add all attributes required for content admin tutorial panel
        Long lessonID = NumberUtils.toLong(qpalxELessonID);
        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(lessonID);
        contentAdminTutorialGradePanelService.addDisplayPanelAttributes(model, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, qPalXELesson);

        // Find all the QPalXELesson's currently available
        List<QPalXEMicroLesson> qPalXEMicroLessons = iqPalXEMicroLessonService.findQPalXEMicroLessons(qPalXELesson);
        model.addAttribute(LessonsAdminAttributesE.QPalXEMicroLessons.toString(), qPalXEMicroLessons);
        return ContentRootE.Content_Admin_Home.getContentRootPagePath("view-micro-lessons");
    }

//    @RequestMapping(value = "/add-qpalx-elesson", method = RequestMethod.GET)
//    public String addCurriculumCourse(final Model model, @RequestParam("eLearningCourseID") String eLearningCourseID, HttpServletRequest request, HttpServletResponse response) {
//        LOGGER.info("Building add QPalxELesson page options for eLearningCourseID: {}", eLearningCourseID);
//
//        // Add all required attributes to dispaly add qpalx elesson page
//        Long courseID = NumberUtils.toLong(eLearningCourseID);
//        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(courseID);
//        List<QPalXEducationalInstitution> qPalXEducationalInstitutions = iqPalXEducationalInstitutionService.findAll();
//        model.addAttribute(CurriculumDisplayAttributeE.SelectedELearningCourse.toString(), eLearningCourse);
//        model.addAttribute(DomainDataDisplayAttributeE.AvailableQPalXEducationalInstitutions.toString(), qPalXEducationalInstitutions);
//        model.addAttribute(DomainDataDisplayAttributeE.ProficiencyRankings.toString(), ProficiencyRankingScaleE.lowestToHighest());
//        model.addAttribute(ValueObjectDataDisplayAttributeE.QPalXELessonWebVO.toString(), new QPalXELessonWebVO());
//
//        // Add all attributes required for User information panel
//        qPalXUserInfoPanelService.addUserInfoAttributes(model);
//
//        return ContentRootE.Content_Admin_Home.getContentRootPagePath("add-qpalx-elesson");
//    }
}
