package com.quaza.solutions.qpalx.elearning.domain.lms.media;

import java.util.Set;

/**
 * @author manyce400
 */
public interface ILMSMediaContentVO {

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

}
