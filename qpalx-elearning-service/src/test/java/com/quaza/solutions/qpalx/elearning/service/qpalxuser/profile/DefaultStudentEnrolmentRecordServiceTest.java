package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IStudentEnrolmentRecordRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

/**
 * @RunWith annotation will initialize all Mockito mocks ahead of all test runs
 *
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultStudentEnrolmentRecordServiceTest {


    @Mock
    private IStudentEnrolmentRecordRepository iStudentEnrolmentRecordRepository;

    @Spy
    @InjectMocks
    private DefaultStudentEnrolmentRecordService iStudentEnrolmentRecordService;


    @Test
    public void testCreateStudentEnrolmentRecord() {
        QPalXUser qPalXUser = mockQPalXUser();
        StudentTutorialGrade studentTutorialGrade = mockStudentTutorialGrade();
        QPalXEducationalInstitution educationalInstitution = mockQPalXEducationalInstitution();

        // Capture the StudentEnrolmentRecord to be saved
        ArgumentCaptor<StudentEnrolmentRecord> argumentCaptor = ArgumentCaptor.forClass(StudentEnrolmentRecord.class);
        iStudentEnrolmentRecordService.createStudentEnrolmentRecord(qPalXUser, studentTutorialGrade, educationalInstitution);

        // Verify save gets called once and values of StudentEnrolmentRecord
        verify(iStudentEnrolmentRecordRepository, Mockito.times(1)).save(argumentCaptor.capture());
        StudentEnrolmentRecord studentEnrolmentRecordToBeSaved = argumentCaptor.getValue();
        Assert.assertNotNull(studentEnrolmentRecordToBeSaved);
        Assert.assertNull(studentEnrolmentRecordToBeSaved.getEnrolmentEndDate()); // No Enrolment end date as this is a newly registered enrolment.
        Assert.assertEquals(qPalXUser, studentEnrolmentRecordToBeSaved.getQpalxUser());
        Assert.assertEquals(studentTutorialGrade, studentEnrolmentRecordToBeSaved.getStudentTutorialGrade());
        Assert.assertEquals(educationalInstitution, studentEnrolmentRecordToBeSaved.getEducationalInstitution());
    }

    @Test
    public void testFindCurrentStudentEnrolmentRecord() {
        QPalXUser qPalXUser = mockQPalXUser();
        iStudentEnrolmentRecordService.findCurrentStudentEnrolmentRecord(qPalXUser);
        // verify findCurrentStudentEnrolmentRecord called once
        verify(iStudentEnrolmentRecordRepository, Mockito.times(1)).findCurrentStudentEnrolmentRecord(any());
    }

    @Test
    public void testFindCurrentStudentsEnrolmentRecordForEducationalInstitution() {
        QPalXEducationalInstitution educationalInstitution = mockQPalXEducationalInstitution();
        iStudentEnrolmentRecordService.findCurrentStudentsEnrolmentRecordForEducationalInstitution(educationalInstitution);
        // verify findCurrentStudentsEnrolmentRecordForEducationalInstitution called once
        verify(iStudentEnrolmentRecordRepository, Mockito.times(1)).findCurrentStudentsEnrolmentRecordForEducationalInstitution(any());

    }


    private QPalXUser mockQPalXUser() {
        return QPalXUser.builder()
                .firstName("Manny")
                .lastName("Jones")
                .build();
    }

    private StudentTutorialGrade mockStudentTutorialGrade() {
        return StudentTutorialGrade.builder()
                .tutorialGrade("Sophmore")
                .enabled(true)
                .build();
    }

    public QPalXEducationalInstitution mockQPalXEducationalInstitution() {
        return QPalXEducationalInstitution.builder()
                .name("Ross International High School")
                .code("RIS")
                .build();
    }
}
