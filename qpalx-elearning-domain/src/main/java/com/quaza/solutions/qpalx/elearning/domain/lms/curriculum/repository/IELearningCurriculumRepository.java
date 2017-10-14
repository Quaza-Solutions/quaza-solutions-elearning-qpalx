package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
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
     * Find ELearningCurriculum by its name and type
     *
     * @param curriculumName
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum Where eLearningCurriculum.curriculumName = ?1 And eLearningCurriculum.curriculumType = ?2")
    public ELearningCurriculum findByELearningCurriculumNameAndType(final String curriculumName, CurriculumType curriculumType);

    /**
     * Find all the ELearningCurriculum of a specific CurriculumType
     *
     * @param curriculumType
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum Where eLearningCurriculum.curriculumType = ?1")
    public List<ELearningCurriculum> findAllCurriculumByType(final CurriculumType curriculumType);

    /**
     * Find all the ElearningCurriculum for a StudentTutorialLevel
     *
     * @return List<ELearningCurriculum>
     */
    @Query("Select eLearningCurriculum From ELearningCurriculum eLearningCurriculum JOIN FETCH eLearningCurriculum.studentTutorialGrade Where eLearningCurriculum.studentTutorialGrade =?1")
    public List<ELearningCurriculum> findAllCurriculumByTutorialGrade(final StudentTutorialGrade studentTutorialGrade);

    /**
     * Find all the ElearningCurriculum of a specific CurriculumType for a StudentTutorialLevel in a GeographicalRegion
     *
     * @param curriculumType
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum " +
            "JOIN   FETCH eLearningCurriculum.studentTutorialGrade " +
            "Where  eLearningCurriculum.curriculumType = ?1 " +
            "And    eLearningCurriculum.studentTutorialGrade =?2 ")
    public List<ELearningCurriculum> findAllCurriculumByTutorialGradeAndType(final CurriculumType curriculumType, final StudentTutorialGrade studentTutorialGrade);

    /**
     * Find all the ElearningCurriculum of a specific CurriculumType for a StudentTutorialLevel where the Curriculum is currently active
     *
     * @param curriculumType
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum " +
            "JOIN   FETCH eLearningCurriculum.studentTutorialGrade " +
            "Where  eLearningCurriculum.curriculumType = ?1 " +
            "And    eLearningCurriculum.studentTutorialGrade =?2 " +
            "And    eLearningCurriculum.active = true"
    )
    public List<ELearningCurriculum> findAllCurriculumByTutorialGradeAndTypeAndActive(final CurriculumType curriculumType, final StudentTutorialGrade studentTutorialGrade);

    /**
     * Find all the ElearningCurriculum of a specific CurriculumType for a StudentTutorialLevel in a GeographicalRegion
     *
     * @param curriculumType
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum " +
            "JOIN   FETCH eLearningCurriculum.studentTutorialGrade as studentTutorialGrade " +
            "JOIN   FETCH studentTutorialGrade.studentTutorialLevel " +
            "Where  eLearningCurriculum.curriculumType = ?1 " +
            "And    studentTutorialGrade.studentTutorialLevel =?2"
    )
    public List<ELearningCurriculum> findAllCurriculumByTutorialTypeAndCurriculumType(final CurriculumType curriculumType, final StudentTutorialLevel studentTutorialLevel);

    /**
     * Find all the ElearningCurriculum of a specific CurriculumType for a StudentTutorialLevel in a GeographicalRegion
     *
     * @param curriculumType
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum " +
            "JOIN   FETCH eLearningCurriculum.studentTutorialGrade as studentTutorialGrade " +
            "JOIN   FETCH studentTutorialGrade.studentTutorialLevel " +
            "Where  eLearningCurriculum.curriculumType = ?1 " +
            "And    studentTutorialGrade.studentTutorialLevel =?2 " +
            "And    eLearningCurriculum.active = true"
    )
    public List<ELearningCurriculum> findAllCurriculumByTutorialTypeAndCurriculumTypeAndActive(final CurriculumType curriculumType, final StudentTutorialLevel studentTutorialLevel);

    /**
     * Find ELearningCurriculum by its name and type
     *
     * @param curriculumName
     * @return
     */
    @Query("Select  eLearningCurriculum From ELearningCurriculum eLearningCurriculum JOIN FETCH eLearningCurriculum.studentTutorialGrade Where eLearningCurriculum.curriculumName = ?1 And eLearningCurriculum.curriculumType = ?2 And eLearningCurriculum.studentTutorialGrade =?3")
    public ELearningCurriculum findByELearningCurriculumNameTypeAndTutorialGrade(final String curriculumName, final CurriculumType curriculumType, final StudentTutorialGrade studentTutorialGrade);


}
