package com.quaza.solutions.qpalx.elearning.web.utils;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implements and provides File upload capabilities.
 *
 * @author manyce400
 */
public interface IFileUploadUtil {


    public String uploadAndScaleImageFile(MultipartFile imageFile);

    public ELearningMediaContent uploadELearningMediaContent(MultipartFile multipartFile, ILMSMediaContentVO ilmsMediaContentVO);

    public ELearningMediaContent uploadELearningCourseActivityContent(MultipartFile multipartFile, LearningActivityE learningActivityE);

}
