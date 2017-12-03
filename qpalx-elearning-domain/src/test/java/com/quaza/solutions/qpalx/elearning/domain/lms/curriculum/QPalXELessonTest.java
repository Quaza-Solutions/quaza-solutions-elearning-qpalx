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
                .lessonOrder(1)
                .build();
    }

    @Test
    public void testIsQPalXELessonAbove() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .lessonOrder(2)
                .build();
        
        boolean isLessonAbove = qPalXELesson.isQPalXELessonAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertTrue(isLessonAbove);
    }

    @Test
    public void testIsQPalXELessonAbove2() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .lessonOrder(4)
                .build();

        boolean isLessonAbove = qPalXELesson.isQPalXELessonAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertTrue(isLessonAbove);
    }

    @Test
    public void testIsQPalXELessonAbove3() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .lessonOrder(6)
                .build();

        boolean isLessonAbove = qPalXELesson.isQPalXELessonAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertTrue(isLessonAbove);
    }

    @Test
    public void testIsQPalXELessonAboveFail() {
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .lessonOrder(1)
                .build();

        boolean isLessonAbove = qPalXELesson.isQPalXELessonAbove(lessonBelow);
        System.out.println("isLessonAbove = " + isLessonAbove);
        Assert.assertFalse(isLessonAbove);
    }


    @Test
    public void testIsQPalXELessonBelow() {
        qPalXELesson.setLessonOrder(6);
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .lessonOrder(5)
                .build();

        boolean isLessonBelow = qPalXELesson.isQPalXELessonBelow(lessonBelow);
        System.out.println("isLessonBelow = " + isLessonBelow);
        Assert.assertTrue(isLessonBelow);
    }

    @Test
    public void testIsQPalXELessonBelow2() {
        qPalXELesson.setLessonOrder(6);
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .lessonOrder(4)
                .build();

        boolean isLessonBelow = qPalXELesson.isQPalXELessonBelow(lessonBelow);
        System.out.println("isLessonBelow = " + isLessonBelow);
        Assert.assertTrue(isLessonBelow);
    }

    @Test
    public void testIsQPalXELessonBelow3() {
        qPalXELesson.setLessonOrder(6);
        QPalXELesson lessonBelow = QPalXELesson.builder()
                .lessonOrder(3)
                .build();

        boolean isLessonBelow = qPalXELesson.isQPalXELessonBelow(lessonBelow);
        System.out.println("isLessonBelow = " + isLessonBelow);
        Assert.assertTrue(isLessonBelow);
    }


}
