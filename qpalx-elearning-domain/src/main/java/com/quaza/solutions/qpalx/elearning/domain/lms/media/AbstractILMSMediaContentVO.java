package com.quaza.solutions.qpalx.elearning.domain.lms.media;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;

/**
 * @author manyce400
 */
public abstract class AbstractILMSMediaContentVO implements ILMSMediaContentVO {



    protected String activeFlag;

    protected String qPalXTutorialContentType;

    protected ELearningMediaContent eLearningMediaContent;

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public boolean isActive() {
        return Boolean.valueOf(activeFlag);
    }


    public String getQPalXTutorialContentType() {
        return qPalXTutorialContentType;
    }

    public void setQPalXTutorialContentType(String qPalXTutorialContentType) {
        this.qPalXTutorialContentType = qPalXTutorialContentType;
    }

    @Override
    public QPalXTutorialContentTypeE getSelectedQPalXTutorialContentTypeE() {
        return QPalXTutorialContentTypeE.valueOf(qPalXTutorialContentType);
    }

    public ELearningMediaContent getELearningMediaContent() {
        return eLearningMediaContent;
    }

    public void setELearningMediaContent(ELearningMediaContent eLearningMediaContent) {
        this.eLearningMediaContent = eLearningMediaContent;
    }
}
