package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author manyce400
 */
public class AdaptiveProficiencyRankingVO implements IAdaptiveProficiencyRankingVO {


    private final String eLearningCurriculumName;

    private final String simplifiedProficiencyRank;

    public AdaptiveProficiencyRankingVO(String eLearningCurriculumName, String simplifiedProficiencyRank) {
        this.eLearningCurriculumName = eLearningCurriculumName;
        this.simplifiedProficiencyRank = simplifiedProficiencyRank;
    }

    @Override
    public String getELearningCurriculumName() {
        return eLearningCurriculumName;
    }

    @Override
    public String getSimplifiedProficiencyRank() {
        return simplifiedProficiencyRank;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("eLearningCurriculumName", eLearningCurriculumName)
                .append("simplifiedProficiencyRank", simplifiedProficiencyRank)
                .toString();
    }

}