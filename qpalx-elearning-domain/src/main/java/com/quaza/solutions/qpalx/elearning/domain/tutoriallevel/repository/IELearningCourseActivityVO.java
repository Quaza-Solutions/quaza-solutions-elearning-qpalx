package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository;

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

}
