package com.quaza.solutions.qpalx.elearning.web.lms.curriculum.admin;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IQuestionBankService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.curriculum.vo.QPalXEMicroLessonVO;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.CurriculumDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.ValueObjectDataDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.WebOperationErrorAttributesE;
import com.quaza.solutions.qpalx.elearning.web.lms.curriculum.enums.LessonsAdminAttributesE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IContentAdminTutorialGradePanelService;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.utils.IFileUploadUtil;
import com.quaza.solutions.qpalx.elearning.web.utils.IRedirectStrategyExecutor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    @Qualifier("quaza.solutions.qpalx.elearning.service.QuestionBankService")
    private IQuestionBankService iQuestionBankService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.ContentAdminTutorialGradePanelService")
    private IContentAdminTutorialGradePanelService contentAdminTutorialGradePanelService;

    @Autowired
    @Qualifier("com.quaza.solutions.qpalx.elearning.web.FileUploadUtil")
    private IFileUploadUtil iFileUploadUtil;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
    private IRedirectStrategyExecutor iRedirectStrategyExecutor;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXLessonAdminController.class);

    @RequestMapping(value = "/view-admin-qpalx-micro-elessons", method = RequestMethod.GET)
    public String viewAdminQPalXLessons(final Model model, @RequestParam("qpalxELessonID") String qpalxELessonID) {
        LOGGER.info("Loading and displaying view for all QPalXMicroLessons for qpalxELessonID: {}", qpalxELessonID);

        // Add information required for Users account info display panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);
        model.addAttribute(CurriculumDisplayAttributeE.DisplayUserInfo.toString(), Boolean.TRUE.toString());

        // Add all attributes required for content admin tutorial panel
        Long lessonID = NumberUtils.toLong(qpalxELessonID);
        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(lessonID);
        contentAdminTutorialGradePanelService.addDisplayPanelAttributes(model, Boolean.FALSE, Boolean.TRUE, Boolean.FALSE, qPalXELesson);

        // Find all the QPalXELesson's currently available
        List<QPalXEMicroLesson> qPalXEMicroLessons = iqPalXEMicroLessonService.findQPalXEMicroLessons(qPalXELesson);
        model.addAttribute(LessonsAdminAttributesE.QPalXEMicroLessons.toString(), qPalXEMicroLessons);

        // Add all Question banks for this Lesson
        List<QuestionBankItem> questionBankItems = iQuestionBankService.findQuestionBankItems(qPalXELesson);
        model.addAttribute(LessonsAdminAttributesE.QuestionBankItems.toString(), questionBankItems);
        return ContentRootE.Content_Admin_Lessons.getContentRootPagePath("view-qpalx-microlessons");
    }


    @RequestMapping(value = "/add-qpalx-microlesson", method = RequestMethod.GET)
    public String addMicroLessonsView(final Model model, @RequestParam("qpalxELessonID") String qpalxELessonID, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Building add QPalxELesson page options for qpalxELessonID: {}", qpalxELessonID);

        // IF this is a result of a redirect add any web operations errrors to model
        iRedirectStrategyExecutor.addWebOperationRedirectErrorsToModel(model, request);

        // Create value object used to bind form elements
        QPalXEMicroLessonVO qPalXEMicroLessonVO = new QPalXEMicroLessonVO();
        model.addAttribute(CurriculumDisplayAttributeE.DisplayUserInfo.toString(), Boolean.TRUE.toString());

        // Add all required attributes to dispaly add qpalx elesson page
        Long lessonID = NumberUtils.toLong(qpalxELessonID);
        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(lessonID);
        model.addAttribute(CurriculumDisplayAttributeE.SelectedQPalXELesson.toString(), qPalXELesson);
        model.addAttribute(ValueObjectDataDisplayAttributeE.QPalXEMicroLessonVO.toString(), qPalXEMicroLessonVO);
        model.addAttribute(ValueObjectDataDisplayAttributeE.SupportedQPalXTutorialContentTypes.toString(), qPalXEMicroLessonVO.getQPalXTutorialContentTypes());

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);
        return ContentRootE.Content_Admin_Lessons.getContentRootPagePath("add-qpalx-microlesson");
    }


    @RequestMapping(value = "/save-qpalx-microlesson", method = RequestMethod.POST)
    public void saveMicroLesson(Model model, @ModelAttribute("QPalXEMicroLessonVO") QPalXEMicroLessonVO qPalXEMicroLessonVO,
                                 HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile multipartFile) {
        LOGGER.info("Saving QPalX micro lesson with VO attributes: {}", qPalXEMicroLessonVO);

        // Upload file and create the ELearningMediaContent
        ELearningMediaContent eLearningMediaContent = iFileUploadUtil.uploadELearningMediaContent(multipartFile, qPalXEMicroLessonVO);

        if(eLearningMediaContent == null) {
            LOGGER.warn("Selected ELearning Media content could not be uploaded.  Check selected file content.");
            String targetURL = "/add-qpalx-microlesson?qpalxELessonID=" + qPalXEMicroLessonVO.getQPalXELessonID();
            String errorMessage = "Failed to upload file: Check the contents of the file";
            iRedirectStrategyExecutor.sendRedirectWithError(targetURL, errorMessage, WebOperationErrorAttributesE.Invalid_FORM_Submission, request, response);
        } else if(eLearningMediaContent == ELearningMediaContent.NOT_SUPPORTED_MEDIA_CONTENT) {
            LOGGER.warn("Uploaded course activity media content file is currently not supported...");
            String targetURL = "/add-qpalx-microlesson?qpalxELessonID=" + qPalXEMicroLessonVO.getQPalXELessonID();
            String errorMessage = "Uploaded file is not supported: Only Files of type(MP4, SWF) supported";
            iRedirectStrategyExecutor.sendRedirectWithError(targetURL, errorMessage, WebOperationErrorAttributesE.Invalid_FORM_Submission, request, response);
        } else {
            LOGGER.info("QPalX Lesson media content was succesfully uploaded, saving micro lesson details...");
            qPalXEMicroLessonVO.setELearningMediaContent(eLearningMediaContent);
            iqPalXEMicroLessonService.createAndSaveQPalXEMicroLesson(qPalXEMicroLessonVO);
            String targetURL = "/view-admin-qpalx-micro-elessons?qpalxELessonID=" + qPalXEMicroLessonVO.getQPalXELessonID();
            iRedirectStrategyExecutor.sendRedirect(request, response, targetURL);
        }
    }



}
