package com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

/**
 * Test of functionality defined in ProficiencyRankingScaleE
 *
 * @author manyce400
 */
public class ProficiencyRankingScaleETest {



    @Test
    public void testGetProficiencyRankingScaleEBelowTEN() {
        Optional<ProficiencyRankingScaleE> proficiencyRankingScaleEBelow = ProficiencyRankingScaleE.getProficiencyRankingScaleEBelow(ProficiencyRankingScaleE.TEN);
        System.out.println("proficiencyRankingScaleEBelow = " + proficiencyRankingScaleEBelow);
        Assert.assertTrue("proficiencyRankingScaleEBelow cannot be empty", proficiencyRankingScaleEBelow.isPresent());
        Assert.assertEquals(ProficiencyRankingScaleE.NINE, proficiencyRankingScaleEBelow.get());
    }

    @Test
    public void testGetProficiencyRankingScaleEBelowNINE() {
        Optional<ProficiencyRankingScaleE> proficiencyRankingScaleEBelow = ProficiencyRankingScaleE.getProficiencyRankingScaleEBelow(ProficiencyRankingScaleE.NINE);
        System.out.println("proficiencyRankingScaleEBelow = " + proficiencyRankingScaleEBelow);
        Assert.assertTrue("proficiencyRankingScaleEBelow cannot be empty", proficiencyRankingScaleEBelow.isPresent());
        Assert.assertEquals(ProficiencyRankingScaleE.EIGHT, proficiencyRankingScaleEBelow.get());
    }

    @Test
    public void testGetProficiencyRankingScaleEBelowSEVEN() {
        Optional<ProficiencyRankingScaleE> proficiencyRankingScaleEBelow = ProficiencyRankingScaleE.getProficiencyRankingScaleEBelow(ProficiencyRankingScaleE.SEVEN);
        System.out.println("proficiencyRankingScaleEBelow = " + proficiencyRankingScaleEBelow);
        Assert.assertTrue("proficiencyRankingScaleEBelow cannot be empty", proficiencyRankingScaleEBelow.isPresent());
        Assert.assertEquals(ProficiencyRankingScaleE.SIX, proficiencyRankingScaleEBelow.get());
    }

    @Test
    public void testGetProficiencyRankingScaleEBelowTWO() {
        Optional<ProficiencyRankingScaleE> proficiencyRankingScaleEBelow = ProficiencyRankingScaleE.getProficiencyRankingScaleEBelow(ProficiencyRankingScaleE.TWO);
        System.out.println("proficiencyRankingScaleEBelow = " + proficiencyRankingScaleEBelow);
        Assert.assertTrue("proficiencyRankingScaleEBelow cannot be empty", proficiencyRankingScaleEBelow.isPresent());
        Assert.assertEquals(ProficiencyRankingScaleE.ONE, proficiencyRankingScaleEBelow.get());
    }

    @Test
    public void testGetProficiencyRankingScaleEBelowONE() {
        Optional<ProficiencyRankingScaleE> proficiencyRankingScaleEBelow = ProficiencyRankingScaleE.getProficiencyRankingScaleEBelow(ProficiencyRankingScaleE.ONE);
        System.out.println("proficiencyRankingScaleEBelow = " + proficiencyRankingScaleEBelow);
        Assert.assertFalse("proficiencyRankingScaleEBelow should be empty", proficiencyRankingScaleEBelow.isPresent());
    }

    @Test
    public void testgetAllProficiencyRankingsInScope() {
        Set<ProficiencyRankingScaleE> results = ProficiencyRankingScaleE.getAllProficiencyRankingsInScope(1, 3);
        System.out.println("results = " + results);
    }

    @Test
    public void testIsProficiencyRankingBetweenFloorAndCeiling1() {
        boolean isBetween = ProficiencyRankingScaleE.ONE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertTrue(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.TWO.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertTrue(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.THREE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertTrue(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.FOUR.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertFalse(isBetween);
        System.out.println("Four isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.FIVE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.SIX.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.SEVEN.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.EIGHT.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.NINE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.TEN.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.ONE, ProficiencyRankingScaleE.THREE);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);
    }

    @Test
    public void testIsProficiencyRankingBetweenFloorAndCeiling2() {
        boolean isBetween = ProficiencyRankingScaleE.ONE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.TWO.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.THREE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.FOUR.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertTrue(isBetween);
        System.out.println("Four isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.FIVE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertTrue(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.SIX.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertTrue(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.SEVEN.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.EIGHT.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.NINE.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);

        isBetween = ProficiencyRankingScaleE.TEN.isProficiencyRankingBetweenFloorAndCeiling(ProficiencyRankingScaleE.FOUR, ProficiencyRankingScaleE.SIX);
        Assert.assertFalse(isBetween);
        System.out.println("isBetween = " + isBetween);
    }

}
