package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.QPalXTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialGrade;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXTutorialService {

    public QPalXTutorialLevel findQPalXTutorialLevelByID(Long id);

    public TutorialGrade findTutorialGradeByID(Long id);

    public List<QPalXTutorialLevel> findAllQPalXTutorialLevels();

    public List<QPalXTutorialLevel> findAllGeographicalRegionTutorialLevels(final GeographicalRegion geographicalRegion);

}
