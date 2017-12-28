package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.EnrollmentDecision;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.DefaultAdaptiveProficiencyRankingService;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveProficiencyRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(DefaultEnrollmentMasterService.SPRING_BEAN)
public class DefaultEnrollmentMasterService implements IEnrollmentMasterService {



    @Autowired
    @Qualifier(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    public static final String SPRING_BEAN = "com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile.DefaultEnrollmentMasterService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultEnrollmentMasterService.class);

    @Override
    public EnrollmentDecision authorizeEnrollmentRequest(StudentEnrolmentRecord studentEnrolmentRecord, StudentTutorialGrade targetStudentTutorialGrade) {
        Assert.notNull(studentEnrolmentRecord, "studentEnrolmentRecord cannot be null");
        Assert.notNull(studentEnrolmentRecord.getQpalxUser(), "qPalXUser on studentEnrolmentRecord cannot be null");
        Assert.notNull(targetStudentTutorialGrade, "targetStudentTutorialGrade cannot be null");

        QPalXUser qPalXUser = studentEnrolmentRecord.getQpalxUser();
        StudentTutorialGrade currentStudentTutorialGrade = studentEnrolmentRecord.getStudentTutorialGrade();

        LOGGER.info("Attempting to authorize and approve enrollment reques for student: {} to tutorialGrade: {}", studentEnrolmentRecord.getQpalxUser().getEmail(), targetStudentTutorialGrade.getTutorialGrade());

        boolean isAuthorizedEnrollmentRequest = isAuthorizedEnrollmentRequest(studentEnrolmentRecord, targetStudentTutorialGrade);
        if(isAuthorizedEnrollmentRequest) {
            boolean enrollmentForLowerTutorialGrade = targetStudentTutorialGrade.isLowerStudentTutorialGrade(currentStudentTutorialGrade);

            if(enrollmentForLowerTutorialGrade) {
                LOGGER.info("Enrollment request is for a lower StudentTutorialGrade, completed enrollment analysis, returning enrollment allowed decision");
                EnrollmentDecision enrollmentDecision = EnrollmentDecision.approvedInstance(targetStudentTutorialGrade);
                return enrollmentDecision;
            } else {
                LOGGER.info("Enrollment request is for a higher StudentTutorialGrade, analyzing weaknesses in Students Curriculum performance as part of approval process.  Enrollment will be denied IF Student is currently below average in any Curriculum.");
                List<AdaptiveProficiencyRanking> currentAdaptiveProficiencyRankingsBelowAverage = iAdaptiveProficiencyRankingService.findBelowAverageAdaptiveProficiencyRankings(qPalXUser);

                if(currentAdaptiveProficiencyRankingsBelowAverage.isEmpty()) {
                    LOGGER.warn("Enrollment request has been approved, no Curriculum performances below average found");
                    EnrollmentDecision enrollmentDecision = EnrollmentDecision.approvedInstance(targetStudentTutorialGrade);
                    return enrollmentDecision;
                } else {
                    LOGGER.warn("Enrollment request has been denied, found count of: {} Curriculum performances below average for this user", currentAdaptiveProficiencyRankingsBelowAverage.size());
                    EnrollmentDecision enrollmentDecision = EnrollmentDecision.deniedInstance(targetStudentTutorialGrade, currentAdaptiveProficiencyRankingsBelowAverage);
                    return enrollmentDecision;
                }
            }
        }

        LOGGER.info("Student enrollment request cannot be authorized, returning denial EnrollmentDecision");
        EnrollmentDecision enrollmentDecision = EnrollmentDecision.deniedInstance(targetStudentTutorialGrade);
        return enrollmentDecision;
    }

    protected boolean isAuthorizedEnrollmentRequest(StudentEnrolmentRecord studentEnrolmentRecord, StudentTutorialGrade targetStudentTutorialGrade) {
        // Request is only valid IF this user is enrolling for a TutorialGrade only 1 Level higher than their current grade, and they have never been previously enrolled in that grade before.
        QPalXUser qPalXUser = studentEnrolmentRecord.getQpalxUser();
        StudentTutorialGrade currentStudentTutorialGrade = studentEnrolmentRecord.getStudentTutorialGrade();

        // Check IF this enrollment is for a higher StudentTutorialGrade than what the Student is currently on.
        boolean enrollmentForHigherTutorialGrade = targetStudentTutorialGrade.isHigherStudentTutorialGrade(currentStudentTutorialGrade);

        if (enrollmentForHigherTutorialGrade) {
            long levelsBetween = targetStudentTutorialGrade.getLevelsBetweenStudentTutorialGrades(currentStudentTutorialGrade);

            if(levelsBetween < 2) {
                LOGGER.info("Student is switching to a new StudentTutorialGrade which 1 level higher than their current level, enrollment is authorized");
                return true;
            } else {
                LOGGER.info("Student is switching to a new StudentTutorialGrade which 2 or more levels higher than their current level, enrollment is not authorized");
                return false;
            }
        } else {
            LOGGER.info("Enrollment is for a lower StudentTutorialGrade than what Student currently has, enrollment is authorized...");
            return true;
        }
    }

}