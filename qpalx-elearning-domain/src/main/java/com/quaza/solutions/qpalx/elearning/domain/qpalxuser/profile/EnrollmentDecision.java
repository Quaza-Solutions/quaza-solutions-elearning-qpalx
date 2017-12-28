package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manyce400
 */
public class EnrollmentDecision {


    // The StudentTutorialGrade that Student requested to enroll in
    private StudentTutorialGrade requestedStudentTutorialGrade;

    // Analysis of Student AdaptiveProficiencyRanking, this will be empty IF enrollment request is denied
    private List<AdaptiveProficiencyRanking> adaptiveProficiencyRankingAnalysis = new ArrayList<>();

    // True IF the enrollment request has been denied
    private boolean enrollmentDenied;


    public EnrollmentDecision(StudentTutorialGrade requestedStudentTutorialGrade, boolean enrollmentDenied) {
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
        return new ToStringBuilder(this)
                .append("requestedStudentTutorialGrade", requestedStudentTutorialGrade)
                .append("adaptiveProficiencyRankingAnalysis", adaptiveProficiencyRankingAnalysis)
                .append("enrollmentDenied", enrollmentDenied)
                .toString();
    }
}
