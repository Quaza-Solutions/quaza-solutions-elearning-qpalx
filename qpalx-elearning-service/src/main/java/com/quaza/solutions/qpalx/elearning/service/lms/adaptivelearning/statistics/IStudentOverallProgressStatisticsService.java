package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics.StudentOverallProgressStatistics;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IStudentOverallProgressStatisticsService {


    public List<StudentOverallProgressStatistics> getStudentOverallProgressStatistics(QPalXUser qPalXUser, CurriculumType curriculumType);

}
