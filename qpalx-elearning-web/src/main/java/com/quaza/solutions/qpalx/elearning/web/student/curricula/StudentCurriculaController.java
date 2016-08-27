package com.quaza.solutions.qpalx.elearning.web.student.curricula;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.service.panel.IQPalXUserInfoPanelService;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author manyce400
 */
@Controller
public class StudentCurriculaController {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserWebService")
    private IQPalXUserWebService iqPalXUserWebService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService ieLearningCurriculumService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.web.QPalXUserInfoPanelService")
    private IQPalXUserInfoPanelService qPalXUserInfoPanelService;



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentCurriculaController.class);

    @RequestMapping(value = "/curriculum-courses", method = RequestMethod.GET)
    public String displayAllCurriculumCourses(final Model model, @RequestParam("curriculumID") String curriculumID) {
        LOGGER.info("Finding all courses for curriculumID: {}", curriculumID);

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        addSelectedCurriculumInfoToResponse(model, curriculumID);
        return ContentRootE.Student_Home.getContentRootPagePath("selected-curriculum");
    }

    @RequestMapping(value = "/qpalx-course-details", method = RequestMethod.GET)
    public String displayQPalXCourseActivity(final Model model, @RequestParam("qCourseID") String qCourseID) {
        LOGGER.info("Retrieving all learning activities in qCourseID: {}", qCourseID);

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        addSelectedCourseInfoToResponse(model, qCourseID);
        return ContentRootE.Student_Home.getContentRootPagePath("course-activities");
    }

    @RequestMapping(value = "/play-qcourse-video", method = RequestMethod.GET)
    public String playQCourseActivity(final Model model, @RequestParam("courseActivityID") String courseActivityID) {
        LOGGER.info("Accessing Q Course acitivity with courseActivityID: {}", courseActivityID);

        // Add all attributes required for User information panel
        qPalXUserInfoPanelService.addUserInfoAttributes(model);

        return ContentRootE.Student_Home.getContentRootPagePath("video-widget");
    }

    @RequestMapping(value = "/testQuiz", method = RequestMethod.POST)
    public void testSubmit(Model model, @RequestParam("name") String fileName, @RequestParam("file") MultipartFile imageFile){
        System.out.println("Ping: "+imageFile);
//        String imageUploadedFile = iFileUploadUtil.uploadTestScores(imageFile);
//        System.out.println("imageUploadedFile = " + imageUploadedFile);
    }


    private void addSelectedCurriculumInfoToResponse(final Model model, String curriculumID) {
        Long id = NumberUtils.toLong(curriculumID);
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumID(id);
        model.addAttribute("SelectedELearningCurriculum", eLearningCurriculum);

        // Find all E-Learning courses for this curriculum
        List<ELearningCourse> eLearningCourses =  ieLearningCourseService.findByELearningCurriculum(eLearningCurriculum);
        model.addAttribute("CurriculumELearningCourses", eLearningCourses);
        model.addAttribute("CurriculumType", eLearningCurriculum.getCurriculumType().toString());
    }

    private void addSelectedCourseInfoToResponse(final Model model, String courseID) {
        Long id = NumberUtils.toLong(courseID);
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(id);
        model.addAttribute("SelectedELearningCourse", eLearningCourse);
        model.addAttribute("ELearningCourseParentCurriculum", eLearningCourse.geteLearningCurriculum());
        model.addAttribute("SelectedELearningCourseActivities", eLearningCourse.getELearningCourseActivities());
    }

}
