package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.EnrollmentDecision;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IStudentEnrolmentRecordRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.DefaultAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveProficiencyRankingService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(DefaultStudentEnrollmentRecordService.SPRING_BEAN)
public class DefaultStudentEnrollmentRecordService implements IStudentEnrollmentRecordService {



    @Autowired
    @Qualifier(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;


    @Autowired
    private IStudentEnrolmentRecordRepository iStudentEnrolmentRecordRepository;

    public static final String SPRING_BEAN = "quaza.solutions.qpalx.elearning.service.DefaultStudentEnrollmentRecordService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultStudentEnrollmentRecordService.class);


    @Override
    @Transactional
    public void createStudentEnrolmentRecord(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade, QPalXEducationalInstitution educationalInstitution) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");

        LOGGER.info("Creating new StudentEnrolmentRecord for user with email: {}", qPalXUser.getEmail());

        StudentEnrolmentRecord studentEnrolmentRecord = StudentEnrolmentRecord.builder()
                .qpalxUser(qPalXUser)
                .studentTutorialGrade(studentTutorialGrade)
                .enrolmentEffectiveDate(new DateTime())
                .educationalInstitution(educationalInstitution)
                .build();
        LOGGER.debug("Saving StudentEnrolmentRecord: {}", studentEnrolmentRecord);
        iStudentEnrolmentRecordRepository.save(studentEnrolmentRecord);
    }

    @Override
    public StudentEnrolmentRecord findCurrentStudentEnrolmentRecord(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding current StudentEnrolmentRecord for qPalxUser: {}", qPalXUser.getEmail());
        return iStudentEnrolmentRecordRepository.findCurrentStudentEnrolmentRecord(qPalXUser);
    }

    @Override
    public List<StudentEnrolmentRecord> findCurrentStudentsEnrolmentRecordForEducationalInstitution(QPalXEducationalInstitution educationalInstitution) {
        Assert.notNull(educationalInstitution, "educationalInstitution cannot be null");
        LOGGER.info("Finding all StudentEnrolmentRecords for educationalInstitution: {}", educationalInstitution);
        return iStudentEnrolmentRecordRepository.findCurrentStudentsEnrolmentRecordForEducationalInstitution(educationalInstitution);
    }

    @Override
    public EnrollmentDecision enrollStudentTutorialGrade(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");

        LOGGER.info("Attempting to switch StudentTutorialGrade on enrolment record for user: {} to: {}", qPalXUser.getEmail(), studentTutorialGrade.getTutorialGrade());

        StudentEnrolmentRecord studentEnrolmentRecord = findCurrentStudentEnrolmentRecord(qPalXUser);

        // Check to see IF this user can enroll to this new StudentTutorialGrade
        List<AdaptiveProficiencyRanking> adaptiveProficiencyRankingAnalysis = getStudentAdaptiveProficiencyRankingAnalysis(studentEnrolmentRecord, studentTutorialGrade);

        if (adaptiveProficiencyRankingAnalysis.isEmpty()) {
            LOGGER.info("Student can upgrade to new StudentTutorialGrade");
            studentEnrolmentRecord.setStudentTutorialGrade(studentTutorialGrade);
            iStudentEnrolmentRecordRepository.save(studentEnrolmentRecord);
            EnrollmentDecision enrollmentCompletedDecision = new EnrollmentDecision(studentTutorialGrade, true);
            return enrollmentCompletedDecision;
        }

        LOGGER.info("Student request to enroll for new StudentTutorialGrade has been rejected.");
        EnrollmentDecision enrollmentDeniedDecision = new EnrollmentDecision(studentTutorialGrade, adaptiveProficiencyRankingAnalysis, true);
        return enrollmentDeniedDecision;
    }

    private List<AdaptiveProficiencyRanking> getStudentAdaptiveProficiencyRankingAnalysis(StudentEnrolmentRecord studentEnrolmentRecord, StudentTutorialGrade targetStudentTutorialGrade) {
        QPalXUser qPalXUser = studentEnrolmentRecord.getQpalxUser();
        StudentTutorialGrade currentStudentTutorialGrade = studentEnrolmentRecord.getStudentTutorialGrade();

//        if(currentStudentTutorialGrade.getId() < targetStudentTutorialGrade.getId()) {
//            // IF Student is switching to a lower StudentTutorialGrade then allow switch, we will ignore their current performance at their current level
//            LOGGER.info("Student is switching to a new StudentTutorialGrade which is lesser than their current, allowing switch");
//            return ImmutableList.of();
//        }

        // Validate that this user is performing as expected on this current level in order to approve them being enrolled in a higher tutorial grade
        LOGGER.info("Analyzing Students current AdaptiveProficiencyRankings to determine IF student is performing below average on any curriculum...");
        List<AdaptiveProficiencyRanking> currentAdaptiveProficiencyRankings = iAdaptiveProficiencyRankingService.findBelowAverageAdaptiveProficiencyRankings(qPalXUser);
        return ImmutableList.copyOf(currentAdaptiveProficiencyRankings);
    }


}
