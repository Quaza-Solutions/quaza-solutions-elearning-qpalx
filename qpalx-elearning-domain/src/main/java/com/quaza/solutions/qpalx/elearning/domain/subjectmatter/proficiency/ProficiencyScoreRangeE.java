package com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency;

import org.apache.commons.lang3.Range;
import org.apache.commons.math3.util.Precision;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Proficiency scores ranges from 0 all the way to 100.00.
 *
 * All double scores should always be rounded up to always fall within a specific range.
 */
public enum ProficiencyScoreRangeE {


    EXCEPTIONALLY_OUTSTANDING_PERFORMER(Range.between(98d, 100d)),

    HIGH_FLYER_PERFORMER(Range.between(92d, 98d)),

    STRONG_PERFORMER(Range.between(81d, 91d)),

    ABOVE_AVERAGE_PERFORMER(Range.between(71d, 80d)),

    AVERAGE_PERFORMER(Range.between(61d, 70d)),

    BELOW_AVERAGE_PERFORMER(Range.between(51d, 60d)),

    POOR_PERFORMER(Range.between(41d, 50d)),

    FAILING_PERFORMER(Range.between(31d, 40d)),

    FUNDAMENTALS_LACKING_PERFORMER(Range.between(21d, 30d)),

    EXTREMELY_DEFICIENT_PERFORMER(Range.between(0d, 20d))
    ;


    private final Range<Double> scoreRange;

    ProficiencyScoreRangeE(Range<Double> scoreRange) {
        this.scoreRange = scoreRange;
    }

    public Range<Double> getScoreRange() {
        return scoreRange;
    }

    public Optional<ProficiencyScoreRangeE> getNextProficiencyScoreRangeUp() {
        Double currentMaxScore = scoreRange.getMaximum();
        double minScoreInHigherRange = currentMaxScore.doubleValue() + 1d;
        return getProficiencyScoreRangeForScore(minScoreInHigherRange);
    }

    /**
     * Given a QPalXUser Student score on any given Quiz/Test, figure out their proficiency performance profile based
     * off where they would fall on a score range.
     *
     * @param score Student score is expressed in terms of percentile.
     * @return
     */
    public static Optional<ProficiencyScoreRangeE> getProficiencyScoreRangeForScore(final Double score) {
        Assert.notNull(score, "score cannot be null");

        // Round score half up to always fall between a range
        double roundedScore = Precision.round(score.doubleValue(), 0, BigDecimal.ROUND_HALF_UP);

        for(ProficiencyScoreRangeE proficiencyScoreRangeE : values()) {
            Range scoreRange = proficiencyScoreRangeE.getScoreRange();
            if(scoreRange.contains(roundedScore)) {
                return Optional.of(proficiencyScoreRangeE);
            }
        }

        return Optional.empty();
    }
}
