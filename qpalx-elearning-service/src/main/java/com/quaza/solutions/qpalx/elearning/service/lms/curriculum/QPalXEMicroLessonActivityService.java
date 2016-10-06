package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonActivityVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLessonActivity;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXEMicroLessonActivityRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonActivityService")
public class QPalXEMicroLessonActivityService implements IQPalXEMicroLessonActivityService {



    @Autowired
    private IQPalXEMicroLessonActivityRepository iqPalXEMicroLessonActivityRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
    private IQPalXEMicroLessonService iqPalXEMicroLessonService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXEMicroLessonActivityService.class);


    @Override
    public QPalXEMicroLessonActivity findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        return iqPalXEMicroLessonActivityRepository.findOne(id);
    }

    @Override
    public List<QPalXEMicroLessonActivity> findAllMicroLessonActivities(QPalXEMicroLesson qPalXEMicroLesson) {
        return null;
    }

    @Override
    public void createAndSaveQPalXEMicroLessonActivity(IQPalXEMicroLessonActivityVO iqPalXEMicroLessonActivityVO) {
        Assert.notNull(iqPalXEMicroLessonActivityVO, "iqPalXEMicroLessonActivityVO");
        Assert.notNull(iqPalXEMicroLessonActivityVO.getQPalXEMicroLessonID(), "Non null valid QPalXEMicroLessonID required");
        Assert.notNull(iqPalXEMicroLessonActivityVO.getELearningMediaContent(), "Non null valid ELearningMediaContent ID required");

        LOGGER.info("Creating and saving  iqPalXEMicroLessonActivityVO: {}", iqPalXEMicroLessonActivityVO);

        // Load up the EMicro Lesson
        QPalXEMicroLesson qPalXEMicroLesson =  iqPalXEMicroLessonService.findByID(iqPalXEMicroLessonActivityVO.getQPalXEMicroLessonID());

        QPalXEMicroLessonActivity qPalXEMicroLessonActivity = QPalXEMicroLessonActivity.builder()
                .microLessonActivityName(iqPalXEMicroLessonActivityVO.getMicroLessonActivityName())
                .microLessonActivityDescription(iqPalXEMicroLessonActivityVO.getMicroLessonActivityDescription())
                .microLessonActivityActive(iqPalXEMicroLessonActivityVO.isActive())
                .microLessonActivityType(iqPalXEMicroLessonActivityVO.getMicroLessonActivityTypeE())
                .eLearningMediaContent(iqPalXEMicroLessonActivityVO.getELearningMediaContent())
                .entryDate(new DateTime())
                .qPalXEMicroLesson(qPalXEMicroLesson)
                .build();

        iqPalXEMicroLessonActivityRepository.save(qPalXEMicroLessonActivity);
    }
}
