package com.quaza.solutions.qpalx.elearning.web.student.curricula;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseService;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import com.quaza.solutions.qpalx.elearning.web.content.ContentRootE;
import com.quaza.solutions.qpalx.elearning.web.service.user.IQPalXUserWebService;
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
public class StudentCurriculaAdminController {


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
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService")
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentCurriculaAdminController.class);

    @RequestMapping(value = "/add-curriculum-course", method = RequestMethod.GET)
    public String addCurriculumCourse(final Model model, @RequestParam("curriculumID") String curriculumID) {
        LOGGER.info("Add new ELearningCurriculum with curriculumID: {} requested", curriculumID);

        Optional<QPalXUser> optionalUser = iqPalXUserWebService.getLoggedInQPalXUser();
        if(optionalUser.get().getUserType() == QPalxUserTypeE.CONTENT_DEVELOPER || optionalUser.get().getUserType() == QPalxUserTypeE.ADMINISTRATOR) {
            List<QPalXEducationalInstitution> qPalXEducationalInstitutions = iqPalXEducationalInstitutionService.findAll();
            model.addAttribute("LoggedInQPalXUser", optionalUser.get());
            model.addAttribute("ProficiencyRatings", ProficiencyRankingScaleE.values());
            model.addAttribute("QPalXEducationalInstitutions", qPalXEducationalInstitutions);
            return ContentRootE.Content_Admin_Home.getContentRootPagePath("add-elearning-course");
        }

        LOGGER.info("Currently logged in user does not have Content Admin rights, returning to home page....");
        return ContentRootE.Student_Home.getContentRootPagePath("selected-curriculum");
    }


}
