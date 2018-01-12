package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
public class EnrollmentDecision {



    // Optional Enrollment decision to provide more context on why and how decision was made
    private Optional<String> enrolmentDecisionMessage = Optional.empty();

    // The StudentTutorialGrade that Student requested to enroll in
    private StudentTutorialGrade requestedStudentTutorialGrade;

    // Analysis of Student AdaptiveProficiencyRanking, this will be empty IF enrollment request is denied
    private List<AdaptiveProficiencyRanking> adaptiveProficiencyRankingAnalysis = new ArrayList<>();

    // True IF the enrollment request has been denied
    private boolean enrollmentDenied;


    public EnrollmentDecision(StudentTutorialGrade requestedStudentTutorialGrade, boolean enrollmentDenied) {
        Assert.notNull(requestedStudentTutorialGrade, "requestedStudentTutorialGrade cannot be null");
        this.requestedStudentTutorialGrade = requestedStudentTutorialGrade;
        this.enrollmentDenied = enrollmentDenied;
    }

    public EnrollmentDecision(StudentTutorialGrade requestedStudentTutorialGrade, String enrolmentDecisionMessageString, boolean enrollmentDenied) {
        Assert.notNull(requestedStudentTutorialGrade, "requestedStudentTutorialGrade cannot be null");
        Assert.notNull(enrolmentDecisionMessageString, "enrolmentDecisionMessageString cannot be null");
        this.enrolmentDecisionMessage = enrolmentDecisionMessageString == null ? Optional.empty() : Optional.of(enrolmentDecisionMessageString);
        this.requestedStudentTutorialGrade = requestedStudentTutorialGrade;
        this.enrollmentDenied = enrollmentDenied;
    }

    public EnrollmentDecision(StudentTutorialGrade requestedStudentTutorialGrade, List<AdaptiveProficiencyRanking> adaptiveProficiencyRankingAnalysis, boolean enrollmentDenied) {
        Assert.notNull(requestedStudentTutorialGrade, "requestedStudentTutorialGrade cannot be null");
        Assert.notNull(adaptiveProficiencyRankingAnalysis, "requestedStudentTutorialGrade cannot be null");
        Assert.notNull(enrollmentDenied, "enrollmentDenied cannot be null");
        this.requestedStudentTutorialGrade = requestedStudentTutorialGrade;
        this.adaptiveProficiencyRankingAnalysis.addAll(adaptiveProficiencyRankingAnalysis);
        this.enrollmentDenied = enrollmentDenied;
    }


    public EnrollmentDecision(StudentTutorialGrade requestedStudentTutorialGrade, List<AdaptiveProficiencyRanking> adaptiveProficiencyRankingAnalysis, String enrolmentDecisionMessageString, boolean enrollmentDenied) {
        Assert.notNull(requestedStudentTutorialGrade, "requestedStudentTutorialGrade cannot be null");
        Assert.notNull(adaptiveProficiencyRankingAnalysis, "requestedStudentTutorialGrade cannot be null");
        Assert.notNull(enrolmentDecisionMessageString, "enrolmentDecisionMessageString cannot be null");
        this.enrolmentDecisionMessage = enrolmentDecisionMessageString == null ? Optional.empty() : Optional.of(enrolmentDecisionMessageString);
        this.requestedStudentTutorialGrade = requestedStudentTutorialGrade;
        this.adaptiveProficiencyRankingAnalysis.addAll(adaptiveProficiencyRankingAnalysis);
        this.enrollmentDenied = enrollmentDenied;
    }

    public boolean hasEnrolmentDecisionMessage() {
        return enrolmentDecisionMessage.isPresent();
    }

    public Optional<String> getEnrolmentDecisionMessage() {
        return enrolmentDecisionMessage;
    }

    public StudentTutorialGrade getRequestedStudentTutorialGrade() {
        return requestedStudentTutorialGrade;
    }

    public List<AdaptiveProficiencyRanking> getAdaptiveProficiencyRankingAnalysis() {
        return ImmutableList.copyOf(adaptiveProficiencyRankingAnalysis);
    }

    public boolean hasAdaptiveProficiencyRankingAnalysis() {
        return !adaptiveProficiencyRankingAnalysis.isEmpty();
    }

    public boolean isEnrollmentDenied() {
        return enrollmentDenied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EnrollmentDecision that = (EnrollmentDecision) o;

        return new EqualsBuilder()
                .append(enrollmentDenied, that.enrollmentDenied)
                .append(requestedStudentTutorialGrade, that.requestedStudentTutorialGrade)
                .append(adaptiveProficiencyRankingAnalysis, that.adaptiveProficiencyRankingAnalysis)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(requestedStudentTutorialGrade)
                .append(adaptiveProficiencyRankingAnalysis)
                .append(enrollmentDenied)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("requestedStudentTutorialGrade", requestedStudentTutorialGrade)
                .append("adaptiveProficiencyRankingAnalysis", adaptiveProficiencyRankingAnalysis)
                .append("enrollmentDenied", enrollmentDenied)
                .toString();
    }

    public static EnrollmentDecision approvedInstance(StudentTutorialGrade requestedStudentTutorialGrade) {
        EnrollmentDecision enrollmentDecision = new EnrollmentDecision(requestedStudentTutorialGrade, false);
        return enrollmentDecision;
    }

    public static EnrollmentDecision deniedInstance(StudentTutorialGrade requestedStudentTutorialGrade) {
        EnrollmentDecision enrollmentDecision = new EnrollmentDecision(requestedStudentTutorialGrade, true);
        return enrollmentDecision;
    }

    public static EnrollmentDecision deniedInstance(StudentTutorialGrade requestedStudentTutorialGrade, String enrolmentDecisionMessageString) {
        EnrollmentDecision enrollmentDecision = new EnrollmentDecision(requestedStudentTutorialGrade, enrolmentDecisionMessageString,true);
        return enrollmentDecision;
    }

    public static EnrollmentDecision deniedInstance(StudentTutorialGrade requestedStudentTutorialGrade, List<AdaptiveProficiencyRanking> adaptiveProficiencyRankingAnalysis) {
        EnrollmentDecision enrollmentDecision = new EnrollmentDecision(requestedStudentTutorialGrade, adaptiveProficiencyRankingAnalysis, true);
        return enrollmentDecision;
    }

}
