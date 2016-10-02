package com.quaza.solutions.qpalx.elearning.service.lms.media;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;

import java.io.File;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IQPalXTutorialContentService {


    public Optional<MediaContentTypeE> getMediaContentType(String mediaContentFileName);

    public String getTutorialContentTypeUploadPhysicalDirectory(MediaContentTypeE mediaContentTypeE, QPalXTutorialContentTypeE qPalXTutorialContentTypeE);

    public ELearningMediaContent buildELearningMediaContent(File mediaContentFile, QPalXTutorialContentTypeE qPalXTutorialContentTypeE);


}
