package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IStudentEnrolmentRecordRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultStudentEnrolmentRecordService")
public class DefaultStudentEnrolmentRecordService implements IStudentEnrolmentRecordService {



    @Autowired
    private IStudentEnrolmentRecordRepository iStudentEnrolmentRecordRepository;

    public static final String SPRING_BEAN = "quaza.solutions.qpalx.elearning.service.DefaultStudentEnrolmentRecordService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultStudentEnrolmentRecordService.class);


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


}
