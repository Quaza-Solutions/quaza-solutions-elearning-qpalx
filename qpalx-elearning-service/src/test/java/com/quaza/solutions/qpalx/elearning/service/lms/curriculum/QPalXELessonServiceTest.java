package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXELessonRepository;
import com.quaza.solutions.qpalx.elearning.service.institutions.IQPalXEducationalInstitutionService;
import com.quaza.solutions.qpalx.elearning.service.tutoriallevel.ITutorialLevelCalendarService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class QPalXELessonServiceTest {



    private QPalXELesson lesson1;

    private QPalXELesson lesson2;

    private QPalXELesson lesson3;

    private QPalXELesson lesson4;

    private ELearningCourse eLearningCourse;

    @Mock
    private IQPalXELessonRepository iqPalXELessonRepository;

    @Mock
    private IELearningCourseService ieLearningCourseService;

    @Mock
    private ITutorialLevelCalendarService iTutorialLevelCalendarService;

    @Mock
    private IQPalXEducationalInstitutionService iqPalXEducationalInstitutionService;

    @InjectMocks
    private QPalXELessonService qPalXELessonService;


    @Before
    public void before() {
        eLearningCourse = ELearningCourse.builder()
                .courseName("Test Course")
                .build();

        lesson1 = QPalXELesson.builder()
                .lessonOrder(1)
                .eLearningCourse(eLearningCourse)
                .build();

        lesson2 = QPalXELesson.builder()
                .lessonOrder(2)
                .eLearningCourse(eLearningCourse)
                .build();

        lesson3 = QPalXELesson.builder()
                .lessonOrder(3)
                .eLearningCourse(eLearningCourse)
                .build();

        lesson4 = QPalXELesson.builder()
                .lessonOrder(4)
                .eLearningCourse(eLearningCourse)
                .build();

        eLearningCourse.addQPalXELesson(lesson1);
        eLearningCourse.addQPalXELesson(lesson2);
        eLearningCourse.addQPalXELesson(lesson3);
        eLearningCourse.addQPalXELesson(lesson4);
    }

    @Test
    public void testMoveQPalXELessonUp1() {
        qPalXELessonService.moveQPalXELessonUp(lesson1);

        // In this case we cant move 1 up so expectation is noting should have changed
        Assert.assertTrue(lesson1.getLessonOrder() == 1);
        Assert.assertTrue(lesson2.getLessonOrder() == 2);
        Assert.assertTrue(lesson3.getLessonOrder() == 3);
        Assert.assertTrue(lesson4.getLessonOrder() == 4);
    }

    @Test
    public void testMoveQPalXELessonUp2() {
        qPalXELessonService.moveQPalXELessonUp(lesson2);

        // Verify that lesson2 and lesson1 now have their orders switched.
        Assert.assertTrue(lesson2.getLessonOrder() == 1);
        Assert.assertTrue(lesson1.getLessonOrder() == 2);
        Assert.assertTrue(lesson3.getLessonOrder() == 3);
        Assert.assertTrue(lesson4.getLessonOrder() == 4);
    }

    @Test
    public void testMoveQPalXELessonUp3() {
        qPalXELessonService.moveQPalXELessonUp(lesson3);

        // Verify that lesson2 and lesson1 now have their orders switched.
        Assert.assertTrue(lesson3.getLessonOrder() == 2);
        Assert.assertTrue(lesson2.getLessonOrder() == 3);
        Assert.assertTrue(lesson1.getLessonOrder() == 1);
        Assert.assertTrue(lesson4.getLessonOrder() == 4);
    }

    @Test
    public void testMoveQPalXELessonUp4() {
        qPalXELessonService.moveQPalXELessonUp(lesson4);

        // Verify that lesson2 and lesson1 now have their orders switched.
        Assert.assertTrue(lesson4.getLessonOrder() == 3);
        Assert.assertTrue(lesson3.getLessonOrder() == 4);
        Assert.assertTrue(lesson1.getLessonOrder() == 1);
        Assert.assertTrue(lesson2.getLessonOrder() == 2);
    }

    @Test
    public void moveQPalXELessonDown1() {
        qPalXELessonService.moveQPalXELessonDown(lesson1);
        Assert.assertTrue(lesson1.getLessonOrder() == 2);
        Assert.assertTrue(lesson2.getLessonOrder() == 1);
        Assert.assertTrue(lesson3.getLessonOrder() == 3);
        Assert.assertTrue(lesson4.getLessonOrder() == 4);
    }

    @Test
    public void moveQPalXELessonDown2() {
        qPalXELessonService.moveQPalXELessonDown(lesson2);
        Assert.assertTrue(lesson1.getLessonOrder() == 1);
        Assert.assertTrue(lesson2.getLessonOrder() == 3);
        Assert.assertTrue(lesson3.getLessonOrder() == 2);
        Assert.assertTrue(lesson4.getLessonOrder() == 4);
    }

    @Test
    public void moveQPalXELessonDown3() {
        qPalXELessonService.moveQPalXELessonDown(lesson3);

        // In this case we cant move 1 up so expectation is noting should have changed
        Assert.assertTrue(lesson1.getLessonOrder() == 1);
        Assert.assertTrue(lesson2.getLessonOrder() == 2);
        Assert.assertTrue(lesson3.getLessonOrder() == 4);
        Assert.assertTrue(lesson4.getLessonOrder() == 3);
    }
    @Test
    public void moveQPalXELessonDown4() {
        qPalXELessonService.moveQPalXELessonDown(lesson4);

        // In this case we cant move down  so expectation is noting should have changed
        Assert.assertTrue(lesson1.getLessonOrder() == 1);
        Assert.assertTrue(lesson2.getLessonOrder() == 2);
        Assert.assertTrue(lesson3.getLessonOrder() == 3);
        Assert.assertTrue(lesson4.getLessonOrder() == 4);
    }

}
