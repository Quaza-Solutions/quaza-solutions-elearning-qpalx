package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 * @author manyce400
 */
public interface IELearningCurriculumRepository extends CrudRepository<ELearningCurriculum, Long> {


    /**
     * Find ELearningCurriculum by its name.
     *
     * @param curriculumName
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum Where eLearningCurriculum.curriculumName = ?1")
    public ELearningCurriculum findByELearningCurriculumName(final String curriculumName);

    /**
     * Find all the ELearningCurriculum of a specific CurriculumType
     *
     * @param curriculumType
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum Where eLearningCurriculum.curriculumType = ?1")
    public List<ELearningCurriculum> findAllCurriculumByType(final CurriculumType curriculumType);

    /**
     * Find all the ElearningCurriculum of a specific CurriculumType by GeographicalRegion
     *
     * @param curriculumType
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum JOIN FETCH eLearningCurriculum.geographicalRegion Where eLearningCurriculum.curriculumType = ?1 And eLearningCurriculum.geographicalRegion =?2")
    public List<ELearningCurriculum> findAllCurriculumByRegionAndType(final CurriculumType curriculumType, final GeographicalRegion geographicalRegion);

    /**
     * Find all the ElearningCurriculum of a specific CurriculumType for a QPalXTutorialLevel in a GeographicalRegion
     *
     * @param curriculumType
     * @return
     */
    @Query("Select eLearningCurriculum From ELearningCurriculum eLearningCurriculum JOIN FETCH eLearningCurriculum.geographicalRegion  JOIN FETCH eLearningCurriculum.qPalXTutorialLevel Where eLearningCurriculum.curriculumType = ?1 And eLearningCurriculum.qPalXTutorialLevel =?2 And eLearningCurriculum.geographicalRegion =?3")
    public List<ELearningCurriculum> findAllCurriculumByRegionTutorialLevelAndType(final CurriculumType curriculumType, final QPalXTutorialLevel qPalXTutorialLevel,  final GeographicalRegion geographicalRegion);
}
