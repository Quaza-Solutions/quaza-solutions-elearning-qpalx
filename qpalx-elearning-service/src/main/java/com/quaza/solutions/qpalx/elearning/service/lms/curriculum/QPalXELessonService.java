package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXELessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXELessonRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.ITutorialLevelCalendarService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
public class QPalXELessonService implements IQPalXELessonService {




    private String lessonActiveItemsSQL;

    @Value("classpath:/sql/lesson-active-items-count.sql")
    private Resource sqlResource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IQPalXELessonRepository iqPalXELessonRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultTutorialLevelCalendarService")
    private ITutorialLevelCalendarService iTutorialLevelCalendarService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService")
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXELessonService.class);


    @Override
    public QPalXELesson findQPalXELessonByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding QPalXELesson with id:> {}", id);
        return iqPalXELessonRepository.findOne(id);
    }

    @Override
    public List<QPalXELesson> findQPalXELessonByCourse(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.debug("Finding QPalXELesson with eLearningCourse:> {}", eLearningCourse);
        List<QPalXELesson> qPalXELessons = iqPalXELessonRepository.findQPalXELessonForELearningCourse(eLearningCourse);
        return qPalXELessons;
    }

    @Override
    public List<QPalXELesson> findQPalXELessonByCalendarAndCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse) {
        Assert.notNull(tutorialLevelCalendar, "tutorialLevelCalendar cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.debug("Finding QPalxELesson for calendar: {} and eLearningCourse: {}", tutorialLevelCalendar, eLearningCourse);
        List<QPalXELesson> qPalXELessons = iqPalXELessonRepository.findQPalXELessonByTutorialCalendarELearningCourse(tutorialLevelCalendar, eLearningCourse);
        return qPalXELessons;
    }

    @Override
    public void saveQPalXELesson(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson");
        LOGGER.debug("Saving qPalXELesson: {}", qPalXELesson);
        iqPalXELessonRepository.save(qPalXELesson);
    }

    @Transactional
    @Override
    public void createAndSaveQPalXELesson(IQPalXELessonVO iqPalXELessonVO) {
        Assert.notNull(iqPalXELessonVO, "iqPalXELessonVO cannot be null");
        Assert.notNull(iqPalXELessonVO.getELearningCourseID(), "Non null ELearningCourse ID required");
        Assert.notNull(iqPalXELessonVO.getTutorialLevelCalendarID(), "Non null valid TutorialLevelCalendar ID required");
        Assert.notNull(iqPalXELessonVO.getELearningMediaContent(), "Non null valid ELearningMediaContent ID required");
        LOGGER.info("Creating new QPalXELesson with value object iqPalXELessonVO: {}", iqPalXELessonVO);

        // Load up ELearningCourse instance
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(iqPalXELessonVO.getELearningCourseID());

        // Load up the EducationalInstitution for this course.  Only initialized if this course was only designed for an institution.
        QPalXEducationalInstitution qPalXEducationalInstitution = null;
        if (iqPalXELessonVO.getEducationalInstitutionID() != null) {
            qPalXEducationalInstitution = iqPalXEducationalInstitutionService.findByID(iqPalXELessonVO.getEducationalInstitutionID());
        }

        // Load up the TutorialLevel calendar
        TutorialLevelCalendar tutorialLevelCalendar = iTutorialLevelCalendarService.findByID(iqPalXELessonVO.getTutorialLevelCalendarID());

        QPalXELesson qPalXELesson = QPalXELesson.builder()
                .lessonName(iqPalXELessonVO.getLessonName())
                .lessonDescription(iqPalXELessonVO.getLessonDescription())
                .eLearningCourse(eLearningCourse)
                .tutorialLevelCalendar(tutorialLevelCalendar)
                .qPalXEducationalInstitution(qPalXEducationalInstitution)
                .eLearningMediaContent(iqPalXELessonVO.getELearningMediaContent())
                .proficiencyRankingScaleFloor(iqPalXELessonVO.getProficiencyRankingScaleFloorE())
                .proficiencyRankingScaleCeiling(iqPalXELessonVO.getProficiencyRankingScaleCeilingE())
                .lessonActive(iqPalXELessonVO.isActive())
                .entryDate(new DateTime())
                .build();

        iqPalXELessonRepository.save(qPalXELesson);
    }

    @Override
    public boolean isELessonDeletable(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.info("Checking to see if QPalXLesson with id: {} has active items....", qPalXELesson.getId());
        Long [] queryParams = new Long[]{qPalXELesson.getId(), qPalXELesson.getId(), qPalXELesson.getId()};
        Integer activeItemsCount = jdbcTemplate.queryForObject(lessonActiveItemsSQL, Integer.class, queryParams);
        return activeItemsCount != null ? activeItemsCount.intValue() == 0 : false;
    }

    @Transactional
    @Override
    public void deleteQPalXELesson(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.info("Attempting to delete qPalXELesson with ID: {}", qPalXELesson.getId());
        iqPalXELessonRepository.delete(qPalXELesson);
    }

    @PostConstruct
    private void constructSQL() throws IOException {
        LOGGER.info("Loading sql from resource: {}", sqlResource);
        lessonActiveItemsSQL  = Resources.toString(sqlResource.getURL(), Charsets.UTF_8);
    }


}
