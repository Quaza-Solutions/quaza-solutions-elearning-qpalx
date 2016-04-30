package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

import java.util.List;

/**
 * @author manyce400
 */
public interface IStudentEnrolmentRecordService {


    public void createStudentEnrolmentRecord(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade, QPalXEducationalInstitution educationalInstitution);

    public StudentEnrolmentRecord findCurrentStudentEnrolmentRecord(QPalXUser qPalXUser);

    public List<StudentEnrolmentRecord> findCurrentStudentsEnrolmentRecordForEducationalInstitution(final QPalXEducationalInstitution educationalInstitution);

}
