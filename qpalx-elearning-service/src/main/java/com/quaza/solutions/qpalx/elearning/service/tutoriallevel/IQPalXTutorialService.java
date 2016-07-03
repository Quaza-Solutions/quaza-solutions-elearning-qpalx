package com.quaza.solutions.qpalx.elearning.service.tutoriallevel;

import com.quaza.solutions.qpalx.elearning.domain.geographical.GeographicalRegion;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXTutorialService {

    public StudentTutorialLevel findQPalXTutorialLevelByID(Long id);

    public StudentTutorialGrade findTutorialGradeByID(Long id);

    public List<StudentTutorialGrade> findAllStudentTutorialGrade();

    public List<StudentTutorialLevel> findAllQPalXTutorialLevels();

    public List<StudentTutorialLevel> findAllGeographicalRegionTutorialLevels(final GeographicalRegion geographicalRegion);

}
