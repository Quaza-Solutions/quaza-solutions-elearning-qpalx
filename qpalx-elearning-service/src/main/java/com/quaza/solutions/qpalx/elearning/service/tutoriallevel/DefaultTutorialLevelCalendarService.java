package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseActivityRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialCalendarMonthE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.ITutorialLevelCalendarRepository;
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
@Service("quaza.solutions.qpalx.elearning.service.DefaultTutorialLevelCalendarService")
public class DefaultTutorialLevelCalendarService implements ITutorialLevelCalendarService {



    @Autowired
    private ITutorialLevelCalendarRepository iTutorialLevelCalendarRepository;

    @Autowired
    private IELearningCourseActivityRepository ieLearningCourseActivityRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultStudentEnrolmentRecordService")
    private IStudentEnrolmentRecordService iStudentEnrolmentRecordService;

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

        // Get student enrolment record to identify tutorial level
        StudentEnrolmentRecord studentEnrolmentRecord = iStudentEnrolmentRecordService.findCurrentStudentEnrolmentRecord(qPalXUser);
        if(studentEnrolmentRecord == null) {
            LOGGER.warn("No enrolment recored was found for student: {}.  Investigate student signup process", qPalXUser.getEmail());
            return Optional.empty();
        }

        StudentTutorialLevel studentTutorialLevel = studentEnrolmentRecord.getStudentTutorialGrade().getStudentTutorialLevel();
        return findCurrentCalendarByTutorialLevel(studentTutorialLevel);
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
