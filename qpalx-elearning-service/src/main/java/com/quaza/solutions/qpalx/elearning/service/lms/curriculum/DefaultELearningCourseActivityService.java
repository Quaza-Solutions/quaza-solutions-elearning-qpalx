package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseActivityService")
public class DefaultELearningCourseActivityService implements IELearningCourseActivityService {


    @Autowired
    private IELearningCourseActivityRepository ieLearningCourseActivityRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultELearningCourseActivityService.class);


    @Override
    public ELearningCourseActivity findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding ELearningCourseActivity with id:> {}", id);
        return ieLearningCourseActivityRepository.findOne(id);
    }

    @Override
    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse) {
        Assert.notNull(activityName, "activityName cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding ELearningCourseActivity for eLearningCourse:> {} and activityName:> {}", eLearningCourse.getCourseName(), activityName);
        return ieLearningCourseActivityRepository.findELearningCourseActivityByCourseAndActivityName(activityName, eLearningCourse);
    }
}
