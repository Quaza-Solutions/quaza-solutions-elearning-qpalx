package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXELessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXELessonRepository;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.domain.util.IEntityHasOrderInfo;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.ITutorialLevelCalendarService;
import com.quaza.solutions.qpalx.elearning.service.util.ElementHasOrderInfoUtil;
import com.quaza.solutions.qpalx.elearning.service.util.IElementHasOrderInfoUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author manyce400
 */
@Service(QPalXELessonService.BEAN_NAME)
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
    @Qualifier(ElementHasOrderInfoUtil.BEAN_NAME)
    private IElementHasOrderInfoUtil iElementHasOrderInfoUtil;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService")
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;


    public static final String BEAN_NAME = "quaza.solutions.qpalx.elearning.service.QPalXELessonService";


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
    public List<QPalXELesson> findQPalXELessonByCourseWithProficiency(ELearningCourse eLearningCourse, ProficiencyRankingScaleE proficiencyRankingScaleE) {
        List<QPalXELesson> courseLessons = findQPalXELessonByCourse(eLearningCourse);

        for(QPalXELesson qPalXELesson : courseLessons) {
            ProficiencyRankingScaleE floor = qPalXELesson.getProficiencyRankingScaleFloor();
            ProficiencyRankingScaleE ceiling = qPalXELesson.getProficiencyRankingScaleCeiling();

            // Check to see if the passed proficiencyRankingScaleE matches the floor and ceiling requirements of the lesson
            boolean isBetween = proficiencyRankingScaleE.isProficiencyRankingBetweenFloorAndCeiling(floor, ceiling);

            if(isBetween) {
                courseLessons.add(qPalXELesson);
            }
        }

        return courseLessons;
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


    @Override
    @Transactional
    public void createAndSaveQPalXELesson(IQPalXELessonVO iqPalXELessonVO) {
        Assert.notNull(iqPalXELessonVO, "iqPalXELessonVO cannot be null");
        Assert.notNull(iqPalXELessonVO.getELearningCourseID(), "Non null ELearningCourse ID required");
        Assert.notNull(iqPalXELessonVO.getTutorialLevelCalendarID(), "Non null valid TutorialLevelCalendar ID required");
        LOGGER.info("Creating new QPalXELesson with value object iqPalXELessonVO: {}", iqPalXELessonVO);

        // Load up ELearningCourse instance
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(iqPalXELessonVO.getELearningCourseID());
        Set<QPalXELesson> qPalXELessons = eLearningCourse.getQPalXELessons();

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

        // Build and set the ElementOrder for this new Lesson
        List<IEntityHasOrderInfo> iEntityHasOrderInfos = new ArrayList<>(qPalXELessons);
        iElementHasOrderInfoUtil.addNewEntityHasOrderInfoWithElementOrder(qPalXELesson, iEntityHasOrderInfos, iqPalXELessonRepository);
    }


    @Override
    @Transactional
    public void updateAndSaveQPalXELesson(QPalXELesson qPalXELesson, IQPalXELessonVO iqPalXELessonVO) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        Assert.notNull(iqPalXELessonVO, "iqPalXELessonVO cannot be null");
        Assert.notNull(iqPalXELessonVO.getELearningCourseID(), "Non null ELearningCourse ID required");
        Assert.notNull(iqPalXELessonVO.getTutorialLevelCalendarID(), "Non null valid TutorialLevelCalendar ID required");
        //Assert.notNull(iqPalXELessonVO.getELearningMediaContent(), "Non null valid ELearningMediaContent ID required");
        LOGGER.info("Updating and saving QPalXELesson with value object iqPalXELessonVO: {}", iqPalXELessonVO);

        // Load up the EducationalInstitution for this course if its been selected.
        QPalXEducationalInstitution qPalXEducationalInstitution = null;
        if (iqPalXELessonVO.getEducationalInstitutionID() != null) {
            qPalXEducationalInstitution = iqPalXEducationalInstitutionService.findByID(iqPalXELessonVO.getEducationalInstitutionID());
        }

        // Load up the TutorialLevel calendar
        TutorialLevelCalendar tutorialLevelCalendar = iTutorialLevelCalendarService.findByID(iqPalXELessonVO.getTutorialLevelCalendarID());

        qPalXELesson.setLessonName(iqPalXELessonVO.getLessonName());
        qPalXELesson.setLessonDescription(iqPalXELessonVO.getLessonDescription());
        qPalXELesson.seteLearningMediaContent(iqPalXELessonVO.getELearningMediaContent());
        qPalXELesson.setTutorialLevelCalendar(tutorialLevelCalendar);
        qPalXELesson.setLessonActive(iqPalXELessonVO.isActive());
        qPalXELesson.setQPalXEducationalInstitution(qPalXEducationalInstitution);
        qPalXELesson.setProficiencyRankingScaleFloor(iqPalXELessonVO.getProficiencyRankingScaleFloorE());
        qPalXELesson.setProficiencyRankingScaleCeiling(iqPalXELessonVO.getProficiencyRankingScaleCeilingE());

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

    @Override
    @Transactional
    public void moveQPalXELessonUp(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.debug("Attempting to move lesson with ID: {} up", qPalXELesson.getId());

        ELearningCourse eLearningCourse = qPalXELesson.geteLearningCourse();
        TutorialLevelCalendar tutorialLevelCalendar = qPalXELesson.getTutorialLevelCalendar();
        List<QPalXELesson> qPalXELessonList = findQPalXELessonByCourse(eLearningCourse);

        // Ordering discriminator will be the TutorialLevelCalendar that we are dealing with
        Long orderingDiscriminator = tutorialLevelCalendar.getId();

        List<IEntityHasOrderInfo> iEntityHasOrderInfos = new ArrayList<>(qPalXELessonList);
        iElementHasOrderInfoUtil.moveElementUp(qPalXELesson, iEntityHasOrderInfos, iqPalXELessonRepository);
    }

    @Override
    @Transactional
    public void moveQPalXELessonDown(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.debug("Attempting to move lesson with ID: {} down", qPalXELesson.getId());

        ELearningCourse eLearningCourse = qPalXELesson.geteLearningCourse();
        TutorialLevelCalendar tutorialLevelCalendar = qPalXELesson.getTutorialLevelCalendar();
        List<QPalXELesson> qPalXELessonList = findQPalXELessonByCourse(eLearningCourse);

        List<IEntityHasOrderInfo> iEntityHasOrderInfos = new ArrayList<>(qPalXELessonList);
        iElementHasOrderInfoUtil.moveElementDown(qPalXELesson, iEntityHasOrderInfos, iqPalXELessonRepository);
    }


    @PostConstruct
    private void constructSQL() throws IOException {
        LOGGER.info("Loading sql from resource: {}", sqlResource);
        lessonActiveItemsSQL  = Resources.toString(sqlResource.getURL(), Charsets.UTF_8);
    }

}
