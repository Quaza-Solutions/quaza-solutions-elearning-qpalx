package com.quaza.solutions.qpalx.elearning.domain.lms.media;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;

import java.util.Set;

/**
 * @author manyce400
 */
public interface ILMSMediaContentVO {


    public boolean isActive();

    /**
     * Get all the MediaContentTypeE instances that are supported by this VO
     */
    public Set<MediaContentTypeE> getMediaContentTypes();

    /**
     * Get all the QPalXTutorialContentTypeE instances that are supported by this VO
     */
    public Set<QPalXTutorialContentTypeE> getQPalXTutorialContentTypes();

    /**
     * Get selected QPalXTutorialContentTypeE associated with this LMS Media Content
     */
    public QPalXTutorialContentTypeE getSelectedQPalXTutorialContentTypeE();

    public ELearningMediaContent getELearningMediaContent();

}
