package com.quaza.solutions.qpalx.elearning.web.utils;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.ILMSMediaContentVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implements and provides File upload capabilities.
 *
 * @author manyce400
 */
public interface IFileUploadUtil {


    public String uploadAndScaleImageFile(MultipartFile imageFile);

    public String uploadQPalxUserPhoto(QPalXUser qPalXUser, MultipartFile multipartFile);

    public ELearningMediaContent uploadELearningMediaContent(MultipartFile multipartFile, ILMSMediaContentVO ilmsMediaContentVO);

    public ELearningMediaContent uploadELearningCourseActivityContent(MultipartFile multipartFile, LearningActivityE learningActivityE);

    public void deleteELearningMediaContent(ELearningMediaContent eLearningMediaContent);

}
