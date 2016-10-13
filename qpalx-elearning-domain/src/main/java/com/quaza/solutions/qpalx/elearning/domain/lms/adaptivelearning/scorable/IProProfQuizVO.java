package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;

/**
 * @author manyce400
 */
public interface IProProfQuizVO {


    public Long getScorableActivityID();

    public String getQuizName();

    public String getQuizDescription();

    public Long getQPalXEMicroLessonID();

    public Double getMaxPossibleQuizScore();

    public String getProficiencyRankingScaleFloor();

    public String getProficiencyRankingScaleCeiling();

    public ProficiencyRankingScaleE getProficiencyRankingScaleFloorE();

    public ProficiencyRankingScaleE getProficiencyRankingScaleCeilingE();

    public String getQuizEmbedURL();

    public boolean isActive();

}
