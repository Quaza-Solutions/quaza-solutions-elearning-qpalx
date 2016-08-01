package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseActivityRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultTutorialLevelCalendarService")
public class DefaultTutorialLevelCalendarService implements ITutorialLevelCalendarService {



    @Autowired
    private ITutorialLevelCalendarService iTutorialLevelCalendarService;

    @Autowired
    private IELearningCourseActivityRepository ieLearningCourseActivityRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultTutorialLevelCalendarService.class);


    @Override
    public Map<TutorialLevelCalendar, ELearningCourseActivity> findAllCourseELearningActivities(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.info("Building all Map of all TutorialLevelCalendar to Course activities for eLearningCourse:> {}", eLearningCourse.getCourseName());
        List<ELearningCourseActivity> eLearningCourseActivities = ieLearningCourseActivityRepository.findELearningCourseActivities(eLearningCourse);


        return null;
    }

    @Override
    public List<ELearningCourseActivity> findAllCourseELearningActivities(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse) {
        Assert.notNull(tutorialLevelCalendar, "tutorialLevelCalendar cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.info("Finding all ELearningCourseActivity for tutorialLevelCalendar:> {} and eLearningCourse:> {}", tutorialLevelCalendar.getCalendarItemName(), eLearningCourse.getCourseName());
        List<ELearningCourseActivity> eLearningCourseActivities = iTutorialLevelCalendarService.findAllCourseELearningActivities(tutorialLevelCalendar, eLearningCourse);
        return eLearningCourseActivities;
    }

}
