package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseActivityRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.IELearningCourseActivityVO;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseActivityService")
public class DefaultELearningCourseActivityService implements IELearningCourseActivityService {


    @Autowired
    private IELearningCourseActivityRepository ieLearningCourseActivityRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultELearningCourseActivityService.class);


    @Override
    public ELearningCourseActivity buildNew(IELearningCourseActivityVO iELearningCourseActivityVO) {
        Assert.notNull(iELearningCourseActivityVO, "iELearningCourseActivityVO cannot be null");
        LOGGER.info("Creating new iELearningCourseActivityVO:> {}", iELearningCourseActivityVO);

        Long eLearningCourseID = iELearningCourseActivityVO.getELearningCourseID();
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(eLearningCourseID);

        LearningActivityE learningActivityE = LearningActivityE.valueOf(iELearningCourseActivityVO.getActivityType());

        ELearningMediaContent eLearningMediaContent = ELearningMediaContent.builder()
                .eLearningMediaType("mp4")
                .eLearningMediaFile(iELearningCourseActivityVO.getActivityFile())
                .build();

        ELearningCourseActivity eLearningCourseActivity = ELearningCourseActivity.builder()
                .eLearningCourse(eLearningCourse)
                .learningActivityE(learningActivityE)
                .activityName(iELearningCourseActivityVO.getActivityName())
                .activityDescription(iELearningCourseActivityVO.getActivityDescription())
                .eLearningMediaContent(eLearningMediaContent)
                .activityActive(true)
                .entryDate(new DateTime())
                .build();
        return null;
    }

    @Override
    public ELearningCourseActivity findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding ELearningCourseActivity with id:> {}", id);
        return ieLearningCourseActivityRepository.findOne(id);
    }

    @Override
    public List<ELearningCourseActivity> findELearningCourseAcitivitiesByCourse(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.info("Finding all ELearning course activities for eLearningCours:> {}", eLearningCourse.getCourseName());
        return ieLearningCourseActivityRepository.findELearningCourseActivities(eLearningCourse);
    }

    @Override
    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse) {
        Assert.notNull(activityName, "activityName cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding ELearningCourseActivity for eLearningCourse:> {} and activityName:> {}", eLearningCourse.getCourseName(), activityName);
        return ieLearningCourseActivityRepository.findELearningCourseActivityByCourseAndActivityName(activityName, eLearningCourse);
    }
}
