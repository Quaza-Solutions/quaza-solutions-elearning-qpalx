package com.quaza.solutions.qpalx.elearning.web.lms.curriculum.admin;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.curriculum.vo.QPalXELessonWebVO;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.CurriculumDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.DomainDataDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.ValueObjectDataDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.lms.curriculum.enums.LessonsAdminAttributesE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IContentAdminTutorialGradePanelService;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.utils.IRedirectStrategyExecutor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
    private IRedirectStrategyExecutor iRedirectStrategyExecutor;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXLessonAdminController.class);

    @RequestMapping(value = "/view-admin-qpalx-micro-elessons", method = RequestMethod.GET)
    public String viewAdminQPalXLessons(final Model model, @RequestParam("qpalxELessonID") String qpalxELessonID) {
        LOGGER.info("Loading and displaying view for all QPalXMicroLessons for qpalxELessonID: {}", qpalxELessonID);

        // Add information required for Users account info display panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        // Add all attributes required for content admin tutorial panel
        Long lessonID = NumberUtils.toLong(qpalxELessonID);
        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(lessonID);
        contentAdminTutorialGradePanelService.addDisplayPanelAttributes(model, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, qPalXELesson);

        // Find all the QPalXELesson's currently available
        List<QPalXEMicroLesson> qPalXEMicroLessons = iqPalXEMicroLessonService.findQPalXEMicroLessons(qPalXELesson);
        model.addAttribute(LessonsAdminAttributesE.QPalXEMicroLessons.toString(), qPalXEMicroLessons);
        return ContentRootE.Content_Admin_Lessons.getContentRootPagePath("view-qpalx-microlessons");
    }


    @RequestMapping(value = "/add-qpalx-microlesson", method = RequestMethod.GET)
    public String addQPalXELessonsView(final Model model, @RequestParam("qpalxELessonID") String qpalxELessonID, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Building add QPalxELesson page options for qpalxELessonID: {}", qpalxELessonID);

        // IF this is a result of a redirect add any web operations errrors to model
        iRedirectStrategyExecutor.addWebOperationRedirectErrorsToModel(model, request);

        // Create value object used to bind form elements
        QPalXELessonWebVO qPalXELessonWebVO = new QPalXELessonWebVO();

        // Add all required attributes to dispaly add qpalx elesson page
        Long lessonID = NumberUtils.toLong(qpalxELessonID);
        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(lessonID);
        List<QPalXEducationalInstitution> qPalXEducationalInstitutions = iqPalXEducationalInstitutionService.findAll();
        model.addAttribute(CurriculumDisplayAttributeE.SelectedQPalXELesson.toString(), qPalXELesson);
        model.addAttribute(DomainDataDisplayAttributeE.AvailableQPalXEducationalInstitutions.toString(), qPalXEducationalInstitutions);
        model.addAttribute(DomainDataDisplayAttributeE.ProficiencyRankings.toString(), ProficiencyRankingScaleE.lowestToHighest());
        model.addAttribute(ValueObjectDataDisplayAttributeE.QPalXELessonWebVO.toString(), qPalXELessonWebVO);
        model.addAttribute(ValueObjectDataDisplayAttributeE.SupportedQPalXTutorialContentTypes.toString(), qPalXELessonWebVO.getQPalXTutorialContentTypes());

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);
        return ContentRootE.Content_Admin_Lessons.getContentRootPagePath("add-qpalx-elesson");
    }



}
