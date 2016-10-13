package com.quaza.solutions.qpalx.elearning.web.adaptivelearning.vo;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IProProfQuizVO;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;

/**
 * @author manyce400
 */
public class ProProfQuizWebVO implements IProProfQuizVO {


    private Long scorableActivityID;

    private String quizName;

    private String quizDescription;

    private String quizEmbedURL;

    private Long qPalXEMicroLessonID;

    private Double maxPossibleQuizScore;

    private String proficiencyRankingScaleFloor;

    private String proficiencyRankingScaleCeiling;

    private boolean active;


    public ProProfQuizWebVO() {

    }

    @Override
    public Long getScorableActivityID() {
        return scorableActivityID;
    }

    public void setScorableActivityID(Long scorableActivityID) {
        this.scorableActivityID = scorableActivityID;
    }

    @Override
    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    @Override
    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    @Override
    public String getQuizEmbedURL() {
        return quizEmbedURL;
    }

    public void setQuizEmbedURL(String quizEmbedURL) {
        this.quizEmbedURL = quizEmbedURL;
    }

    public Long getQPalXEMicroLessonID() {
        return qPalXEMicroLessonID;
    }

    public void setQPalXEMicroLessonID(Long qPalXEMicroLessonID) {
        this.qPalXEMicroLessonID = qPalXEMicroLessonID;
    }

    @Override
    public Double getMaxPossibleQuizScore() {
        return maxPossibleQuizScore;
    }

    public void setMaxPossibleQuizScore(Double maxPossibleQuizScore) {
        this.maxPossibleQuizScore = maxPossibleQuizScore;
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

    @Override
    public ProficiencyRankingScaleE getProficiencyRankingScaleFloorE() {
        return ProficiencyRankingScaleE.valueOf(proficiencyRankingScaleFloor);
    }

    @Override
    public ProficiencyRankingScaleE getProficiencyRankingScaleCeilingE() {
        return ProficiencyRankingScaleE.valueOf(proficiencyRankingScaleCeiling);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
