package com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency;

import org.springframework.util.Assert;

import java.util.Optional;

/**
 * Establishes a proficiency rating scale of 1 - 10 to be applied to any Subject Matter Proficiency level.
 *
 * @author manyce400
 */
public enum ProficiencyRankingScaleE {


    TEN(10, ProficiencyScoreRangeE.EXCEPTIONALLY_OUTSTANDING_PERFORMER),

    NINE(9, ProficiencyScoreRangeE.HIGH_FLYER_PERFORMER),

    EIGHT(8, ProficiencyScoreRangeE.STRONG_PERFORMER),

    SEVEN(7, ProficiencyScoreRangeE.AVERAGE_PERFORMER),

    SIX(6, ProficiencyScoreRangeE.BELOW_AVERAGE_PERFORMER),

    FIVE(5, ProficiencyScoreRangeE.POOR_PERFORMER),

    FOUR(4, ProficiencyScoreRangeE.FAILING_PERFORMER),

    THREE(3, ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER),

    TWO(2, ProficiencyScoreRangeE.DEFICIENT_PERFORMER),

    ONE(1, ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER)
    ;


    private final int proficiencyRanking;

    private final ProficiencyScoreRangeE proficiencyScoreRangeE;

    ProficiencyRankingScaleE(int proficiencyRanking, ProficiencyScoreRangeE proficiencyScoreRangeE) {
        this.proficiencyRanking = proficiencyRanking;
        this.proficiencyScoreRangeE = proficiencyScoreRangeE;
    }

    public int getProficiencyRanking() {
        return proficiencyRanking;
    }

    public ProficiencyScoreRangeE getProficiencyScoreRangeE() {
        return proficiencyScoreRangeE;
    }

    public static ProficiencyRankingScaleE getProficiencyRankingScaleEByRanking(int ranking) {
        for(ProficiencyRankingScaleE proficiencyRankingScaleE : values()) {
            if(proficiencyRankingScaleE.getProficiencyRanking() == ranking) {
                return proficiencyRankingScaleE;
            }
        }

        return null;
    }

    /**
     * Gets and returns the ProficiencyRankingScaleE one level below the #proficiencyRankingScaleE passed in as argument.
     *
     * Will return #Optional.empty() if there is no ProficiencyRankingScaleE immediately below this.
     *
     * @param proficiencyRankingScaleE
     * @return ProficiencyRankingScaleE
     */
    public static Optional<ProficiencyRankingScaleE> getProficiencyRankingScaleEBelow(ProficiencyRankingScaleE proficiencyRankingScaleE) {
        Assert.notNull(proficiencyRankingScaleE, "proficiencyRankingScaleE cannot be null");
        int proficiencyRankingBelow = proficiencyRankingScaleE.getProficiencyRanking() - 1;

        switch (proficiencyRankingBelow) {
            case 0:
                return Optional.empty();
            default:
                ProficiencyRankingScaleE proficiencyRankingScaleEBelow = ProficiencyRankingScaleE.getProficiencyRankingScaleEByRanking(proficiencyRankingBelow);
                return Optional.of(proficiencyRankingScaleEBelow);
        }
    }

    public static Optional<ProficiencyRankingScaleE> getProficiencyRankingScaleForRange(ProficiencyScoreRangeE proficiencyScoreRangeArg) {
        Assert.notNull(proficiencyScoreRangeArg, "proficiencyScoreRangeArg cannot be null");

        for(ProficiencyRankingScaleE proficiencyRankingScaleE : values()) {
            ProficiencyScoreRangeE proficiencyScoreRangeE = proficiencyRankingScaleE.getProficiencyScoreRangeE();
            if(proficiencyScoreRangeE == proficiencyScoreRangeArg) {
                return Optional.of(proficiencyRankingScaleE);
            }
        }

        return Optional.empty();
    }
}
