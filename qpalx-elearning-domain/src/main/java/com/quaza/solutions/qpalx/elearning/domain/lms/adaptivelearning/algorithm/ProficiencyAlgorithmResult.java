package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.algorithm;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author manyce400
 */
public class ProficiencyAlgorithmResult {


    private Double algorithmScore;

    private String algorithmFocusArea;

    public ProficiencyAlgorithmResult(Double algorithmScore, String algorithmFocusArea) {
        this.algorithmScore = algorithmScore;
        this.algorithmFocusArea = algorithmFocusArea;
    }

    public Double getAlgorithmScore() {
        return algorithmScore;
    }

    public String getAlgorithmFocusArea() {
        return algorithmFocusArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProficiencyAlgorithmResult that = (ProficiencyAlgorithmResult) o;

        return new EqualsBuilder()
                .append(algorithmScore, that.algorithmScore)
                .append(algorithmFocusArea, that.algorithmFocusArea)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(algorithmScore)
                .append(algorithmFocusArea)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("algorithmScore", algorithmScore)
                .append("algorithmFocusArea", algorithmFocusArea)
                .toString();
    }

}
