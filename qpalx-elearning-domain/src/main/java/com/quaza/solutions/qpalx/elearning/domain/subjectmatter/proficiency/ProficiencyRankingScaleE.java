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

    public boolean isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE floor, ProficiencyRankingScaleE ceiling) {
        Assert.notNull(floor, "floor cannot be null");
        Assert.notNull(ceiling, "ceiling cannot be null");
        Assert.isTrue(floor.getProficiencyRanking() <= ceiling.getProficiencyRanking(), "Floor ranking must be less than or equal to Ceiling");

        int floorRanking = floor.getProficiencyRanking();
        int ceilingRanking = ceiling.getProficiencyRanking();
        return isProficiencyRankingBetweenFloorAndCeiling(floorRanking, ceilingRanking);
    }

    public boolean isProficiencyRankingBetweenFloorAndCeiling(int floorRanking, int ceilingRanking) {
        boolean isBetween = floorRanking <= this.proficiencyRanking && this.proficiencyRanking <= ceilingRanking;
        return isBetween;
    }

    public boolean isAboveProficiencyRanking(ProficiencyRankingScaleE proficiencyRankingScaleE) {
        Assert.notNull(proficiencyRankingScaleE, "proficiencyRankingScaleE cannot be null");
        int otherProficiencyRanking = proficiencyRankingScaleE.getProficiencyRanking();
        return isAboveProficiencyRanking(otherProficiencyRanking);
    }

    public boolean isAboveProficiencyRanking(int otherProficiencyRanking) {
        return this.proficiencyRanking > otherProficiencyRanking;
    }

    public boolean isBelowProficiencyRanking(ProficiencyRankingScaleE proficiencyRankingScaleE) {
        Assert.notNull(proficiencyRankingScaleE, "proficiencyRankingScaleE cannot be null");
        int otherProficiencyRanking = proficiencyRankingScaleE.getProficiencyRanking();
        return isBelowProficiencyRanking(otherProficiencyRanking);
    }

    public boolean isBelowProficiencyRanking(int otherProficiencyRanking) {
        return this.proficiencyRanking < otherProficiencyRanking;
    }

    public boolean canAccessProficiencyRange(int floorRanking, int ceilingRanking) {
        Assert.isTrue(ceilingRanking > floorRanking, "ceilingRanking has to be greater than floorRanking");

        // Base case is this proficiency ranking between floor and ceiling?  IF it is then definitely good to access
        boolean isBetween = isProficiencyRankingBetweenFloorAndCeiling(floorRanking, ceilingRanking);
        if(isBetween) {
            return true;
        }

        // Check to see if this proficiency ranking is less than the floor.  IF less than the floor this proficiency ranking is not compatible with this range
        boolean isLessThanFloor = isBelowProficiencyRanking(floorRanking);
        if(isLessThanFloor) {
            return false;
        }

        boolean isGreaterThanCeiling = isAboveProficiencyRanking(ceilingRanking);
        if(isGreaterThanCeiling) {
            return true;
        }

        return false;
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

        // Handle base case, IF we are at NINE, return TEN and break recursion
        if(proficiencyRankingScaleE == ProficiencyRankingScaleE.NINE) {
            return Optional.of(ProficiencyRankingScaleE.TEN);
        } else if(proficiencyRankingScaleE == ProficiencyRankingScaleE.TEN) {
            return Optional.empty();
        }

        int proficiencyRankingAbove = proficiencyRankingScaleE.getProficiencyRanking() + 1;

        switch (proficiencyRankingAbove) {
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
        Assert.isTrue(proficiencyRankingStart <= proficiencyRankingEnd, "Start ranking must be less than or equal to end");
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

    public static ProficiencyRankingScaleE averageProficiencyRankingScale(ProficiencyRankingScaleE proficiencyRankingScaleE1, ProficiencyRankingScaleE proficiencyRankingScaleE2) {
        Assert.notNull(proficiencyRankingScaleE1, "proficiencyRankingScaleE1 cannot be null");
        Assert.notNull(proficiencyRankingScaleE2, "proficiencyRankingScaleE2 cannot be null");

        ProficiencyScoreRangeE scoreRangeE1 = proficiencyRankingScaleE1.getProficiencyScoreRangeE();
        ProficiencyScoreRangeE scoreRangeE2 = proficiencyRankingScaleE2.getProficiencyScoreRangeE();

        // Average the score ranges
        ProficiencyScoreRangeE avgProficiencyScoreRangeE = ProficiencyScoreRangeE.averageProficiencyScoreRangeE(scoreRangeE1, scoreRangeE2);
        Optional<ProficiencyRankingScaleE> avgProficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(avgProficiencyScoreRangeE);
        return avgProficiencyRankingScaleE.get();
    }

}
