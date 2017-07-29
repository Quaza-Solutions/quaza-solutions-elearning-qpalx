package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository;

import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface ITutorialGradeRepository  extends CrudRepository<StudentTutorialGrade, Long> {


    @Query("Select studentTutorialGrade From StudentTutorialGrade studentTutorialGrade " +
            "JOIN FETCH studentTutorialGrade.studentTutorialLevel " +
            "Where studentTutorialGrade.studentTutorialLevel =?1"
    )
    public List<StudentTutorialGrade> findAllStudentTutorialGradeByTutorialLevel(StudentTutorialLevel studentTutorialLevel);

}
