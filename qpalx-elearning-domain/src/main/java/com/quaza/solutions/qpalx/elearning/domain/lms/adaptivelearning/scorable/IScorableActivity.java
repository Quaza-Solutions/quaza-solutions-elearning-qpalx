package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable;

/**
 * @author manyce400
 */
public interface IScorableActivity {


    public Long getScorableActivityID();

    public String getScorableActivityName();

    public String getScorableActivityDescription();

    public Double getMaxPossibleActivityScore();

}
