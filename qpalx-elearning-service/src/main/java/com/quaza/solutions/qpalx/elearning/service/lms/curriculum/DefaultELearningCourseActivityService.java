package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.config.file.management.FileUploadLocationConfiguration;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.MediaContentType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCourseActivityRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.TutorialLevelCalendar;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.IELearningCourseActivityVO;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.ITutorialLevelCalendarRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseActivityService")
public class DefaultELearningCourseActivityService implements IELearningCourseActivityService {




    @Autowired
    private FileUploadLocationConfiguration fileUploadLocationConfiguration;

    @Autowired
    private IELearningCourseActivityRepository ieLearningCourseActivityRepository;

    @Autowired
    private ITutorialLevelCalendarRepository iTutorialLevelCalendarRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultELearningCourseService")
    private IELearningCourseService ieLearningCourseService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultELearningCourseActivityService.class);


    @Override
    public ELearningCourseActivity buildNew(IELearningCourseActivityVO iELearningCourseActivityVO) {
        Assert.notNull(iELearningCourseActivityVO, "iELearningCourseActivityVO cannot be null");
        LOGGER.info("Creating new iELearningCourseActivityVO:> {}", iELearningCourseActivityVO);

        Long eLearningCourseID = iELearningCourseActivityVO.getELearningCourseID();
        ELearningCourse eLearningCourse = ieLearningCourseService.findByCourseID(eLearningCourseID);
        TutorialLevelCalendar tutorialLevelCalendar = iTutorialLevelCalendarRepository.findOne(iELearningCourseActivityVO.getTutorialLevelCalendarID());

        LearningActivityE learningActivityE = LearningActivityE.valueOf(iELearningCourseActivityVO.getActivityType());

        ELearningCourseActivity eLearningCourseActivity = ELearningCourseActivity.builder()
                .eLearningCourse(eLearningCourse)
                .learningActivityE(learningActivityE)
                .activityName(iELearningCourseActivityVO.getActivityName())
                .activityDescription(iELearningCourseActivityVO.getActivityDescription())
                .eLearningMediaContent(iELearningCourseActivityVO.getELearningMediaContent())
                .tutorialLevelCalendar(tutorialLevelCalendar)
                .proficiencyRankingScaleFloor(iELearningCourseActivityVO.getproficiencyRankingScaleFloorE())
                .proficiencyRankingScaleCeiling(iELearningCourseActivityVO.getProficiencyRankingScaleCeilingE())
                .activityActive(true)
                .entryDate(new DateTime())
                .build();

        ieLearningCourseActivityRepository.save(eLearningCourseActivity);
        return null;
    }

    @Override
    public ELearningMediaContent buildELearningMediaContent(File mediaContentFile) {
        Assert.notNull(mediaContentFile, "mediaContentFile cannot be null");

        LOGGER.debug("Creating new ELearningMediaContent from file: {}", mediaContentFile);
        String fullPathFileName = mediaContentFile.getAbsolutePath();

        // Get the file extension
        Optional<MediaContentType> optionalMediaContentType = getMediaContentType(fullPathFileName);
        return ELearningMediaContent.builder()
                .eLearningMediaType(optionalMediaContentType.get().toString())
                .eLearningMediaFile(fullPathFileName)
                .build();
    }

    @Override
    public boolean isCourseActivityTypeSupported(String mediaContentFileName) {
        Assert.notNull(mediaContentFileName, "mediaContentFileName cannot be null");
        LOGGER.debug("Checking media content file with name: {} to see if its a supported type", mediaContentFileName);

        Optional<MediaContentType> optionalMediaContentType = getMediaContentType(mediaContentFileName);

        for (MediaContentType mType : MediaContentType.values()) {
            if(mType.equals(optionalMediaContentType.get())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Optional<MediaContentType> getMediaContentType(String mediaContentFileName) {
        Assert.notNull(mediaContentFileName, "mediaContentFileName cannot be null");

        if(mediaContentFileName.lastIndexOf(".") != -1 && mediaContentFileName.lastIndexOf(".") != 0) {
            String fileType = mediaContentFileName.substring(mediaContentFileName.lastIndexOf(".")+1);
            return Optional.of(MediaContentType.valueOf(fileType));
        }

        return Optional.empty();
    }

    @Override
    public String getMediaContentTypeUploadDirectory(MediaContentType mediaContentType) {
        Assert.notNull(mediaContentType, "mediaContentType cannot be null");

        String uploadDirectory = null;

        switch (mediaContentType) {
            case mp4:
                uploadDirectory = fileUploadLocationConfiguration.getVideos();
                break;
            case swf:
                uploadDirectory = fileUploadLocationConfiguration.getQuizzes();
                break;
            default:
                break;

        }

        return uploadDirectory;
    }

    @Override
    public ELearningCourseActivity findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding ELearningCourseActivity with id:> {}", id);
        return ieLearningCourseActivityRepository.findOne(id);
    }

    @Override
    public List<ELearningCourseActivity> findELearningCourseAcitivitiesByCourse(ELearningCourse eLearningCourse) {
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.info("Finding all ELearning course activities for eLearningCours:> {}", eLearningCourse.getCourseName());
        return ieLearningCourseActivityRepository.findELearningCourseActivities(eLearningCourse);
    }

    @Override
    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse) {
        Assert.notNull(activityName, "activityName cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding ELearningCourseActivity for eLearningCourse:> {} and activityName:> {}", eLearningCourse.getCourseName(), activityName);
        return ieLearningCourseActivityRepository.findELearningCourseActivityByCourseAndActivityName(activityName, eLearningCourse);
    }
}
