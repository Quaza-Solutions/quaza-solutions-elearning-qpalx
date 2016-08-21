package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IELearningCourseVO;

import java.util.List;

/**
 * @author manyce400
 */
public interface IELearningCourseService {


    public void saveELearningCourse(ELearningCourse eLearningCourse);

    public void deleteELearningCourse(Long eLearningCourseID);

    public void createELearningCourse(IELearningCourseVO eLearningCourseWebVO);

    public ELearningCourse findByCourseID(Long courseID);

    public ELearningCourse findByCourseName(String courseName);

    public List<ELearningCourse> findByELearningCurriculum(ELearningCurriculum eLearningCurriculum);

    public ELearningCourse findByCourseNameAndELearningCurriculum(String courseName, ELearningCurriculum eLearningCurriculum);

}
