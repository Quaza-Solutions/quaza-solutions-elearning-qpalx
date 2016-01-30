package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;

import java.util.List;

/**
 * @author manyce400
 */
public interface IELearningCurriculumService {


    public ELearningCurriculum findByELearningCurriculumName(final String curriculumName);

    public List<ELearningCurriculum> findAllCurriculumByType(final CurriculumType curriculumType);

    public List<ELearningCurriculum> findAllCurriculumByRegionAndType(final CurriculumType curriculumType, final GeographicalRegion geographicalRegion);

    public List<ELearningCurriculum> findAllCurriculumByRegionTutorialLevelAndType(final CurriculumType curriculumType, final QPalXTutorialLevel qPalXTutorialLevel, final GeographicalRegion geographicalRegion);

}
