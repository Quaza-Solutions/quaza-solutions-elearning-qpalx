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

}
