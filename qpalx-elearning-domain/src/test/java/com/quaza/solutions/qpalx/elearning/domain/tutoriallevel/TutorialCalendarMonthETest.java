package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author manyce400 
 */
public class TutorialCalendarMonthETest {
    
    
    
    @Test
    public void testIsMonthInTutorialCalendarMonthRangeValid() {
        // Februaury should betewen Jan and March
        boolean isBetweenRange = TutorialCalendarMonthE.isMonthInTutorialCalendarMonthRange(TutorialCalendarMonthE.February, TutorialCalendarMonthE.January, TutorialCalendarMonthE.March);
        System.out.println("isBetweenRange = " + isBetweenRange);
        Assert.assertTrue(isBetweenRange);
    }

    @Test
    public void testIsMonthInTutorialCalendarMonthRangeInValid() {
        // Februaury should betewen Jan and March
        boolean isBetweenRange = TutorialCalendarMonthE.isMonthInTutorialCalendarMonthRange(TutorialCalendarMonthE.February, TutorialCalendarMonthE.September, TutorialCalendarMonthE.December);
        System.out.println("isBetweenRange = " + isBetweenRange);
        Assert.assertFalse(isBetweenRange);
    }
}
