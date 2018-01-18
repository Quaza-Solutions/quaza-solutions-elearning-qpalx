package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.EnrollmentDecision;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.StudentEnrolmentRecord;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.IAdaptiveProficiencyRankingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultEnrollmentMasterServiceTest {


    @Mock
    private IAdaptiveProficiencyRankingService iAdaptiveProficiencyRankingService;

    @InjectMocks
    private DefaultEnrollmentMasterService defaultEnrollmentMasterService;


    @Test
    public void isAuthorizedEnrollmentRequestForHigherLevelTest() {
        // Build the target StudentTutorialGrade which Student wants to upgrade to
        StudentTutorialGrade targetStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 2")
                .enabled(true)
                .build();
        targetStudentTutorialGrade.setId(2L);

        // Build the current StudentTutorialGrade which Student is currently enrolled in
        StudentTutorialGrade currentStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 1")
                .enabled(true)
                .build();
        currentStudentTutorialGrade.setId(1L);

        // QPalXUser that wants to enroll
        QPalXUser student = buildMockQPalxUser();

        // Build QPalxUser StudentEnrolmentRecord
        StudentEnrolmentRecord studentEnrolmentRecord = StudentEnrolmentRecord.builder()
                .qpalxUser(student)
                .studentTutorialGrade(currentStudentTutorialGrade)
                .build();

        // This should be an authorized enrollment, Student is enrolling for JHS2 from JHS1.  This is one level directly above.
        EnrollmentDecision enrollmentDecision = defaultEnrollmentMasterService.isAuthorizedEnrollmentRequest(studentEnrolmentRecord, targetStudentTutorialGrade);
        Assert.assertFalse(enrollmentDecision.isEnrollmentDenied());
        System.out.println("isAuthorized = " + enrollmentDecision.isEnrollmentDenied());
    }


    @Test
    public void isAuthorizedEnrollmentRequestForLowerLevelTest() {
        // Build the target StudentTutorialGrade which Student wants to upgrade to
        StudentTutorialGrade targetStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 1")
                .enabled(true)
                .build();
        targetStudentTutorialGrade.setId(1L);

        // Build the current StudentTutorialGrade which Student is currently enrolled in
        StudentTutorialGrade currentStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 2")
                .enabled(true)
                .build();
        currentStudentTutorialGrade.setId(2L);

        // QPalXUser that wants to enroll
        QPalXUser student = buildMockQPalxUser();

        // Build QPalxUser StudentEnrolmentRecord
        StudentEnrolmentRecord studentEnrolmentRecord = StudentEnrolmentRecord.builder()
                .qpalxUser(student)
                .studentTutorialGrade(currentStudentTutorialGrade)
                .build();

        // This should be an authorized enrollment, Student is enrolling for JHS1 from JHS2.  This is below their current enrollment level.  Student can always move down to reinforce concepts they are weak on
        EnrollmentDecision enrollmentDecision = defaultEnrollmentMasterService.isAuthorizedEnrollmentRequest(studentEnrolmentRecord, targetStudentTutorialGrade);
        Assert.assertFalse(enrollmentDecision.isEnrollmentDenied());
        System.out.println("isAuthorized = " + enrollmentDecision.isEnrollmentDenied());
    }

    @Test
    public void isAuthorizedEnrollmentRequestForTwoLowerLevelTest() {
        // Build the target StudentTutorialGrade which Student wants to upgrade to
        StudentTutorialGrade targetStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 3")
                .enabled(true)
                .build();
        targetStudentTutorialGrade.setId(3L);

        // Build the current StudentTutorialGrade which Student is currently enrolled in
        StudentTutorialGrade currentStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 1")
                .enabled(true)
                .build();
        currentStudentTutorialGrade.setId(1L);

        // QPalXUser that wants to enroll
        QPalXUser student = buildMockQPalxUser();

        // Build QPalxUser StudentEnrolmentRecord
        StudentEnrolmentRecord studentEnrolmentRecord = StudentEnrolmentRecord.builder()
                .qpalxUser(student)
                .studentTutorialGrade(currentStudentTutorialGrade)
                .build();

        // This should not be authorized enrollment, Student is enrolling for JHS3 from JHS1.  This 2 levels above their current enrollment.
        EnrollmentDecision enrollmentDecision = defaultEnrollmentMasterService.isAuthorizedEnrollmentRequest(studentEnrolmentRecord, targetStudentTutorialGrade);
        Assert.assertTrue(enrollmentDecision.isEnrollmentDenied());
        System.out.println("isAuthorized = " + enrollmentDecision.isEnrollmentDenied());
    }

    @Test
    public void authorizeEnrollmentRequestNoWeakCurriculumPerformancesTest() {
        StudentTutorialGrade targetStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 2")
                .enabled(true)
                .build();
        targetStudentTutorialGrade.setId(2L);

        // Build the current StudentTutorialGrade which Student is currently enrolled in
        StudentTutorialGrade currentStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 1")
                .enabled(true)
                .build();
        currentStudentTutorialGrade.setId(1L);

        // QPalXUser that wants to enroll
        QPalXUser student = buildMockQPalxUser();

        // Build QPalxUser StudentEnrolmentRecord
        StudentEnrolmentRecord studentEnrolmentRecord = StudentEnrolmentRecord.builder()
                .qpalxUser(student)
                .studentTutorialGrade(currentStudentTutorialGrade)
                .build();

        // Simulate call for below average AdaptiveProficiencyRanking returning no results
        Mockito.when(iAdaptiveProficiencyRankingService.findBelowAverageAdaptiveProficiencyRankings(student)).thenReturn(ImmutableList.of());
        EnrollmentDecision enrollmentDecision = defaultEnrollmentMasterService.authorizeEnrollmentRequest(studentEnrolmentRecord, targetStudentTutorialGrade);
        Assert.assertFalse(enrollmentDecision.isEnrollmentDenied());
        Assert.assertTrue(enrollmentDecision.getAdaptiveProficiencyRankingAnalysis().size() == 0);
    }

    @Test
    public void authorizeEnrollmentRequestWeakCurriculumPerformancesTest() {
        StudentTutorialGrade targetStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 2")
                .enabled(true)
                .build();
        targetStudentTutorialGrade.setId(2L);

        // Build the current StudentTutorialGrade which Student is currently enrolled in
        StudentTutorialGrade currentStudentTutorialGrade = StudentTutorialGrade.builder()
                .tutorialGrade("JHS 1")
                .enabled(true)
                .build();
        currentStudentTutorialGrade.setId(1L);

        // QPalXUser that wants to enroll
        QPalXUser student = buildMockQPalxUser();

        // Build QPalxUser StudentEnrolmentRecord
        StudentEnrolmentRecord studentEnrolmentRecord = StudentEnrolmentRecord.builder()
                .qpalxUser(student)
                .studentTutorialGrade(currentStudentTutorialGrade)
                .build();

        // Simulate call for below average AdaptiveProficiencyRanking returning no results
        List<AdaptiveProficiencyRanking> currentAdaptiveProficiencyRankingsBelowAverage = buildMockBelowAverageAdaptiveProficiencyRanking(student);

        Mockito.when(iAdaptiveProficiencyRankingService.findBelowAverageAdaptiveProficiencyRankings(student, currentStudentTutorialGrade)).thenReturn(currentAdaptiveProficiencyRankingsBelowAverage);
        EnrollmentDecision enrollmentDecision = defaultEnrollmentMasterService.authorizeEnrollmentRequest(studentEnrolmentRecord, targetStudentTutorialGrade);
        Assert.assertTrue(enrollmentDecision.isEnrollmentDenied());
        Assert.assertTrue(enrollmentDecision.getAdaptiveProficiencyRankingAnalysis().size() >= 1);
    }

    private QPalXUser buildMockQPalxUser() {
        QPalXUser qPalXUser = QPalXUser.builder()
                .email("manyce400@gmail.com")
                .build();
        return qPalXUser;
    }

    private List<AdaptiveProficiencyRanking> buildMockBelowAverageAdaptiveProficiencyRanking(QPalXUser student) {
        ELearningCurriculum eLearningCurriculum = new ELearningCurriculum();
        eLearningCurriculum.setCurriculumName("Mathematics");

        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .qpalxUser(student)
                .eLearningCurriculum(eLearningCurriculum)
                .proficiencyRankingScaleE(ProficiencyRankingScaleE.THREE)
                .build();
        return ImmutableList.of(adaptiveProficiencyRanking);
    }


}
