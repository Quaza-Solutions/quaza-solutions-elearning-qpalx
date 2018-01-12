package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

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
    @Qualifier(DefaultEnrollmentMasterService.SPRING_BEAN)
    private IEnrollmentMasterService iEnrollmentMasterService;


    @Autowired
    private IStudentEnrolmentRecordRepository iStudentEnrolmentRecordRepository;


    @Autowired
    @Qualifier(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

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
    @Transactional
    public EnrollmentDecision enrollStudentTutorialGrade(QPalXUser qPalXUser, StudentTutorialGrade targetStudentTutorialGrade) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(targetStudentTutorialGrade, "targetStudentTutorialGrade cannot be null");

        LOGGER.info("Attempting to enrol Student in a different StudentTutorialGrade Student: {} To: {}", qPalXUser.getEmail(), targetStudentTutorialGrade.getTutorialGrade());

        StudentEnrolmentRecord studentEnrolmentRecord = findCurrentStudentEnrolmentRecord(qPalXUser);
        EnrollmentDecision enrollmentDeniedDecision = iEnrollmentMasterService.authorizeEnrollmentRequest(studentEnrolmentRecord, targetStudentTutorialGrade);

        if (!enrollmentDeniedDecision.isEnrollmentDenied()) {
            LOGGER.info("Completing successful enrollment of student to  StudentTutorialGrade: {}", targetStudentTutorialGrade);

            // Check to see IF the student has any experience on the target StudentTutorialGrade already, if not build enrollment AdaptiveProficiencyRankings
            List<AdaptiveProficiencyRanking> previousAdaptiveProficiencyRankings = iAdaptiveProficiencyRankingService.findStudentAdaptiveProficiencyRankings(qPalXUser, targetStudentTutorialGrade);

            if (previousAdaptiveProficiencyRankings != null && previousAdaptiveProficiencyRankings.size() > 0) {
                iAdaptiveProficiencyRankingService.closeOutAdaptiveProficiencyRanking(previousAdaptiveProficiencyRankings);
            }

            // Now we need to save the new AdaptiveProficiencyRankings from enrollment to targetStudentTutorialGrade
            iAdaptiveProficiencyRankingService.buildInitialAdaptiveProficiencyRanking(qPalXUser, targetStudentTutorialGrade);

            // Next update the Student tutorial grade to the new target tutorial grade
            studentEnrolmentRecord.setStudentTutorialGrade(targetStudentTutorialGrade);
            iStudentEnrolmentRecordRepository.save(studentEnrolmentRecord);
        }

        return enrollmentDeniedDecision;
    }

}
