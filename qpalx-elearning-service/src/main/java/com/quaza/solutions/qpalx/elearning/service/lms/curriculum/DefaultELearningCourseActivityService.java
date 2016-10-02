package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.config.file.management.FileUploadLocationConfiguration;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.LearningActivityE;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourse;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCourseActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningMediaContent;
import com.quaza.solutions.qpalx.elearning.domain.lms.media.MediaContentTypeE;
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
    public ELearningMediaContent buildELearningMediaContent(File mediaContentFile, LearningActivityE learningActivityE) {
        Assert.notNull(mediaContentFile, "mediaContentFile cannot be null");
        Assert.notNull(learningActivityE, "learningActivityE cannot be null");

        LOGGER.debug("Creating new ELearningMediaContent from file: {}", mediaContentFile);

        // Get the file extension to figure out the media content type
        Optional<MediaContentTypeE> optionalMediaContentType = getMediaContentType(mediaContentFile.getName());

        // We save file name using symbolic link directory as the actual file will get uploaded to a directory outside web app context
        String symbolicFileDirectory = getMediaContentTypeVirtualDirectory(optionalMediaContentType.get(), learningActivityE);
        String symbolicFileName = symbolicFileDirectory + mediaContentFile.getName();

        return ELearningMediaContent.builder()
                .eLearningMediaType(optionalMediaContentType.get().toString())
                .eLearningMediaFile(symbolicFileName)
                .build();
    }

    @Override
    public boolean isCourseActivityTypeSupported(String mediaContentFileName) {
        Assert.notNull(mediaContentFileName, "mediaContentFileName cannot be null");
        LOGGER.debug("Checking media content file with name: {} to see if its a supported type", mediaContentFileName);

        Optional<MediaContentTypeE> optionalMediaContentType = getMediaContentType(mediaContentFileName);

        if (optionalMediaContentType.isPresent()) {
            for (MediaContentTypeE mType : MediaContentTypeE.values()) {
                if(mType.equals(optionalMediaContentType.get())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Optional<MediaContentTypeE> getMediaContentType(String mediaContentFileName) {
        Assert.notNull(mediaContentFileName, "mediaContentFileName cannot be null");

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
    public String getMediaContentTypeUploadPhysicalDirectory(MediaContentTypeE mediaContentTypeE, LearningActivityE learningActivityE) {
        Assert.notNull(mediaContentTypeE, "mediaContentTypeE cannot be null");
        Assert.notNull(learningActivityE, "learningActivityE cannot be null");

        String uploadDirectory = null;

        switch (learningActivityE) {
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
    public String getMediaContentTypeVirtualDirectory(MediaContentTypeE mediaContentTypeE, LearningActivityE learningActivityE) {
        Assert.notNull(mediaContentTypeE, "mediaContentTypeE cannot be null");
        Assert.notNull(learningActivityE, "learningActivityE cannot be null");

        String uploadDirectory = null;

        switch (learningActivityE) {
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
    public List<ELearningCourseActivity> findCourseAcitivitiesByCalendarAndCourse(TutorialLevelCalendar tutorialLevelCalendar, ELearningCourse eLearningCourse) {
        Assert.notNull(tutorialLevelCalendar, "tutorialLevelCalendar cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");
        LOGGER.debug("Finding all ELearning Course Activities for tutorialLevelCalendar: {} and eLearningCourse: {}", tutorialLevelCalendar, eLearningCourse);
        return ieLearningCourseActivityRepository.findELearningCourseActivitiesByTutorialCalendarAndCourse(tutorialLevelCalendar, eLearningCourse);
    }

    @Override
    public ELearningCourseActivity findELearningCourseActivityByCourseAndActivityName(String activityName, ELearningCourse eLearningCourse) {
        Assert.notNull(activityName, "activityName cannot be null");
        Assert.notNull(eLearningCourse, "eLearningCourse cannot be null");

        LOGGER.info("Finding ELearningCourseActivity for eLearningCourse:> {} and activityName:> {}", eLearningCourse.getCourseName(), activityName);
        return ieLearningCourseActivityRepository.findELearningCourseActivityByCourseAndActivityName(activityName, eLearningCourse);
    }
}
