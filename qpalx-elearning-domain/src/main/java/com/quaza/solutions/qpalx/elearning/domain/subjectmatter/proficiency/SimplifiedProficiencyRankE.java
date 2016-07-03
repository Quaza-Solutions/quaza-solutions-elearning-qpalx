package com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency;

import org.apache.commons.lang3.Range;

/**
 * @author manyce400
 */
public enum  SimplifiedProficiencyRankE {



    Beginner(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE),

    Intermediate(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SEVEN),

    Advanced(ProficiencyRankingScaleE.EIGHT, ProficiencyRankingScaleE.TEN)
    ;

    private final Range<ProficiencyRankingScaleE> proficiencyRankingScaleRange;

    SimplifiedProficiencyRankE(ProficiencyRankingScaleE  proficiencyRankingScaleFloor, ProficiencyRankingScaleE  proficiencyRankingScaleCeiling) {
        this.proficiencyRankingScaleRange = Range.between(proficiencyRankingScaleFloor, proficiencyRankingScaleCeiling);
    }

    public Range<ProficiencyRankingScaleE> getProficiencyRankingScaleRange() {
        return proficiencyRankingScaleRange;
    }
}
