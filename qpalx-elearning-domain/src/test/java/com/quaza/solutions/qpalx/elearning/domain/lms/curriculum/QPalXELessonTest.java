package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by manyce400 on 12/2/17.
 */
public class QPalXELessonTest {


    private QPalXELesson qPalXELesson;

    @Before
    public void before() {
        qPalXELesson = QPalXELesson.builder()
                .elementOrder(1)
                .build();
    }

    @Test
    public void testIsQPalXELessonAbove() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .elementOrder(2)
                .build();
        
        boolean isLessonAbove = qPalXELesson.isAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertTrue(isLessonAbove);
    }

    @Test
    public void testIsQPalXELessonAbove2() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .elementOrder(4)
                .build();

        boolean isLessonAbove = qPalXELesson.isAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertTrue(isLessonAbove);
    }

    @Test
    public void testIsQPalXELessonAbove3() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .elementOrder(6)
                .build();

        boolean isLessonAbove = qPalXELesson.isAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertTrue(isLessonAbove);
    }

    @Test
    public void testIsQPalXELessonAboveFail() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .elementOrder(1)
                .build();

        boolean isLessonAbove = qPalXELesson.isAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertFalse(isLessonAbove);
    }


    @Test
    public void testIsQPalXELessonBelow() {
        qPalXELesson.setElementOrder(6);
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .elementOrder(5)
                .build();

        boolean isLessonBelow = qPalXELesson.isBelow(lessonBelow);
        System.out.println("isLessonBelow = " + isLessonBelow);
        Assert.assertTrue(isLessonBelow);
    }

    @Test
    public void testIsQPalXELessonBelow2() {
        qPalXELesson.setElementOrder(6);
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .elementOrder(4)
                .build();

        boolean isLessonBelow = qPalXELesson.isBelow(lessonBelow);
        System.out.println("isLessonBelow = " + isLessonBelow);
        Assert.assertTrue(isLessonBelow);
    }

    @Test
    public void testIsQPalXELessonBelow3() {
        qPalXELesson.setElementOrder(6);
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .elementOrder(3)
                .build();

        boolean isLessonBelow = qPalXELesson.isBelow(lessonBelow);
        System.out.println("isLessonBelow = " + isLessonBelow);
        Assert.assertTrue(isLessonBelow);
    }


}
