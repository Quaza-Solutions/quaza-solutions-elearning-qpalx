package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * Interface for service that provides Curriclum and courses information for QPalX Student users
 *
 * @author manyce400
 */
public interface IQPalXStudentCurriculumService {


    /**
     * Find all the ELearningCurriculum that the student user passed in as argument is eligible for on the platform.
     * This should generally be based on the students Tutorial Level which is connected to the Country and region that the
     * Student profile has been registered in.
     *
     * @param student
     * @return List<ELearningCurriculum>
     */
    public List<ELearningCurriculum> findStudentCurriculum(QPalXUser student);


    /**
     * For given QPalxUser Student, find and return all available ELearning courses for a given curriculum.
     *
     * This should be based off the student's
     *
     * <ul>
     *     <li>Geographical Region</li>
     *     <li>Tutorial Level, High School etc</li>
     *     <li>Current Proficiency ranking in that curriculum</li>
     * </ul>
     *
     * @param student
     * @param eLearningCurriculum
     * @return List<ELearningCourse>
     */
    //public List<ELearningCourse> findAllCoursesByCurriculum(QPalXUser student, ELearningCurriculum eLearningCurriculum);


}
