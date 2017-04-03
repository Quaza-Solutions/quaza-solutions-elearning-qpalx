package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

/**
 * @author manyce400
 */
public enum ProficiencyRankingScoreModelE {


    CurriculumCompletion("Curriculum Completion Score Model"),

    AvgLearningExperiences("Average Learning Exepriences Score Model")

    ;

    private String modelDescription;

    ProficiencyRankingScoreModelE(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getModelDescription() {
        return modelDescription;
    }


}
