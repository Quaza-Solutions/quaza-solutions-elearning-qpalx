package com.quaza.solutions.qpalx.elearning.service.mock;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;

/**
 * @author manyce400
 */
public class MockELearningMediaContentBuilder {


    public static ELearningMediaContent buildMockCaptivateMediaContent() {
        ELearningMediaContent eLearningMediaContent = ELearningMediaContent.builder()
                .eLearningMediaFile("Algebra1-Quiz.swf")
                .eLearningMediaFile("SWF")
                .build();
        return eLearningMediaContent;
    }

}
