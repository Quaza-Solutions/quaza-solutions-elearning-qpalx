package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXEMicroLessonRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
public class QPalXEMicroLessonService implements IQPalXEMicroLessonService {



    @Autowired
    private IQPalXEMicroLessonRepository iqPalXEMicroLessonRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
    private IQPalXELessonService iqPalXELessonService;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXEMicroLessonService.class);


    @Override
    public QPalXEMicroLesson findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding QPalxEMicroLesson by id: {}", id);
        return iqPalXEMicroLessonRepository.findOne(id);
    }

    @Override
    public List<QPalXEMicroLesson> findQPalXEMicroLessons(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.debug("Finding all QPalxEMicroLesson's for qPalXELesson: {}", qPalXELesson);
        return iqPalXEMicroLessonRepository.findAllQPalxMicroLessons(qPalXELesson);
    }

    @Override
    public void createAndSaveQPalXEMicroLesson(IQPalXEMicroLessonVO iqPalXEMicroLessonVO) {
        Assert.notNull(iqPalXEMicroLessonVO, "iqPalXEMicroLessonVO cannot be null");
        Assert.notNull(iqPalXEMicroLessonVO.getQPalXELessonID(), "Valid non null QPalXELessonID required");
        Assert.notNull(iqPalXEMicroLessonVO.getELearningMediaContent(), "Valid non null ELearningMediaContent required");
        LOGGER.debug("Creating and saving new iqPalXEMicroLessonVO: {}", iqPalXEMicroLessonVO);

        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(iqPalXEMicroLessonVO.getQPalXELessonID());

        QPalXEMicroLesson qPalXEMicroLesson = QPalXEMicroLesson.builder()
                .microLessonName(iqPalXEMicroLessonVO.getMicroLessonName())
                .microLessonDescription(iqPalXEMicroLessonVO.getMicroLessonDescription())
                .eLearningMediaContent(iqPalXEMicroLessonVO.getELearningMediaContent())
                .qPalXELesson(qPalXELesson)
                .microLessonActive(iqPalXEMicroLessonVO.isActive())
                .entryDate(new DateTime())
                .build();

        iqPalXEMicroLessonRepository.save(qPalXEMicroLesson);
    }
}
