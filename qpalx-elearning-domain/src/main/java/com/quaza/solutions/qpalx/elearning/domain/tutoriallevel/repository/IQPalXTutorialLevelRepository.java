package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialGrade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * JPA Repository object for QPalXTutorialRepository
 *
 * @author manyce400
 */
public interface IQPalXTutorialLevelRepository extends CrudRepository<QPalXTutorialLevel, Long> {


    /**
     * Find all QPalXTutorialLevel's for the given GeographicalRegion which are currently enabled.
     *
     * @param geographicalRegion
     * @return
     */
    @Query("Select  qPalXTutorialLevel From QPalXTutorialLevel qPalXTutorialLevel Where qPalXTutorialLevel.geographicalRegion =?1 And qPalXTutorialLevel.enabled = 1")
    public List<QPalXTutorialLevel> findAllGeographicalRegionTutorialLevels(final GeographicalRegion geographicalRegion);


    /**
     * Find all TutorialGrade for the given QPalXTutorialLevel
     *
     * @param qPalXTutorialLevel
     * @return
     */
    @Query("Select  tutorialGrade From TutorialGrade tutorialGrade Where tutorialGrade.qPalXTutorialLevel =?1")
    public List<TutorialGrade> findAllTutorialGradeForQPalXTutorialLevel(final QPalXTutorialLevel qPalXTutorialLevel);
}
