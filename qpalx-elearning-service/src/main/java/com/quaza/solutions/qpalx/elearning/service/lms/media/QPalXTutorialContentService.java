package com.quaza.solutions.qpalx.elearning.service.lms.media;

import com.quaza.solutions.qpalx.elearning.config.file.management.FileUploadLocationConfiguration;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.QPalXTutorialContentTypeE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.util.Optional;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXTutorialContentService")
public class QPalXTutorialContentService implements IQPalXTutorialContentService {



    @Autowired
    private FileUploadLocationConfiguration fileUploadLocationConfiguration;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXTutorialContentService.class);


    @Override
    public Optional<MediaContentTypeE> getMediaContentType(String mediaContentFileName) {
        Assert.notNull(mediaContentFileName, "mediaContentFileName cannot be null");
        LOGGER.debug("Retrieving media content type for file: {}", mediaContentFileName);

        if(mediaContentFileName.lastIndexOf(".") != -1 && mediaContentFileName.lastIndexOf(".") != 0) {
            String fileType = mediaContentFileName.substring(mediaContentFileName.lastIndexOf(".")+1);
            try {
                return Optional.of(MediaContentTypeE.valueOf(fileType));
            } catch (IllegalArgumentException e) {
                LOGGER.warn("Could not find matching media content type for file: {}", mediaContentFileName);
            }
        }

        return Optional.empty();
    }

    @Override
    public String getTutorialContentTypeUploadPhysicalDirectory(MediaContentTypeE mediaContentTypeE, QPalXTutorialContentTypeE qPalXTutorialContentTypeE) {
        Assert.notNull(mediaContentTypeE, "mediaContentTypeE cannot be null");
        Assert.notNull(qPalXTutorialContentTypeE, "qPalXTutorialContentTypeE cannot be null");

        LOGGER.debug("Getting physical file upload directory for mediaContentTypeE:>cf {} qPalXTutorialContentTypeE:> {}", mediaContentTypeE, qPalXTutorialContentTypeE);

        String uploadDirectory = null;
        switch (qPalXTutorialContentTypeE) {
            case Video:
                uploadDirectory = fileUploadLocationConfiguration.getELearningContentPhysicalFileResourcesDir() + "videos/";
                break;
            case Quiz:
                uploadDirectory = fileUploadLocationConfiguration.getELearningContentPhysicalFileResourcesDir() + "quizzes/";
                break;
            case Assesment:
                uploadDirectory = fileUploadLocationConfiguration.getELearningContentPhysicalFileResourcesDir() + "assesments/";
                break;
            case Assignment:
                uploadDirectory = fileUploadLocationConfiguration.getELearningContentPhysicalFileResourcesDir() + "assignments/";
                break;
            case Interactive_Excersise:
                uploadDirectory = fileUploadLocationConfiguration.getELearningContentPhysicalFileResourcesDir() + "interactive-exercises/";
                break;
            default:
                break;

        }

        return uploadDirectory;
    }

    @Override
    public ELearningMediaContent buildELearningMediaContent(File mediaContentFile, QPalXTutorialContentTypeE qPalXTutorialContentTypeE) {
        Assert.notNull(mediaContentFile, "mediaContentFile cannot be null");
        Assert.notNull(qPalXTutorialContentTypeE, "qPalXTutorialContentTypeE cannot be null");

        LOGGER.debug("Creating new ELearningMediaContent from file: {}", mediaContentFile);

        // Get the file extension to figure out the media content type
        Optional<MediaContentTypeE> optionalMediaContentType = getMediaContentType(mediaContentFile.getName());

        // We save file name using symbolic link directory as the actual file will get uploaded to a directory outside web app context
        String symbolicFileDirectory = getMediaContentTypeVirtualDirectory(optionalMediaContentType.get(), qPalXTutorialContentTypeE);
        String symbolicFileName = symbolicFileDirectory + mediaContentFile.getName();

        return ELearningMediaContent.builder()
                .eLearningMediaType(optionalMediaContentType.get().toString())
                .qPalXTutorialContentTypeE(qPalXTutorialContentTypeE)
                .eLearningMediaFile(symbolicFileName)
                .build();
    }

    public String getMediaContentTypeVirtualDirectory(MediaContentTypeE mediaContentTypeE, QPalXTutorialContentTypeE qPalXTutorialContentTypeE) {
        Assert.notNull(mediaContentTypeE, "mediaContentTypeE cannot be null");
        Assert.notNull(qPalXTutorialContentTypeE, "qPalXTutorialContentTypeE cannot be null");

        String uploadDirectory = null;

        switch (qPalXTutorialContentTypeE) {
            case Video:
                uploadDirectory = fileUploadLocationConfiguration.geteLearningContentVideosDir();
                break;
            case Quiz:
                uploadDirectory = fileUploadLocationConfiguration.geteLearningContentQuizzesDir();
                break;
            case Assesment:
                uploadDirectory = fileUploadLocationConfiguration.geteLearningContentAssesmentsDir();
                break;
            case Assignment:
                uploadDirectory = fileUploadLocationConfiguration.geteLearningContentAssignmentsDir();
                break;
            case Interactive_Excersise:
                uploadDirectory = fileUploadLocationConfiguration.geteLearningContentInteractiveExercisesDir();
                break;
            default:
                break;

        }

        return uploadDirectory;
    }

}
