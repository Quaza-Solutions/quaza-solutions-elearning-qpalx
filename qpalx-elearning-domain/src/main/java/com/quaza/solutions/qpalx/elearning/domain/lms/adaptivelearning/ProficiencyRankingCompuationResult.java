package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Domain object which holds results of an AdaptiveProficiencyRanking computation.
 *
 * @author manyce400
 */
public class ProficiencyRankingCompuationResult {


    private String resultUnavailableReason;

    private AdaptiveProficiencyRanking adaptiveProficiencyRanking;

    public ProficiencyRankingCompuationResult(String resultUnavailableReason, AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        // One or the other has to be present.  If resultUnavailableReason is null then we expect a non null adaptiveProficiencyRanking
        if (resultUnavailableReason != null) {
            Assert.isTrue(resultUnavailableReason != null && adaptiveProficiencyRanking == null ? true : false);
        } else {
            Assert.isTrue(adaptiveProficiencyRanking != null && resultUnavailableReason == null ? true : false);
        }

        this.resultUnavailableReason = resultUnavailableReason;
        this.adaptiveProficiencyRanking = adaptiveProficiencyRanking;
    }

    public Optional<String> getResultUnavailableReason() {
        if(resultUnavailableReason == null)
            return Optional.empty();
        return Optional.of(resultUnavailableReason);
    }

    public Optional<AdaptiveProficiencyRanking> getAdaptiveProficiencyRanking() {
        if(adaptiveProficiencyRanking == null)
            return Optional.empty();
        return Optional.of(adaptiveProficiencyRanking);
    }

    public boolean hasAdaptiveProficiencyRanking() {
        return adaptiveProficiencyRanking != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ProficiencyRankingCompuationResult that = (ProficiencyRankingCompuationResult) o;

        return new EqualsBuilder()
                .append(resultUnavailableReason, that.resultUnavailableReason)
                .append(adaptiveProficiencyRanking, that.adaptiveProficiencyRanking)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(resultUnavailableReason)
                .append(adaptiveProficiencyRanking)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("resultUnavailableReason", resultUnavailableReason)
                .append("adaptiveProficiencyRanking", adaptiveProficiencyRanking)
                .toString();
    }

    public static ProficiencyRankingCompuationResult newResultNoAdaptiveProficiencyRanking(String resultUnavailableReason) {
        Assert.notNull(resultUnavailableReason, "resultUnavailableReason");
        return new ProficiencyRankingCompuationResult(resultUnavailableReason, null);
    }

    public static ProficiencyRankingCompuationResult newResultAdaptiveProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        Assert.notNull(adaptiveProficiencyRanking, "adaptiveProficiencyRanking");
        return new ProficiencyRankingCompuationResult(null, adaptiveProficiencyRanking);
    }

    public static void main(String[] args) {
        ProficiencyRankingCompuationResult proficiencyRankingCompuationResult = ProficiencyRankingCompuationResult.newResultNoAdaptiveProficiencyRanking("Test");
        ProficiencyRankingCompuationResult proficiencyRankingCompuationResult2 = ProficiencyRankingCompuationResult.newResultAdaptiveProficiencyRanking(new AdaptiveProficiencyRanking());
        System.out.println("proficiencyRankingCompuationResult = " + proficiencyRankingCompuationResult);
    }

}
