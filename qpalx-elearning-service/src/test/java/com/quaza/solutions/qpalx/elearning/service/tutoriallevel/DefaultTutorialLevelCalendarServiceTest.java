package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseActivityRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.ITutorialLevelCalendarRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author manyce400
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultTutorialLevelCalendarServiceTest {


    @Mock
    private ITutorialLevelCalendarRepository iTutorialLevelCalendarRepository;

    @Mock
    private IELearningCourseActivityRepository ieLearningCourseActivityRepository;

    @Spy
    @InjectMocks
    private DefaultTutorialLevelCalendarService defaultTutorialLevelCalendarService;


    @Test
    public void testFindCurrentCalendarByTutorialLevel() {
        DateTime dateTime = new DateTime();
        String monthOfYear = dateTime.monthOfYear().getAsText();
        int mInt = dateTime.monthOfYear().get();

        System.out.println("mInt = " + mInt);
        System.out.println("monthOfYear = " + monthOfYear);
    }


}
