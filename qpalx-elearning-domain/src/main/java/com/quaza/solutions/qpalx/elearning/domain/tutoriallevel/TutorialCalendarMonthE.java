package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel;

import org.apache.commons.lang3.Range;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
public enum TutorialCalendarMonthE {


    January(1),

    February(2),

    March(3),

    April(4),

    May(5),

    June(6),

    July(7),

    August(8),

    September(9),

    October(10),

    November(11),

    December(12);


    private final int monthNumber;

    TutorialCalendarMonthE(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public static TutorialCalendarMonthE findByMonthString(String month) {
        for(TutorialCalendarMonthE tutorialCalendarMonthE : values()) {
            if(tutorialCalendarMonthE.toString().equals(month)) {
                return tutorialCalendarMonthE;
            }
         }

         return null;
    }

    public static TutorialCalendarMonthE findByMonthInt(int month) {
        for(TutorialCalendarMonthE tutorialCalendarMonthE : values()) {
            if(tutorialCalendarMonthE.getMonthNumber() == month) {
                return tutorialCalendarMonthE;
            }
        }

        return null;
    }

    public static boolean isMonthInTutorialCalendarMonthRange(TutorialCalendarMonthE inputMonthE, TutorialCalendarMonthE startMonthE, TutorialCalendarMonthE endMonthE) {
        Assert.notNull(inputMonthE, "inputMonthE cannot be null");
        Assert.notNull(startMonthE, "startMonthE cannot be null");
        Assert.notNull(endMonthE, "endMonthE cannot be null");

        // Create range and check to see if input month is between this range
        Range<Integer> calendarMonthRange =  Range.between(startMonthE.getMonthNumber(), endMonthE.getMonthNumber());
        return calendarMonthRange.contains(inputMonthE.getMonthNumber());
    }
}
