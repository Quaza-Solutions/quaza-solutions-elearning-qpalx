package com.quaza.solutions.qpalx.elearning.web.utils;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.MediaContentType;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCourseActivityService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

/**
 * @author manyce400
 */
@Service("com.quaza.solutions.qpalx.elearning.web.FileUploadUtil")
public class FileUploadUtil implements IFileUploadUtil {




    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseActivityService")
    private IELearningCourseActivityService ieLearningCourseActivityService;


    public static final String IMAGES_UPLOAD_DIRECTORY = "/Users/manyce400/QuazaSolutions/quaza-solutions-elearning-qpalx/qpalx-elearning-web/src/main/resources/static/img/students/";

    public static final String ELEARNING_VIDEOS_UPLOAD_DIRECTORY = "/Users/manyce400/QuazaSolutions/static-content/videos/";

    public static final String QUIZZES_VIDEOS_UPLOAD_DIRECTORY = "/Users/manyce400/QuazaSolutions/static-content/quizzes/";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FileUploadUtil.class);


    @Override
    public String uploadAndScaleImageFile(MultipartFile imageFile) {
        Assert.notNull(imageFile, "imageFile cannot be null");
        String fileName = imageFile.getOriginalFilename();
        System.out.println("Original FileName = "+imageFile.getOriginalFilename());
        LOGGER.info("Loading new file with fileName:> {}", fileName);

        if (!imageFile.isEmpty()) {
            try {
                // First we need to upload and output to local directory
                byte[] bytes = imageFile.getBytes();
                String newFileName = IMAGES_UPLOAD_DIRECTORY + fileName;
                System.out.println("newFileName = " + newFileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(newFileName)));
                stream.write(bytes);
                stream.close();

                // now we can resize the file and overwrite it
                LOGGER.debug("Resizing uploade file...");
                BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
                BufferedImage rescaledImage = Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, 150, 100, Scalr.OP_ANTIALIAS);
                File overWriteFile = new File(newFileName);

                ImageIO.write(rescaledImage, "JPG", overWriteFile);

                return newFileName;
            } catch (Exception e) {
                LOGGER.error("Exception occurred while uploading file....", e);
                return null;
            }
        } else {
            LOGGER.warn("Mulitpart imageFile provided was empty, cannot upload file");
            return null;
        }
    }

    @Override
    public ELearningMediaContent uploadELearningCourseActivityContent(MultipartFile multipartFile, LearningActivityE learningActivityE) {
        Assert.notNull(multipartFile, "multipartFile cannot be null");
        Assert.notNull(learningActivityE, "learningActivityE cannot be null");

        // Check to see if the media content file type is current supported in QPalX
        String fileName = multipartFile.getOriginalFilename();
        boolean isSupported = ieLearningCourseActivityService.isCourseActivityTypeSupported(fileName);

        if (isSupported) {
            // Get the actual media content type and the actual directory to upload this file to
            Optional<MediaContentType> optionalMediaContentType = ieLearningCourseActivityService.getMediaContentType(fileName);
            MediaContentType mediaContentType = optionalMediaContentType.get();
            String fileUploadDirectory = ieLearningCourseActivityService.getMediaContentTypeUploadDirectory(mediaContentType, learningActivityE);
            LOGGER.info("Uploading Course activity media content file:> {} to location:> {}", fileName, fileUploadDirectory);

            File mediaContentFile = writeFileToDisk(multipartFile, fileUploadDirectory);
            if (mediaContentFile != null) {
                ELearningMediaContent eLearningMediaContent = ieLearningCourseActivityService.buildELearningMediaContent(mediaContentFile, learningActivityE);
                return eLearningMediaContent;
            }

            return null;
        }

        return ELearningMediaContent.NOT_SUPPORTED_MEDIA_CONTENT;
    }


    private File writeFileToDisk(MultipartFile multipartFile, String fileLocation) {
        String fileName = multipartFile.getOriginalFilename();

        try {
            // First we need to upload and output to local directory
            byte[] bytes = multipartFile.getBytes();
            String newFileName = fileLocation + fileName;
            LOGGER.info("Writing new file with name: {} to output stream", fileName);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(newFileName)));
            stream.write(bytes);
            stream.close();
            return new File(newFileName);
        } catch (Exception e) {
            LOGGER.error("Exception occurred while uploading file.  File could not be uploaded", e);
            return null;
        }
    }

    private boolean isResizeableToNewDimensions(BufferedImage bufferedImage, int targetHeight, int targetWidth) {
        int currentHeight = bufferedImage.getHeight();
        int currentWidth = bufferedImage.getWidth();

        if(currentHeight > targetHeight && currentWidth > targetWidth) {
            return true;
        }

        return false;
    }
}
