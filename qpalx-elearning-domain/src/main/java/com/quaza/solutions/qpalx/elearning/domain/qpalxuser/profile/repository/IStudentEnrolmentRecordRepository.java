package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IStudentEnrolmentRecordRepository extends CrudRepository<StudentEnrolmentRecord, Long> {


    /**
     * For the given QPalXUser, find a currently active StudentEnrolmentRecord.  Only Students on the platform will have an enrolment record.
     *
     * @param qPalXUser
     * @return StudentEnrolmentRecord
     */
    @Query("Select studentEnrolmentRecord From StudentEnrolmentRecord studentEnrolmentRecord Where studentEnrolmentRecord.qpalxUser = ?1 And studentEnrolmentRecord.enrolmentEffectiveDate is not null And studentEnrolmentRecord.enrolmentEndDate is not null")
    public StudentEnrolmentRecord findCurrentStudentEnrolmentRecord(QPalXUser qPalXUser);

    /**
     * Find all current StudentEnrolmentRecord's for the educationalInstitution passed in as argument.
     *
     * @param educationalInstitution
     * @return
     */
    @Query("Select studentEnrolmentRecord " +
            "From StudentEnrolmentRecord studentEnrolmentRecord " +
            "Where studentEnrolmentRecord.educationalInstitution = ?1 " +
            "And studentEnrolmentRecord.enrolmentEffectiveDate is not null " +
            "And studentEnrolmentRecord.enrolmentEndDate is not null ")
    public List<StudentEnrolmentRecord> findCurrentStudentsEnrolmentRecordForEducationalInstitution(final QPalXEducationalInstitution educationalInstitution);


}
