package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IQPalXEMicroLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
public class QPalXEMicroLessonService implements IQPalXEMicroLessonService {



    @Autowired
    private IQPalXEMicroLessonRepository iqPalXEMicroLessonRepository;

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


}
