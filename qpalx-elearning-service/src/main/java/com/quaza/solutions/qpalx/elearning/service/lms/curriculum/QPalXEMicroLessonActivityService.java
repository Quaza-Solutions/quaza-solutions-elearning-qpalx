package com.quaza.solutions.qpalx.elearning.service.lms.curriculum;

import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.IQPalXEMicroLessonActivityVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLessonActivity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonActivityService")
public class QPalXEMicroLessonActivityService implements IQPalXEMicroLessonActivityService {




    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QPalXEMicroLessonActivityService.class);


    @Override
    public QPalXEMicroLessonActivity findByID(Long id) {
        return null;
    }

    @Override
    public List<QPalXEMicroLessonActivity> findAllMicroLessonActivities(QPalXEMicroLesson qPalXEMicroLesson) {
        return null;
    }

    @Override
    public QPalXEMicroLessonActivity createAndSaveQPalXEMicroLessonActivity(IQPalXEMicroLessonActivityVO iqPalXEMicroLessonActivityVO) {
        return null;
    }
}
