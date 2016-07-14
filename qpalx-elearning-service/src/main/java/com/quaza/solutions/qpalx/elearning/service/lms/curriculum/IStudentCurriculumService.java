package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * @author manyce400
 */
public interface IStudentCurriculumService {


    public List<ELearningCurriculum> findAllStudentCoreELearningCurriculum(QPalXUser qPalXUser);

    public List<ELearningCurriculum> findAllStudentElectiveELearningCurriculum(QPalXUser qPalXUser);
}
