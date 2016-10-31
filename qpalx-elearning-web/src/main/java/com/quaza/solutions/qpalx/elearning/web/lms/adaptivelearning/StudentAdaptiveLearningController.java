package com.quaza.solutions.qpalx.elearning.web.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.AdaptiveMicroLessonStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.IAdaptiveLessonStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics.IAdaptiveMicroLessonStatisticsService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.ITutorialLevelCalendarService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.AdaptiveLearningDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.CurriculumDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.lms.curriculum.enums.LessonsAdminAttributesE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IStudentInfoOverviewPanelService;
import com.quaza.solutions.qpalx.elearning.web.service.panel.ITutorialLevelCalendarPanelService;
import com.quaza.solutions.qpalx.elearning.web.student.curricula.StudentCurriculaController;
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
public class StudentAdaptiveLearningController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
    private IQPalXELessonService iqPalXELessonService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
    private IQPalXEMicroLessonService iqPalXEMicroLessonService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveLessonStatisticsService")
    private IAdaptiveLessonStatisticsService iAdaptiveLessonStatisticsService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.AdaptiveMicroLessonStatisticsService")
    private IAdaptiveMicroLessonStatisticsService iAdaptiveMicroLessonStatisticsService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultTutorialLevelCalendarService")
    private ITutorialLevelCalendarService iTutorialLevelCalendarService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.TutorialLevelCalendarPanelService")
    private ITutorialLevelCalendarPanelService iTutorialLevelCalendarPanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.StudentInfoOverviewPanelService")
    private IStudentInfoOverviewPanelService iStudentInfoOverviewPanelService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentCurriculaController.class);


    @RequestMapping(value = "/view-course-lessons", method = RequestMethod.GET)
    public String viewAdaptiveLessons(final Model model, @RequestParam("eLearningCourseID") String eLearningCourseID, @RequestParam("tutorialLevelID") String tutorialLevelID) {
        LOGGER.info("Finding and displaying all lessons for courseID: {} and tutorialLevelID: {}", eLearningCourseID, tutorialLevelID);

        Long cId = NumberUtils.toLong(eLearningCourseID);
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(cId);
        model.addAttribute(CurriculumDisplayAttributeE.SelectedELearningCourse.toString(), eLearningCourse);
        iStudentInfoOverviewPanelService.addStudentInfoOverviewWithCourse(model, eLearningCourse);

        // Find the current default TutorialLevelCalendar based on the selected value
        Long tId = NumberUtils.toLong(tutorialLevelID);
        TutorialLevelCalendar selectedTutorialLevelCalendar = iTutorialLevelCalendarService.findByID(tId);
        List<AdaptiveLessonStatistics> adaptiveLessonStatisticsList = iAdaptiveLessonStatisticsService.findAdaptiveLessonStatisticsByCourseIDAndTutorialLevel(selectedTutorialLevelCalendar, eLearningCourse);
        model.addAttribute(LessonsAdminAttributesE.QPalXELessons.toString(), adaptiveLessonStatisticsList);

        // Add all attributes required for Student tutorial level calendar panel.  By Default we load only first term if no calendar is specified
        iTutorialLevelCalendarPanelService.addCalendarPanelInfo(model, selectedTutorialLevelCalendar);

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        // Add Lessons display attributes
        model.addAttribute(AdaptiveLearningDisplayAttributeE.LessonsDisplayEnabled.toString(), Boolean.TRUE.toString());

        return ContentRootE.Student_Adaptive_Learning.getContentRootPagePath("lesson-display-page");
    }

    @RequestMapping(value = "/view-micro-lessons", method = RequestMethod.GET)
    public String viewAdaptiveMicroLessons(final Model model, @RequestParam("eLessonID") String eLessonID, @RequestParam("tutorialLevelID") String tutorialLevelID) {
        LOGGER.info("Finding and displaying all micro lessons foe eLessonID: {}", eLessonID);

        Long id = NumberUtils.toLong(eLessonID);
        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(id);
        iStudentInfoOverviewPanelService.addStudentInfoOverviewWithLesson(model, qPalXELesson);

        ELearningCourse eLearningCourse = qPalXELesson.geteLearningCourse();
        model.addAttribute(CurriculumDisplayAttributeE.SelectedELearningCourse.toString(), eLearningCourse);

        // Find all micro lessons for this lesson
        //List<QPalXEMicroLesson> qPalXEMicroLessons = iqPalXEMicroLessonService.findQPalXEMicroLessons(qPalXELesson);
        List<AdaptiveMicroLessonStatistics> adaptiveMicroLessonStatisticsList = iAdaptiveMicroLessonStatisticsService.findAdaptiveMicroLessonStatisticsByLessonAndCourse(qPalXELesson);

        model.addAttribute(LessonsAdminAttributesE.QPalXEMicroLessons.toString(), adaptiveMicroLessonStatisticsList);

        // Find the current default TutorialLevelCalendar based on the selected value
        Long tId = NumberUtils.toLong(tutorialLevelID);
        iTutorialLevelCalendarPanelService.addCalendarPanelInfo(model, tId);

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        // Add Micro-Lessons display attributes
        model.addAttribute(AdaptiveLearningDisplayAttributeE.MicroLessonsDisplayEnabled.toString(), Boolean.TRUE.toString());

        LOGGER.info("Returning micro lessons display page ==> micro-lesson-display");
        return ContentRootE.Student_Adaptive_Learning.getContentRootPagePath("micro-lesson-display");
    }
}
