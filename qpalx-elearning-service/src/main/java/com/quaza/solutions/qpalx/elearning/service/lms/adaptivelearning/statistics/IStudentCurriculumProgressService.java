package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * @author manyce400
 */
public interface IStudentCurriculumProgressService {


    public StudentOverallProgressStatistics getStudentOverallProgressStatisticsInCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum);

}
