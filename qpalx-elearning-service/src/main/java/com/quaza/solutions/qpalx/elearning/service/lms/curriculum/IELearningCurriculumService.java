package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

import java.util.List;

/**
 * @author manyce400
 */
public interface IELearningCurriculumService {

    public ELearningCurriculum findByELearningCurriculumID(final Long curriculumID);

    public ELearningCurriculum findByELearningCurriculumName(final String curriculumName);

    public List<ELearningCurriculum> findAllCurriculumByType(final CurriculumType curriculumType);

    public List<ELearningCurriculum> findAllCurriculumByTutorialGrade(final StudentTutorialGrade studentTutorialGrade);

    public List<ELearningCurriculum> findAllCurriculumByTutorialGradeAndType(final CurriculumType curriculumType, final StudentTutorialGrade studentTutorialGrade);

}
