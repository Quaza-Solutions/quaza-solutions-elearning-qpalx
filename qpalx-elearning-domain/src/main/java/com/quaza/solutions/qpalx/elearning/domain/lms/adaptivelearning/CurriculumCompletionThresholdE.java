package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning;

import org.junit.Assert;

/**
 * @author manyce400
 */
public enum CurriculumCompletionThresholdE {


    TWENTY_FIVE_PERCENT(25.0),

    FIFTY_PERCENT(50.0),

    SEVENTY_FIVE_PERCENT(75.0),

    ONE_HUNDRED_PERCENT(100.0),
    ;


    private double completionThreshold;

    CurriculumCompletionThresholdE(double completionThreshold) {
        this.completionThreshold = completionThreshold;
    }

    public double getCompletionThreshold() {
        return completionThreshold;
    }

    public static CurriculumCompletionThresholdE getTargetCurriculumCompletionThresholdE(double completion) {
        Assert.assertTrue("Completion % cannot be greater that 100%",completion <= 100.0);
        if(completion < CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT.getCompletionThreshold()) {
            return CurriculumCompletionThresholdE.TWENTY_FIVE_PERCENT;
        } else if(completion < CurriculumCompletionThresholdE.FIFTY_PERCENT.getCompletionThreshold()) {
            return CurriculumCompletionThresholdE.FIFTY_PERCENT;
        } else if(completion < CurriculumCompletionThresholdE.SEVENTY_FIVE_PERCENT.getCompletionThreshold()) {
            return CurriculumCompletionThresholdE.SEVENTY_FIVE_PERCENT;
        }

        return CurriculumCompletionThresholdE.ONE_HUNDRED_PERCENT;
    }
}
