package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
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
     * Find all StudentTutorialLevel's for the given GeographicalRegion which are currently enabled.
     *
     * @param geographicalRegion
     * @return
     */
    @Query("Select  qPalXTutorialLevel From StudentTutorialLevel qPalXTutorialLevel Where qPalXTutorialLevel.geographicalRegion =?1 And qPalXTutorialLevel.enabled = 1")
    public List<StudentTutorialLevel> findAllGeographicalRegionTutorialLevels(final GeographicalRegion geographicalRegion);


    /**
     * Find all StudentTutorialGrade for the given StudentTutorialLevel
     *
     * @param studentTutorialLevel
     * @return
     */
    @Query("Select  tutorialGrade From StudentTutorialGrade tutorialGrade Where tutorialGrade.studentTutorialLevel =?1")
    public List<StudentTutorialGrade> findAllTutorialGradeForQPalXTutorialLevel(final StudentTutorialLevel studentTutorialLevel);
}
