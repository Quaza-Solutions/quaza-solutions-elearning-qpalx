package com.quaza.solutions.qpalx.elearning.web.service.user;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IStudentCurriculumService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.IContentAdminProfileRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.web.ContentAdminWebService")
public class ContentAdminWebService implements IContentAdminWebService {




    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StudentCurriculumService")
    private IStudentCurriculumService iStudentCurriculumService;


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultContentAdminProfileRecordService")
    private IContentAdminProfileRecordService iContentAdminProfileRecordService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ContentAdminWebService.class);


    @Override
    public void addContentAdminCurriculaOptions(Model model, QPalXUser qPalXUser) {
        Assert.notNull(model, "model cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        // Get and set all the Student Tutorial Grades assiged to content admin user
        List<StudentTutorialGrade> studentTutorialGrades = iContentAdminProfileRecordService.findContentAdminStudentTutorialGrades(qPalXUser);
        model.addAttribute("AssignedStudentTutorialGrades", studentTutorialGrades);

        // Load up all curricula for first studentTutorialGrades
        StudentTutorialGrade studentTutorialGrade = studentTutorialGrades.get(0);
        List<ELearningCurriculum> eLearningCurricula = iStudentCurriculumService.findAllCoreELearningCurriculum(studentTutorialGrade);
        model.addAttribute("SelectedStudentTutorialGrade", studentTutorialGrade.getTutorialGrade());
        model.addAttribute("AssignedELearningCurricula", eLearningCurricula);
        model.addAttribute("CurriculumType", "CORE");
    }

}
