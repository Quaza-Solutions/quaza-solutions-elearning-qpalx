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

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(12d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(15d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(20d);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, proficiencyScoreRangeE.get());
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
    
    @Test
    public void testGetNextProficiencyScoreRangeUp() {
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.FAILING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.FAILING_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.POOR_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.POOR_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.BELOW_AVERAGE_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.BELOW_AVERAGE_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.AVERAGE_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.AVERAGE_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.ABOVE_AVERAGE_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.STRONG_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.STRONG_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.HIGH_FLYER_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.HIGH_FLYER_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertTrue(proficiencyScoreRangeE.isPresent());
        Assert.assertEquals(ProficiencyScoreRangeE.EXCEPTIONALLY_OUTSTANDING_PERFORMER, proficiencyScoreRangeE.get());

        proficiencyScoreRangeE = ProficiencyScoreRangeE.EXCEPTIONALLY_OUTSTANDING_PERFORMER.getNextProficiencyScoreRangeUp();
        Assert.assertFalse(proficiencyScoreRangeE.isPresent());
    }

    @Test
    public void testAverageProficiencyScoreRangeE() {
        ProficiencyScoreRangeE avg = ProficiencyScoreRangeE.averageProficiencyScoreRangeE(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER);
        Assert.assertEquals(ProficiencyScoreRangeE.EXTREMELY_DEFICIENT_PERFORMER, avg);

        avg = ProficiencyScoreRangeE.averageProficiencyScoreRangeE(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, ProficiencyScoreRangeE.FAILING_PERFORMER);
        Assert.assertEquals(ProficiencyScoreRangeE.FUNDAMENTALS_LACKING_PERFORMER, avg);

        avg = ProficiencyScoreRangeE.averageProficiencyScoreRangeE(ProficiencyScoreRangeE.FAILING_PERFORMER, ProficiencyScoreRangeE.POOR_PERFORMER);
        Assert.assertEquals(ProficiencyScoreRangeE.FAILING_PERFORMER, avg);
    }

}
