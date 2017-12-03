package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseActivityRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.ContentAdminProfileRecord;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialCalendarMonthE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.ITutorialLevelCalendarRepository;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.DefaultContentAdminProfileRecordService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.IContentAdminProfileRecordService;
import com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.IStudentEnrolmentRecordService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author manyce400
 */
@Service(DefaultTutorialLevelCalendarService.SPRING_BEAN)
public class DefaultTutorialLevelCalendarService implements ITutorialLevelCalendarService {



    @Autowired
    private ITutorialLevelCalendarRepository iTutorialLevelCalendarRepository;

    @Autowired
    private IELearningCourseActivityRepository ieLearningCourseActivityRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultStudentEnrolmentRecordService")
    private IStudentEnrolmentRecordService iStudentEnrolmentRecordService;

    @Autowired
    @Qualifier(DefaultContentAdminProfileRecordService.SPRING_BEAN)
    private IContentAdminProfileRecordService iContentAdminProfileRecordService;

    public static final String SPRING_BEAN = "quaza.solutions.qpalx.elearning.service.DefaultTutorialLevelCalendarService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultTutorialLevelCalendarService.class);


    @Override
    public TutorialLevelCalendar findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding TutorialLevelCalendar with id: {}", id);
        return iTutorialLevelCalendarRepository.findOne(id);
    }

    @Override
    public Optional<TutorialLevelCalendar> findCurrentCalendarByTutorialLevel(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("finding the current tutorial level calendar for student: {}", qPalXUser.getEmail());

        if(qPalXUser.getUserType() == QPalxUserTypeE.STUDENT) {
            StudentEnrolmentRecord studentEnrolmentRecord = iStudentEnrolmentRecordService.findCurrentStudentEnrolmentRecord(qPalXUser);
            StudentTutorialLevel studentTutorialLevel = studentEnrolmentRecord.getStudentTutorialGrade().getStudentTutorialLevel();
            return findCurrentCalendarByTutorialLevel(studentTutorialLevel);
        } else if(qPalXUser.getUserType() == QPalxUserTypeE.CONTENT_DEVELOPER) {
            ContentAdminProfileRecord contentAdminProfileRecord = iContentAdminProfileRecordService.findEnabledContentAdminProfileRecord(qPalXUser);
            StudentTutorialLevel studentTutorialLevel = contentAdminProfileRecord.getStudentTutorialLevel();
            return findCurrentCalendarByTutorialLevel(studentTutorialLevel);
        }

        return Optional.empty();
    }

    @Override
    public Optional<TutorialLevelCalendar> findCurrentCalendarByTutorialLevel(StudentTutorialLevel studentTutorialLevel) {
        Assert.notNull(studentTutorialLevel, "studentTutorialLevel cannot be null");
        LOGGER.info("Finding the current TutorialLevelCalendar for studentTutorialLevel:> {}", studentTutorialLevel);

        // Find all TutorialLevelCalendar instances studentTutorialLevel
        List<TutorialLevelCalendar> calendars = iTutorialLevelCalendarRepository.findCalendarForStudentTutorialLevel(studentTutorialLevel);

        Optional<TutorialLevelCalendar> defaultTutorialLevelCalendar = calendars.stream()
                .filter(calendar ->  isTutorialLevelCalendarValidForCurrentMonth(calendar)).findFirst();

        if(!defaultTutorialLevelCalendar.isPresent()) {
            // Current month is not a school term/semmester.  School is out so return first
            LOGGER.info("Current month is not a valid tutorial level calendar month, school is out returning default by order");
            TutorialLevelCalendar tutorialLevelCalendar = iTutorialLevelCalendarRepository.findOne(1L);
            defaultTutorialLevelCalendar = Optional.of(tutorialLevelCalendar);
        }

        LOGGER.info("Returning default student TutorialLevelCalendar: {}", defaultTutorialLevelCalendar.get());
        return defaultTutorialLevelCalendar;
    }

    @Override
    public List<TutorialLevelCalendar> findAllForQPalXUser(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("finding all tutorial level calendar for QPalxUser: {}", qPalXUser.getEmail());

        if(qPalXUser.getUserType() == QPalxUserTypeE.STUDENT) {
            StudentEnrolmentRecord studentEnrolmentRecord = iStudentEnrolmentRecordService.findCurrentStudentEnrolmentRecord(qPalXUser);
            if(studentEnrolmentRecord == null) {
                return findAllByStudentTutorialLevel(studentEnrolmentRecord.getStudentTutorialGrade().getStudentTutorialLevel());
            }
        } else if(qPalXUser.getUserType() == QPalxUserTypeE.CONTENT_DEVELOPER) {
            ContentAdminProfileRecord contentAdminProfileRecord = iContentAdminProfileRecordService.findEnabledContentAdminProfileRecord(qPalXUser);
            return findAllByStudentTutorialLevel(contentAdminProfileRecord.getStudentTutorialLevel());
        }

        return ImmutableList.of();
    }

    @Override
    public List<TutorialLevelCalendar> findAllByStudentTutorialLevel(StudentTutorialLevel studentTutorialLevel) {
        Assert.notNull(studentTutorialLevel, "studentTutorialLevel cannot be null");
        LOGGER.debug("Finding all TutorialLevelCalendar for studentTutorialLevel: {}", studentTutorialLevel);
        return iTutorialLevelCalendarRepository.findCalendarForStudentTutorialLevel(studentTutorialLevel);
    }

    @Override
    public Map<TutorialLevelCalendar, ELearningCourseActivity> findAllCourseELearningActivities(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.info("Building all Map of all TutorialLevelCalendar to Course activities for eLearningCourse:> {}", eLearningCourse.getCourseName());
        List<ELearningCourseActivity> eLearningCourseActivities = ieLearningCourseActivityRepository.findELearningCourseActivities(eLearningCourse);


        return null;
    }


    private boolean isTutorialLevelCalendarValidForCurrentMonth(TutorialLevelCalendar tutorialLevelCalendar) {
        TutorialCalendarMonthE startMonthE = TutorialCalendarMonthE.findByMonthString(tutorialLevelCalendar.getCalendarItemStartMonth());
        TutorialCalendarMonthE endMonthE = TutorialCalendarMonthE.findByMonthString(tutorialLevelCalendar.getCalendarItemEndMonth());

        // We need to get the current month to check if its in range
        DateTime dateTime = new DateTime();
        TutorialCalendarMonthE inputMonthE = TutorialCalendarMonthE.findByMonthString(dateTime.monthOfYear().getAsText());

        LOGGER.info("Checking if currentMont: {} is between startMonth: {} and endMonth: {}", inputMonthE, startMonthE, endMonthE);
        return TutorialCalendarMonthE.isMonthInTutorialCalendarMonthRange(inputMonthE, startMonthE, endMonthE);
    }


}
