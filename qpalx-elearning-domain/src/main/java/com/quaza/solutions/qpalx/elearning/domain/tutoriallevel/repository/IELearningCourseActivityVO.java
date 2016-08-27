package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;

/**
 * @author manyce400
 */
public interface IELearningCourseActivityVO {


    public String getActivityName();

    public String getActivityDescription();

    public String getActivityType();

    public String getActivityFile();

    public String getProficiencyRankingScaleFloor();

    public String getProficiencyRankingScaleCeiling();

    public Long getELearningCourseID();

    public Long getTutorialLevelCalendarID();

    public ELearningMediaContent getELearningMediaContent();

    public ProficiencyRankingScaleE getproficiencyRankingScaleFloorE();

    public ProficiencyRankingScaleE getProficiencyRankingScaleCeilingE();

}
