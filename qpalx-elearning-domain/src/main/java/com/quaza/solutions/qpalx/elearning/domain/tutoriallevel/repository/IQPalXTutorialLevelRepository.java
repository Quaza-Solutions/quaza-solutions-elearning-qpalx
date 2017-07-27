package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXCountry;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * JPA Repository object for QPalXTutorialRepository
 *
 * @author manyce400
 */
public interface IQPalXTutorialLevelRepository extends CrudRepository<StudentTutorialLevel, Long> {


    /**
     * Find all StudentTutorialLevel's for the given Country which are currently enabled.
     *
     * @param qPalXCountry
     * @return
     */
    @Query("Select  qPalXTutorialLevel From StudentTutorialLevel qPalXTutorialLevel Where qPalXTutorialLevel.qPalXCountry =?1 And qPalXTutorialLevel.enabled = 1")
    public List<StudentTutorialLevel> findAllQPalXCountryTutorialLevels(final QPalXCountry qPalXCountry);


    /**
     * Find all StudentTutorialGrade for the given StudentTutorialLevel
     *
     * @param studentTutorialLevel
     * @return
     */
    @Query("Select  tutorialGrade From StudentTutorialGrade tutorialGrade Where tutorialGrade.studentTutorialLevel =?1")
    public List<StudentTutorialGrade> findAllTutorialGradeForQPalXTutorialLevel(final StudentTutorialLevel studentTutorialLevel);
}
