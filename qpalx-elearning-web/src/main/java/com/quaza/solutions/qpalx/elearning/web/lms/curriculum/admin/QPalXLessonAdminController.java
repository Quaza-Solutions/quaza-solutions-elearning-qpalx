package com.quaza.solutions.qpalx.elearning.web.lms.curriculum.admin;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.curriculum.vo.QPalXELessonWebVO;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.CurriculumDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.DomainDataDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.ValueObjectDataDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.lms.curriculum.enums.LessonsAdminAttributesE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.WebOperationErrorAttributesE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IContentAdminTutorialGradePanelService;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.utils.IRedirectStrategyExecutor;
import com.quaza.solutions.qpalx.elearning.web.utils.IFileUploadUtil;
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
public class QPalXLessonAdminController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
    private IQPalXELessonService iqPalXELessonService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService")
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;

    @Autowired
    @Qualifier("com.quaza.solutions.qpalx.elearning.web.FileUploadUtil")
    private IFileUploadUtil iFileUploadUtil;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.ContentAdminTutorialGradePanelService")
    private IContentAdminTutorialGradePanelService contentAdminTutorialGradePanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
    private IRedirectStrategyExecutor iRedirectStrategyExecutor;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXLessonAdminController.class);

    @RequestMapping(value = "/view-admin-qpalx-elessons", method = RequestMethod.GET)
    public String viewAdminQPalXLessons(final Model model, @RequestParam("eLearningCourseID") String eLearningCourseID) {
        LOGGER.info("Loading and displaying view for all QPalXELesson for eLearningCourseID: {}", eLearningCourseID);

        // Add information required for Users account info display panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        // Add all attributes required for content admin tutorial panel
        Long courseID = NumberUtils.toLong(eLearningCourseID);
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(courseID);
        contentAdminTutorialGradePanelService.addDisplayPanelAttributes(model, Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, eLearningCourse);

        // Find all the QPalXELesson's currently available
        List<QPalXELesson> qPalXELessons = iqPalXELessonService.findQPalXELessonByCourse(eLearningCourse);
        model.addAttribute(LessonsAdminAttributesE.QPalXELessons.toString(), qPalXELessons);
        return ContentRootE.Content_Admin_Home.getContentRootPagePath("view-qpalx-elessons");
    }

    @RequestMapping(value = "/add-qpalx-elesson", method = RequestMethod.GET)
    public String addQPalXELessonsView(final Model model, @RequestParam("eLearningCourseID") String eLearningCourseID, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Building add QPalxELesson page options for eLearningCourseID: {}", eLearningCourseID);

        // IF this is a result of a redirect add any web operations errrors to model
        iRedirectStrategyExecutor.addWebOperationRedirectErrorsToModel(model, request);

        // Create value object used to bind form elements
        QPalXELessonWebVO qPalXELessonWebVO = new QPalXELessonWebVO();

        // Add all required attributes to dispaly add qpalx elesson page
        Long courseID = NumberUtils.toLong(eLearningCourseID);
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(courseID);
        List<QPalXEducationalInstitution> qPalXEducationalInstitutions = iqPalXEducationalInstitutionService.findAll();
        model.addAttribute(CurriculumDisplayAttributeE.SelectedELearningCourse.toString(), eLearningCourse);
        model.addAttribute(DomainDataDisplayAttributeE.AvailableQPalXEducationalInstitutions.toString(), qPalXEducationalInstitutions);
        model.addAttribute(DomainDataDisplayAttributeE.ProficiencyRankings.toString(), ProficiencyRankingScaleE.lowestToHighest());
        model.addAttribute(ValueObjectDataDisplayAttributeE.QPalXELessonWebVO.toString(), qPalXELessonWebVO);
        model.addAttribute(ValueObjectDataDisplayAttributeE.SupportedQPalXTutorialContentTypes.toString(), qPalXELessonWebVO.getQPalXTutorialContentTypes());

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        return ContentRootE.Content_Admin_Home.getContentRootPagePath("add-qpalx-elesson");
    }

    @RequestMapping(value = "/save-qpalx-elesson", method = RequestMethod.POST)
    public void saveQPalXELesson(Model model, @ModelAttribute("QPalXELessonWebVO") QPalXELessonWebVO qPalXELessonWebVO,
                                 HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile multipartFile) {
        LOGGER.info("Saving QPalX ELesson with VO attributes: {}", qPalXELessonWebVO);

        // Upload file and create the ELearningMediaContent
        ELearningMediaContent eLearningMediaContent = iFileUploadUtil.uploadELearningMediaContent(multipartFile, qPalXELessonWebVO);

        if(eLearningMediaContent == null) {
            LOGGER.warn("Selected ELearning Media content could not be uploaded.  Check selected file content.");
            String targetURL = "/add-qpalx-elesson?eLearningCourseID=" + qPalXELessonWebVO.getELearningCourseID();
            String errorMessage = "Failed to upload file: Check the contents of the file";
            iRedirectStrategyExecutor.sendRedirectWithError(targetURL, errorMessage, WebOperationErrorAttributesE.Invalid_FORM_Submission, request, response);
        } else if(eLearningMediaContent == ELearningMediaContent.NOT_SUPPORTED_MEDIA_CONTENT) {
            LOGGER.warn("Uploaded course activity media content file is currently not supported...");
            String targetURL = "/add-qpalx-elesson?eLearningCourseID=" + qPalXELessonWebVO.getELearningCourseID();
            String errorMessage = "Uploaded file is not supported: Only Files of type(MP4, SWF) supported";
            iRedirectStrategyExecutor.sendRedirectWithError(targetURL, errorMessage, WebOperationErrorAttributesE.Invalid_FORM_Submission, request, response);
        } else {
            LOGGER.info("QPalX Lesson media content was succesfully uploaded, saving lesson details...");
            qPalXELessonWebVO.setELearningMediaContent(eLearningMediaContent);
            iqPalXELessonService.createAndSaveQPalXELesson(qPalXELessonWebVO);
            String targetURL = "/view-admin-qpalx-elessons?eLearningCourseID=" + qPalXELessonWebVO.getELearningCourseID();
            iRedirectStrategyExecutor.sendRedirect(request, response, targetURL);
        }
    }
}
