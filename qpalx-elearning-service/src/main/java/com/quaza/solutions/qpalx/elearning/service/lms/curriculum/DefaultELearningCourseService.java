package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
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


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultELearningCourseService.class);


    @Override
    @Transactional
    public void saveELearningCourse(ELearningCourse eLearningCourse) {
        LOGGER.info("Saving ElearningCourse:> {}", eLearningCourse);
        ieLearningCourseRepository.save(eLearningCourse);
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
    public List<ELearningCourse> findStudentELearningCourses(QPalXUser qPalXUser) {
        return null;
    }
}
