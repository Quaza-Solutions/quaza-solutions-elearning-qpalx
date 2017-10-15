package com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency;

import org.springframework.util.Assert;

import java.util.*;

/**
 * Establishes a proficiency rating scale of 1 - 10 to be applied to any Subject Matter Proficiency level.
 *
 * @author manyce400
 */
public enum ProficiencyRankingScaleE {


    TEN(10, ProficiencyScoreRangeE.EXCEPTIONALLY_OUTSTANDING_PERFORMER),

    NINE(9, ProficiencyScoreRangeE.HIGH_FLYER_PERFORMER),

    EIGHT(8, ProficiencyScoreRangeE.STRONG_PERFORMER),

    SEVEN(7, ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER),

    SIX(6, ProficiencyScoreRangeE.AVERAGE_PERFORMER),

    FIVE(5, ProficiencyScoreRangeE.BELOW_AVERAGE_PERFORMER),

    FOUR(4, ProficiencyScoreRangeE.POOR_PERFORMER),

    THREE(3, ProficiencyScoreRangeE.FAILING_PERFORMER),

    TWO(2, ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER),

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

    /**
     * Gets and returns the ProficiencyRankingScaleE one level above the #proficiencyRankingScaleE passed in as argument.
     *
     * Will return #Optional.empty() if there is no ProficiencyRankingScaleE immediately below this.
     *
     * @param proficiencyRankingScaleE
     * @return ProficiencyRankingScaleE
     */
    public static Optional<ProficiencyRankingScaleE> getProficiencyRankingScaleEAbove(ProficiencyRankingScaleE proficiencyRankingScaleE) {
        Assert.notNull(proficiencyRankingScaleE, "proficiencyRankingScaleE cannot be null");
        int proficiencyRankingAbove = proficiencyRankingScaleE.getProficiencyRanking() + 1;

        switch (proficiencyRankingAbove) {
            case 10:
                return Optional.empty();
            default:
                ProficiencyRankingScaleE proficiencyRankingScaleEBelow = ProficiencyRankingScaleE.getProficiencyRankingScaleEByRanking(proficiencyRankingAbove);
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

    public static Set<ProficiencyRankingScaleE> getAllProficiencyRankingsInScope(int proficiencyRankingStart, int proficiencyRankingEnd) {
        Assert.isTrue(proficiencyRankingStart < proficiencyRankingEnd, "Start ranking must be less than end");
        Set<ProficiencyRankingScaleE> results = new LinkedHashSet<>();

        ProficiencyRankingScaleE current = ProficiencyRankingScaleE.getProficiencyRankingScaleEByRanking(proficiencyRankingStart);
        ProficiencyRankingScaleE proficiencyRankingScaleEEnd = ProficiencyRankingScaleE.getProficiencyRankingScaleEByRanking(proficiencyRankingEnd);
        results.add(current);

        while (ProficiencyRankingScaleE.getProficiencyRankingScaleEAbove(current).isPresent() && ProficiencyRankingScaleE.getProficiencyRankingScaleEAbove(current).get().getProficiencyRanking() <= proficiencyRankingEnd) {
            ProficiencyRankingScaleE higher = ProficiencyRankingScaleE.getProficiencyRankingScaleEAbove(current).get();
            current = higher;
            results.add(higher);
        }

        return results;
    }


    public static final class ProficiencyComparator implements Comparator<ProficiencyRankingScaleE> {
        @Override
        public int compare(ProficiencyRankingScaleE o1, ProficiencyRankingScaleE o2) {
            Integer i1 = o1.getProficiencyRanking();
            Integer i2 = o2.getProficiencyRanking();
            return i1.compareTo(i2);
        }
    }

    public static List<ProficiencyRankingScaleE> lowestToHighest() {
        List<ProficiencyRankingScaleE> proficiencyRankingScaleEs = new ArrayList<>(Arrays.asList(values()));
        proficiencyRankingScaleEs.sort((ProficiencyRankingScaleE o1, ProficiencyRankingScaleE o2) -> o1.getProficiencyRanking() - o2.getProficiencyRanking());
        return proficiencyRankingScaleEs;
    }
}
