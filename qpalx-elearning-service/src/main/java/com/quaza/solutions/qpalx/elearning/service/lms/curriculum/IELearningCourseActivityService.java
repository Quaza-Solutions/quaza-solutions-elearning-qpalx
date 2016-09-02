package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.MediaContentType;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.IELearningCourseActivityVO;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
public interface IELearningCourseActivityService {


    public ELearningCourseActivity buildNew(IELearningCourseActivityVO ieLearningCourseActivityVO);

    public ELearningMediaContent buildELearningMediaContent(File mediaContentFile, LearningActivityE learningActivityE);

    public boolean isCourseActivityTypeSupported(String mediaContentFileName);

    public Optional<MediaContentType> getMediaContentType(String mediaContentFileName);

    public String getMediaContentTypeUploadDirectory(MediaContentType mediaContentType, LearningActivityE learningActivityE);

    public String getMediaContentTypeSymbolicDirectory(MediaContentType mediaContentType, LearningActivityE learningActivityE);

    public ELearningCourseActivity findByID(Long id);

    public List<ELearningCourseActivity> findELearningCourseAcitivitiesByCourse(ELearningCourse eLearningCourse);

    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse);

}
