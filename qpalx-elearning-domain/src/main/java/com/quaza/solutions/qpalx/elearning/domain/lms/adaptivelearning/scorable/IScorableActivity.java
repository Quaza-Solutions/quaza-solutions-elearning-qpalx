package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;

/**
 * @author manyce400
 */
public interface IScorableActivity {


    public Long getScorableActivityID();

    public String getScorableActivityName();

    public String getScorableActivityDescription();

    public Double getMaxPossibleActivityScore();

    public ProficiencyRankingScaleE getProficiencyRankingScaleFloor();

    public ProficiencyRankingScaleE getProficiencyRankingScaleCeiling();

}
