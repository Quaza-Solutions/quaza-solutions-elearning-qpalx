package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.EnrollmentDecision;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

/**
 * @author manyce400
 */
public interface IEnrollmentMasterService {

    // Executes enrollment logic to determine IF Student enrollment request for a new StudentTutorialGrade should be fulfilled
    public EnrollmentDecision authorizeEnrollmentRequest(StudentEnrolmentRecord studentEnrolmentRecord, StudentTutorialGrade targetStudentTutorialGrade);

}
