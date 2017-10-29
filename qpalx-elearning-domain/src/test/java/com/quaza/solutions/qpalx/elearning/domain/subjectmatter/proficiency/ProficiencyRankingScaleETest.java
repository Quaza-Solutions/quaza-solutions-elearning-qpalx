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
    public void testGetAllProficiencyRankingsInScope() {
        Set<ProficiencyRankingScaleE> results = ProficiencyRankingScaleE.getAllProficiencyRankingsInScope(1, 3);
        Assert.assertTrue(results.size() == 3);
        Assert.assertTrue(results.contains(ProficiencyRankingScaleE.ONE));
        Assert.assertTrue(results.contains(ProficiencyRankingScaleE.TWO));
        Assert.assertTrue(results.contains(ProficiencyRankingScaleE.THREE));
        System.out.println("results = " + results);

        results = ProficiencyRankingScaleE.getAllProficiencyRankingsInScope(9, 10);
        Assert.assertTrue(results.size() == 2);
        Assert.assertTrue(results.contains(ProficiencyRankingScaleE.NINE));
        Assert.assertTrue(results.contains(ProficiencyRankingScaleE.TEN));
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

    //@Test
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

    @Test
    public void testCanAccessProficiencyRangeONEtoTHREERange() {
        boolean canAccessProficiencyRange = ProficiencyRankingScaleE.ONE.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TWO.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.THREE.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FOUR.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FIVE.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SIX.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SEVEN.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.EIGHT.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.NINE.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TEN.canAccessProficiencyRange(ProficiencyRankingScaleE.ONE.getProficiencyRanking(), ProficiencyRankingScaleE.THREE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);
    }

    @Test
    public void testCanAccessProficiencyRangeFOURtoSIXRange() {
        boolean canAccessProficiencyRange = ProficiencyRankingScaleE.ONE.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TWO.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.THREE.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FOUR.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FIVE.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SIX.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SEVEN.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.EIGHT.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.NINE.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TEN.canAccessProficiencyRange(ProficiencyRankingScaleE.FOUR.getProficiencyRanking(), ProficiencyRankingScaleE.SIX.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);
    }

    @Test
    public void testCanAccessProficiencyRangeSEVENtoNINERange() {
        boolean canAccessProficiencyRange = ProficiencyRankingScaleE.ONE.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TWO.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.THREE.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FOUR.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FIVE.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SIX.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SEVEN.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.EIGHT.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.NINE.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TEN.canAccessProficiencyRange(ProficiencyRankingScaleE.SEVEN.getProficiencyRanking(), ProficiencyRankingScaleE.NINE.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);
    }

    @Test
    public void testCanAccessProficiencyRangeNINEtoTENRange() {
        boolean canAccessProficiencyRange = ProficiencyRankingScaleE.ONE.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TWO.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.THREE.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FOUR.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.FIVE.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SIX.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.SEVEN.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.EIGHT.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertFalse(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.NINE.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);

        canAccessProficiencyRange = ProficiencyRankingScaleE.TEN.canAccessProficiencyRange(ProficiencyRankingScaleE.NINE.getProficiencyRanking(), ProficiencyRankingScaleE.TEN.getProficiencyRanking());
        Assert.assertTrue(canAccessProficiencyRange);
        System.out.println("canAccessProficiencyRange = " + canAccessProficiencyRange);
    }

}
