package com.quaza.solutions.qpalx.elearning.web.student.curricula;

import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.IELearningCourseActivityVO;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author manyce400
 */
public class ELearningCourseActivityWebVO implements IELearningCourseActivityVO {


    public String activityName;

    public String activityDescription;

    public String activityType;

    public String activityFile;

    private Integer proficiencyRankingScaleFloor;

    private Integer proficiencyRankingScaleCeiling;

    private Long eLearningCourseID;

    private Long tutorialLevelCalendarID;


    @Override
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    @Override
    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    @Override
    public String getActivityFile() {
        return activityFile;
    }

    public void setActivityFile(String activityFile) {
        this.activityFile = activityFile;
    }

    @Override
    public Integer getProficiencyRankingScaleFloor() {
        return proficiencyRankingScaleFloor;
    }

    public void setProficiencyRankingScaleFloor(Integer proficiencyRankingScaleFloor) {
        this.proficiencyRankingScaleFloor = proficiencyRankingScaleFloor;
    }

    @Override
    public Integer getProficiencyRankingScaleCeiling() {
        return proficiencyRankingScaleCeiling;
    }

    public void setProficiencyRankingScaleCeiling(Integer proficiencyRankingScaleCeiling) {
        this.proficiencyRankingScaleCeiling = proficiencyRankingScaleCeiling;
    }

    @Override
    public Long getELearningCourseID() {
        return eLearningCourseID;
    }

    public void setELearningCourseID(Long eLearningCourseID) {
        this.eLearningCourseID = eLearningCourseID;
    }

    @Override
    public Long getTutorialLevelCalendarID() {
        return tutorialLevelCalendarID;
    }

    public void setTutorialLevelCalendarID(Long tutorialLevelCalendarID) {
        this.tutorialLevelCalendarID = tutorialLevelCalendarID;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("activityName", activityName)
                .append("activityDescription", activityDescription)
                .append("activityType", activityType)
                .append("activityFile", activityFile)
                .append("proficiencyRankingScaleFloor", proficiencyRankingScaleFloor)
                .append("proficiencyRankingScaleCeiling", proficiencyRankingScaleCeiling)
                .append("eLearningCourseID", eLearningCourseID)
                .append("tutorialLevelCalendarID", tutorialLevelCalendarID)
                .toString();
    }
}
