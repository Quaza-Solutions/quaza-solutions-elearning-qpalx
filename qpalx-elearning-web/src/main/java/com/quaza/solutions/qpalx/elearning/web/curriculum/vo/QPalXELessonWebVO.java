package com.quaza.solutions.qpalx.elearning.web.curriculum.vo;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXELessonVO;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author manyce400
 */
public class QPalXELessonWebVO implements IQPalXELessonVO {


    public String lessonName;

    public String lessonDescription;

    private String proficiencyRankingScaleFloor;

    private String proficiencyRankingScaleCeiling;

    private Long eLearningCourseID;

    private Long tutorialLevelCalendarID;

    private Long educationalInstitutionID;

    public ELearningMediaContent eLearningMediaContent;

    @Override
    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    @Override
    public String getLessonDescription() {
        return lessonDescription;
    }

    public void setLessonDescription(String lessonDescription) {
        this.lessonDescription = lessonDescription;
    }

    @Override
    public String getProficiencyRankingScaleFloor() {
        return proficiencyRankingScaleFloor;
    }

    public void setProficiencyRankingScaleFloor(String proficiencyRankingScaleFloor) {
        this.proficiencyRankingScaleFloor = proficiencyRankingScaleFloor;
    }

    @Override
    public String getProficiencyRankingScaleCeiling() {
        return proficiencyRankingScaleCeiling;
    }

    public void setProficiencyRankingScaleCeiling(String proficiencyRankingScaleCeiling) {
        this.proficiencyRankingScaleCeiling = proficiencyRankingScaleCeiling;
    }

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
    public Long getEducationalInstitutionID() {
        return educationalInstitutionID;
    }

    public void setEducationalInstitutionID(Long educationalInstitutionID) {
        this.educationalInstitutionID = educationalInstitutionID;
    }

    public ELearningMediaContent getELearningMediaContent() {
        return eLearningMediaContent;
    }

    public void setELearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }

    @Override
    public ProficiencyRankingScaleE getProficiencyRankingScaleFloorE() {
        return ProficiencyRankingScaleE.valueOf(proficiencyRankingScaleFloor);
    }

    @Override
    public ProficiencyRankingScaleE getProficiencyRankingScaleCeilingE() {
        return ProficiencyRankingScaleE.valueOf(proficiencyRankingScaleCeiling);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("lessonName", lessonName)
                .append("lessonDescription", lessonDescription)
                .append("proficiencyRankingScaleFloor", proficiencyRankingScaleFloor)
                .append("proficiencyRankingScaleCeiling", proficiencyRankingScaleCeiling)
                .append("eLearningCourseID", eLearningCourseID)
                .append("tutorialLevelCalendarID", tutorialLevelCalendarID)
                .append("educationalInstitutionID", educationalInstitutionID)
                .append("eLearningMediaContent", eLearningMediaContent)
                .toString();
    }
}
