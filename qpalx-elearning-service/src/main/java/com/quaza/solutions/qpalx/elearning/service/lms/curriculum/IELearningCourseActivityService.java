package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
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

    public Optional<MediaContentTypeE> getMediaContentType(String mediaContentFileName);

    public String getMediaContentTypeUploadPhysicalDirectory(MediaContentTypeE mediaContentTypeE, LearningActivityE learningActivityE);

    public String getMediaContentTypeVirtualDirectory(MediaContentTypeE mediaContentTypeE, LearningActivityE learningActivityE);

    public ELearningCourseActivity findByID(Long id);

    public List<ELearningCourseActivity> findELearningCourseAcitivitiesByCourse(ELearningCourse eLearningCourse);

    public List<ELearningCourseActivity> findCourseAcitivitiesByCalendarAndCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse);

    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse);

}
