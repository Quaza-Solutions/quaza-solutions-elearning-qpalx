package com.quaza.solutions.qpalx.elearning.web.lms.curriculum.admin;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable.IAdaptiveLearningQuizService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import com.quaza.solutions.qpalx.elearning.web.adaptivelearning.vo.AdaptiveLearningQuizWebVO;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.CurriculumDisplayAttributeE;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class AdaptiveLearningQuizController {




    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
    private IQPalXEMicroLessonService iqPalXEMicroLessonService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizService")
    private IAdaptiveLearningQuizService iAdaptiveLearningQuizService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.ContentAdminTutorialGradePanelService")
    private IContentAdminTutorialGradePanelService contentAdminTutorialGradePanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.DefaultRedirectStrategyExecutor")
    private IRedirectStrategyExecutor iRedirectStrategyExecutor;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizController.class);


    @RequestMapping(value = "/view-admin-quizzes", method = RequestMethod.GET)
    public String viewAdminAdaptiveQuizzes(final Model model, @RequestParam("microlessonID") String microlessonID) {
        LOGGER.info("Generating view for quizzes under QPalXEMicroLesson with ID: {}", microlessonID);

        Long microLessonID = NumberUtils.toLong(microlessonID);
        QPalXEMicroLesson qPalXEMicroLesson = iqPalXEMicroLessonService.findByID(microLessonID);
        List<AdaptiveLearningQuiz> adaptiveLearningQuizzes = iAdaptiveLearningQuizService.findAllByQPalXEMicroLesson(qPalXEMicroLesson.getId());

        model.addAttribute(LessonsAdminAttributesE.AdaptiveLearningQuizzes.toString(), adaptiveLearningQuizzes);

        // Add information required for Users account info display panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);
        model.addAttribute(CurriculumDisplayAttributeE.DisplayUserInfo.toString(), Boolean.TRUE.toString());

        model.addAttribute(CurriculumDisplayAttributeE.SelectedQPalXMicroLesson.toString(), qPalXEMicroLesson);

        QPalXELesson qPalXELesson = qPalXEMicroLesson.getQPalXELesson();
        contentAdminTutorialGradePanelService.addDisplayPanelAttributes(model, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE, qPalXELesson);
        return ContentRootE.Content_Admin_Lessons.getContentRootPagePath("view-qpalx-microlesson-quizzes");
    }

    @RequestMapping(value = "/add-adaptive-quiz", method = RequestMethod.GET)
    public String addAdminAdaptiveQuizzes(final Model model, @RequestParam("microlessonID") String microlessonID, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Generating view to add Adaptive Quiz for microlessonID: {}", microlessonID);

        Long microLessonID = NumberUtils.toLong(microlessonID);
        QPalXEMicroLesson qPalXEMicroLesson = iqPalXEMicroLessonService.findByID(microLessonID);
        model.addAttribute(CurriculumDisplayAttributeE.SelectedQPalXMicroLesson.toString(), qPalXEMicroLesson);

        model.addAttribute(CurriculumDisplayAttributeE.DisplayUserInfo.toString(), Boolean.TRUE.toString());

        AdaptiveLearningQuizWebVO adaptiveLearningQuizWebVO = new AdaptiveLearningQuizWebVO();
        model.addAttribute(ValueObjectDataDisplayAttributeE.AdaptiveLearningQuizVO.toString(), adaptiveLearningQuizWebVO);

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        return ContentRootE.Content_Admin_Lessons.getContentRootPagePath("add-adaptive-quiz");
    }

    @RequestMapping(value = "/save-adaptive-quiz", method = RequestMethod.POST)
    public void saveAdminAdaptiveQuiz(Model model,
                                      @ModelAttribute("AdaptiveLearningQuizVO") AdaptiveLearningQuizWebVO adaptiveLearningQuizWebVO,
                                      HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Saving Adaptive Quiz with VO attributes: {}", adaptiveLearningQuizWebVO);
        iAdaptiveLearningQuizService.buildAndSaveAdaptiveLearningQuiz(adaptiveLearningQuizWebVO);
        String targetURL = "/view-admin-quizzes?microlessonID=" + adaptiveLearningQuizWebVO.getQPalXEMicroLessonID();
        iRedirectStrategyExecutor.sendRedirect(request, response, targetURL);
    }

    @RequestMapping(value = "/delete-adaptive-quiz", method = RequestMethod.GET)
    public void deleteAdminAdaptiveQuiz(@RequestParam("adaptiveQuizID") String adaptiveQuizID, Model model, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Deleting AdaptiveLearning Quiz with ID: {}", adaptiveQuizID);
        Long id = NumberUtils.toLong(adaptiveQuizID);
        AdaptiveLearningQuiz adaptiveLearningQuiz = iAdaptiveLearningQuizService.findByID(id);
        QPalXEMicroLesson qPalXEMicroLesson = adaptiveLearningQuiz.getqPalXEMicroLesson();
        iAdaptiveLearningQuizService.delete(adaptiveLearningQuiz);

        // redirect to view all quizzes
        String targetURL = "/view-admin-quizzes?microlessonID=" + qPalXEMicroLesson.getId();
        iRedirectStrategyExecutor.sendRedirect(request, response, targetURL);
    }

}
