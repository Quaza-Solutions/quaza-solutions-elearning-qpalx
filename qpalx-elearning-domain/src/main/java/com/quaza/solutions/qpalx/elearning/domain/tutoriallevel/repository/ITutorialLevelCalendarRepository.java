package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository;

import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface ITutorialLevelCalendarRepository extends CrudRepository<TutorialLevelCalendar, Long> {



    @Query("Select tutorialLevelCalendar From TutorialLevelCalendar tutorialLevelCalendar Where tutorialLevelCalendar.studentTutorialLevel =?1 Order By tutorialLevelCalendar.calendarItemEndMonth ASC ")
    public List<TutorialLevelCalendar> findCalendarForStudentTutorialLevel(StudentTutorialLevel studentTutorialLevel);


}
