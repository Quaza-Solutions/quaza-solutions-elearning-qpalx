package com.quaza.solutions.qpalx.elearning.domain.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;

/**
 * Value object interface for QPalXELesson domain object.
 *
 * @author manyce400
 */
public interface IQPalXELessonVO extends ILMSMediaContentVO {


    public String getLessonName();

    public String getLessonDescription();

    public Long getELearningCourseID();

    public String getProficiencyRankingScaleFloor();

    public String getProficiencyRankingScaleCeiling();

    public Long getTutorialLevelCalendarID();

    public Long getEducationalInstitutionID();

    public ProficiencyRankingScaleE getProficiencyRankingScaleFloorE();

    public ProficiencyRankingScaleE getProficiencyRankingScaleCeilingE();

    public boolean isActive();

}
