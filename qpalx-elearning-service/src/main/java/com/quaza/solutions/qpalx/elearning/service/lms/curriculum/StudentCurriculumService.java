package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.IStudentEnrolmentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.StudentCurriculumService")
public class StudentCurriculumService implements IStudentCurriculumService {


    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService iELearningCurriculumService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultStudentEnrolmentRecordService")
    private IStudentEnrolmentRecordService iStudentEnrolmentRecordService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(StudentCurriculumService.class);

    @Override
    public List<ELearningCurriculum> findAllStudentCoreELearningCurriculum(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding all CORE ELearningCurriculum for Student: {}", qPalXUser.getEmail());

        // Find the student users enrolment record
        StudentEnrolmentRecord studentEnrolmentRecord = getStudentEnrolmentRecord(qPalXUser);
        if(studentEnrolmentRecord != null) {
            StudentTutorialGrade studentTutorialGrade = studentEnrolmentRecord.getStudentTutorialGrade();
            List<ELearningCurriculum> eLearningCurricula = iELearningCurriculumService.findAllCurriculumByTutorialGradeAndType(CurriculumType.CORE ,studentTutorialGrade);
            LOGGER.info("Returning student user to main home page with all elearning curricula");
            return eLearningCurricula;
        }

        return null;
    }

    @Override
    public List<ELearningCurriculum> findAllStudentElectiveELearningCurriculum(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding all ELECTIVE ELearningCurriculum for Student: {}", qPalXUser.getEmail());

        StudentEnrolmentRecord studentEnrolmentRecord = getStudentEnrolmentRecord(qPalXUser);
        if(studentEnrolmentRecord != null) {
            StudentTutorialGrade studentTutorialGrade = studentEnrolmentRecord.getStudentTutorialGrade();
            List<ELearningCurriculum> eLearningCurricula = iELearningCurriculumService.findAllCurriculumByTutorialGradeAndType(CurriculumType.ELECTIVE ,studentTutorialGrade);
            LOGGER.info("Returning student user to main home page with all elearning curricula");
            return eLearningCurricula;
        }

        return null;
    }

    @Override
    public List<ELearningCurriculum> findAllCoreELearningCurriculum(StudentTutorialGrade studentTutorialGrade) {
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");
        return null;
    }

    @Override
    public List<ELearningCurriculum> findAllElectiveELearningCurriculum(StudentTutorialGrade studentTutorialGrade) {
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");
        return null;
    }

    private StudentEnrolmentRecord getStudentEnrolmentRecord(QPalXUser qPalXUser) {
        return iStudentEnrolmentRecordService.findCurrentStudentEnrolmentRecord(qPalXUser);
    }
}
