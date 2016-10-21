package com.quaza.solutions.qpalx.elearning.web.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.ITutorialLevelCalendarService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.display.attributes.enums.CurriculumDisplayAttributeE;
import com.quaza.solutions.qpalx.elearning.web.lms.curriculum.enums.LessonsAdminAttributesE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.service.panel.ITutorialLevelCalendarPanelService;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
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
import java.util.Optional;

/**
 * @author manyce400
 */
@Controller
public class StudentAdaptiveLearningController {



    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

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
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultTutorialLevelCalendarService")
    private ITutorialLevelCalendarService iTutorialLevelCalendarService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.TutorialLevelCalendarPanelService")
    private ITutorialLevelCalendarPanelService iTutorialLevelCalendarPanelService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentCurriculaController.class);


    @RequestMapping(value = "/view-course-lessons", method = RequestMethod.GET)
    public String viewAdaptiveLessons(final Model model, @RequestParam("eLearningCourseID") String eLearningCourseID, @RequestParam("tutorialLevelID") String tutorialLevelID) {
        LOGGER.info("Finding and displaying all lessons for courseID: {} and tutorialLevelID: {}", eLearningCourseID, tutorialLevelID);

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        Long cId = NumberUtils.toLong(eLearningCourseID);
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(cId);
        model.addAttribute(CurriculumDisplayAttributeE.SelectedELearningCourse.toString(), eLearningCourse);

        // Find the current default TutorialLevelCalendar based on the current month
        Long tId = NumberUtils.toLong(tutorialLevelID);
        TutorialLevelCalendar selectedTutorialLevelCalendar = iTutorialLevelCalendarService.findByID(tId);
        List<QPalXELesson> qPalXELessons = iqPalXELessonService.findQPalXELessonByCalendarAndCourse(selectedTutorialLevelCalendar, eLearningCourse);
        model.addAttribute(LessonsAdminAttributesE.QPalXELessons.toString(), qPalXELessons);

        // Add all attributes required for Student tutorial level calendar panel.  By Default we load only first term if no calendar is specified
        iTutorialLevelCalendarPanelService.addCalendarPanelInfo(model, selectedTutorialLevelCalendar);

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);
        return ContentRootE.Student_Adaptive_Learning.getContentRootPagePath("lesson-display-page");
    }

    @RequestMapping(value = "/view-micro-lessons", method = RequestMethod.GET)
    public String viewAdaptiveMicroLessons(final Model model, @RequestParam("eLessonID") String eLessonID,
                                           @RequestParam("user_name") String userName, @RequestParam("user_email") String userEmail, @RequestParam("user_id") String userID) {
        LOGGER.info("Finding and displaying all micro lessons foe eLessonID: {}", eLessonID);

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();

        Long id = NumberUtils.toLong(eLessonID);
        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(id);
        ELearningCourse eLearningCourse = qPalXELesson.geteLearningCourse();
        String selectedTitle = eLearningCourse.getCourseName() + " - Lesson: " + qPalXELesson.getLessonName();
        model.addAttribute(CurriculumDisplayAttributeE.SelectedQPalXELesson.toString(), qPalXELesson);
        model.addAttribute(CurriculumDisplayAttributeE.SelectedELearningCourse.toString(), eLearningCourse);
        model.addAttribute(CurriculumDisplayAttributeE.SelectedQPalXELessonTitle.toString(), selectedTitle);

        // Find all micro lessons for this lesson
        List<QPalXEMicroLesson> qPalXEMicroLessons = iqPalXEMicroLessonService.findQPalXEMicroLessons(qPalXELesson);
        model.addAttribute(LessonsAdminAttributesE.QPalXEMicroLessons.toString(), qPalXEMicroLessons);

        Optional<TutorialLevelCalendar> selectedTutorialLevelCalendar = iTutorialLevelCalendarService.findCurrentCalendarByTutorialLevel(optionalUser.get());
        // Add all attributes required for Student tutorial level calendar panel.  By Default we load only first term if no calendar is specified
        iTutorialLevelCalendarPanelService.addCalendarPanelInfo(model, selectedTutorialLevelCalendar.get());

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);
        return ContentRootE.Student_Adaptive_Learning.getContentRootPagePath("micro-lesson-display");
    }
}
