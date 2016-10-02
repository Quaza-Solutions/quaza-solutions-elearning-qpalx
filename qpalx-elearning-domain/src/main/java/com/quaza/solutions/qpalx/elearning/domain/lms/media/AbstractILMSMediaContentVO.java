package com.quaza.solutions.qpalx.elearning.domain.lms.media;

/**
 * @author manyce400
 */
public abstract class AbstractILMSMediaContentVO implements ILMSMediaContentVO {



    protected String qPalXTutorialContentType;


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
}
