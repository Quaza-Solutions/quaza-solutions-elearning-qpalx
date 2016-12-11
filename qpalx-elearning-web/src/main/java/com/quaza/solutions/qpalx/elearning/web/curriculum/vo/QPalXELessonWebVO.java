package com.quaza.solutions.qpalx.elearning.web.curriculum.vo;

import com.google.common.collect.ImmutableSet;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXELessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.AbstractILMSMediaContentVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

/**
 * @author manyce400
 */
public class QPalXELessonWebVO extends AbstractILMSMediaContentVO implements IQPalXELessonVO {


    public String lessonName;

    public String lessonDescription;

    private String proficiencyRankingScaleFloor;

    private String proficiencyRankingScaleCeiling;

    private Long eLearningCourseID;

    private Long tutorialLevelCalendarID;

    private Long educationalInstitutionID;


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

    @Override
    public ProficiencyRankingScaleE getProficiencyRankingScaleFloorE() {
        return ProficiencyRankingScaleE.valueOf(proficiencyRankingScaleFloor);
    }

    @Override
    public ProficiencyRankingScaleE getProficiencyRankingScaleCeilingE() {
        return ProficiencyRankingScaleE.valueOf(proficiencyRankingScaleCeiling);
    }

    @Override
    public Set<MediaContentTypeE> getMediaContentTypes() {
        return ImmutableSet.of(MediaContentTypeE.mp4);
    }

    @Override
    public Set<QPalXTutorialContentTypeE> getQPalXTutorialContentTypes() {
        return ImmutableSet.of(QPalXTutorialContentTypeE.Video);
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
                .append("activeFlag", activeFlag)
                .append("qPalXTutorialContentType", qPalXTutorialContentType)
                .append("eLearningMediaContent", eLearningMediaContent)
                .toString();
    }
}
