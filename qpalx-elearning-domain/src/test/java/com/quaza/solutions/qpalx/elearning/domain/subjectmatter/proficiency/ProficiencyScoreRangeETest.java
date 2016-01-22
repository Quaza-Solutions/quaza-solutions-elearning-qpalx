package com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

/**
 * Created by manyce400 on 1/7/16.
 */
public class ProficiencyScoreRangeETest {


    @Test
    public void testExtremelyDeficientPerformerRange() {
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(0.498d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(1d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(5.234d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(8d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(10d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(10.457d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());
}

    @Test
    public void testDeficientPerformerRange() {
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(11d);
        Assert.assertEquals(ProficiencyScoreRangeE.DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(11.9876d);
        Assert.assertEquals(ProficiencyScoreRangeE.DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(12d);
        Assert.assertEquals(ProficiencyScoreRangeE.DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(15d);
        Assert.assertEquals(ProficiencyScoreRangeE.DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(18.67d);
        Assert.assertEquals(ProficiencyScoreRangeE.DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(20d);
        Assert.assertEquals(ProficiencyScoreRangeE.DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(20.324d);
        Assert.assertEquals(ProficiencyScoreRangeE.DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());
    }

    @Test
    public void testFundamentalsLackingPerformerRange() {
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(20.5d);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(21d);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(25d);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(28.35d);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(29d);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(30.3d);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());
    }

    @Test
    public void testFailingPerformerRange() {
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(30.7d);
        Assert.assertEquals(ProficiencyScoreRangeE.FAILING_PERFORMER, proficiencyScoreRangeE.get());

    }

}
