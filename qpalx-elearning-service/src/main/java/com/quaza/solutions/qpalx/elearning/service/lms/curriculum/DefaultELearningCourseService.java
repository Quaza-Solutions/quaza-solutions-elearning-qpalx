package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.institutions.repository.IQPalXEducationalInstitutionRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IELearningCourseVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCurriculumRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
public class DefaultELearningCourseService implements IELearningCourseService {



    @Autowired
    private IELearningCourseRepository ieLearningCourseRepository;

    @Autowired
    private IELearningCurriculumRepository ieLearningCurriculumRepository;

    @Autowired
    private IQPalXEducationalInstitutionRepository iqPalXEducationalInstitutionRepository;



    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultELearningCourseService.class);


    @Override
    @Transactional
    public void saveELearningCourse(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.info("Saving ElearningCourse:> {}", eLearningCourse);
        ieLearningCourseRepository.save(eLearningCourse);
    }

    @Override
    @Transactional
    public void deleteELearningCourse(Long eLearningCourseID) {
        Assert.notNull(eLearningCourseID, "eLearningCourseID cannot be null");
        LOGGER.info("Deleting ELearningCourse with id:> {}", eLearningCourseID);
        ieLearningCourseRepository.delete(eLearningCourseID);
    }

    @Override
    @Transactional
    public void createELearningCourse(IELearningCourseVO eLearningCourseWebVO) {
        Assert.notNull(eLearningCourseWebVO, "eLearningCourseWebVO cannot be null");
        LOGGER.info("Creating new ELearning course from eLearningCourseWebVO:> {}", eLearningCourseWebVO);

        // Lookup the ELearningCurriculum that will be associated with this course
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumRepository.findOne(eLearningCourseWebVO.getELearningCurriculumID());

        // Lookup the EducationalInstitution tied to this course if available
        QPalXEducationalInstitution qPalXEducationalInstitution = null;
        if (eLearningCourseWebVO.getEducationalInstitutionID() != null) {
            qPalXEducationalInstitution = iqPalXEducationalInstitutionRepository.findOne(eLearningCourseWebVO.getEducationalInstitutionID());
        }

        ELearningCourse eLearningCourse = ELearningCourse.builder()
                .eLearningCurriculum(eLearningCurriculum)
                .courseName(eLearningCourseWebVO.getCourseName())
                .courseDescription(eLearningCourseWebVO.getCourseDescription())
                .qPalXEducationalInstitution(qPalXEducationalInstitution)
                .courseActive(true)
                .entryDate(new DateTime())
                .build();
        saveELearningCourse(eLearningCourse);
    }

    @Override
    public ELearningCourse findByCourseID(Long courseID) {
        Assert.notNull(courseID, "courseID cannot be null");
        LOGGER.debug("Finding ELearningCourse with courseID:> {}", courseID);
        return ieLearningCourseRepository.findOne(courseID);
    }

    @Override
    public ELearningCourse findByCourseName(String courseName) {
        Assert.notNull(courseName, "courseName cannot be null");
        LOGGER.debug("Finding ELearningCourse with courseName:> {}", courseName);
        return ieLearningCourseRepository.findByCourseName(courseName);
    }

    @Override
    public List<ELearningCourse> findByELearningCurriculum(ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        LOGGER.info("Finding all ELearning courses for curriculum with name:> {}", eLearningCurriculum.getCurriculumName());
        List<ELearningCourse> eLearningCourses = ieLearningCourseRepository.findByELearningCurriculum(eLearningCurriculum);
        return eLearningCourses;
    }

    @Override
    public ELearningCourse findByCourseNameAndELearningCurriculum(String courseName, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(courseName, "courseName cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        ELearningCourse eLearningCourse = ieLearningCourseRepository.findByCourseNameAndELearningCurriculum(courseName, eLearningCurriculum);
        return eLearningCourse;
    }
}
