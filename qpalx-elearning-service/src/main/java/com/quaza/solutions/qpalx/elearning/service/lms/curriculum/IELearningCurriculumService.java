package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;

import java.util.List;

/**
 * @author manyce400
 */
public interface IELearningCurriculumService {

    public ELearningCurriculum findByELearningCurriculumID(final Long curriculumID);

    public ELearningCurriculum findByELearningCurriculumName(final String curriculumName);

    public List<ELearningCurriculum> findAllCurriculumByType(final CurriculumType curriculumType);

    public ELearningCurriculum findByELearningCurriculumNameAndType(final String curriculumName, CurriculumType curriculumType);

    public List<ELearningCurriculum> findAllCurriculumByTutorialGrade(final StudentTutorialGrade studentTutorialGrade);

    public List<ELearningCurriculum> findAllCurriculumByTutorialGradeAndType(final CurriculumType curriculumType, final StudentTutorialGrade studentTutorialGrade);

    public List<ELearningCurriculum> findAllCurriculumByTutorialGradeAndTypeAndActive(final CurriculumType curriculumType, final StudentTutorialGrade studentTutorialGrade);

    public List<ELearningCurriculum> findAllCurriculumByTutorialTypeAndCurriculumType(final CurriculumType curriculumType, final StudentTutorialLevel studentTutorialLevel);

    public List<ELearningCurriculum> findAllCurriculumByTutorialTypeAndCurriculumTypeAndActive(final CurriculumType curriculumType, final StudentTutorialLevel studentTutorialLevel);

}
