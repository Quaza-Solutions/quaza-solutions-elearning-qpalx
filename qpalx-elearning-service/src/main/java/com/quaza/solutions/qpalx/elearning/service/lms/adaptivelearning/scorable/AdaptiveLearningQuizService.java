package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IAdaptiveLearningQuizVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningQuizRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.AdaptiveLearningQuizService")
public class AdaptiveLearningQuizService implements IAdaptiveLearningQuizService {



    @Autowired
    private IAdaptiveLearningQuizRepository iAdaptiveLearningQuizRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
    private IQPalXEMicroLessonService iqPalXEMicroLessonService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AdaptiveLearningQuizService.class);


    @Override
    public AdaptiveLearningQuiz findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding AdaptiveLearningQuiz with ID: {}", id);
        return iAdaptiveLearningQuizRepository.findOne(id);
    }

    @Override
    public AdaptiveLearningQuiz findByScorableActivityID(Long scorableActivityID) {
        Assert.notNull(scorableActivityID, "scorableActivityID cannot be null");
        LOGGER.debug("Finding AdaptiveLearningQuiz with scorableActivityID: {}", scorableActivityID);
        return iAdaptiveLearningQuizRepository.findByScorableActivityID(scorableActivityID);
    }

    @Override
    public List<AdaptiveLearningQuiz> findAllByQPalXEMicroLesson(Long qPalXEMicroLessonID) {
        Assert.notNull(qPalXEMicroLessonID, "qPalXEMicroLessonID cannot be null");
        LOGGER.debug("Finding all AdaptiveLearningQuiz for qPalXEMicroLesson: {}", qPalXEMicroLessonID);
        return iAdaptiveLearningQuizRepository.findByQPalXEMicroLesson(qPalXEMicroLessonID);
    }

    @Transactional
    @Override
    public void buildAndSaveAdaptiveLearningQuiz(IAdaptiveLearningQuizVO proProfQuizVO) {
        Assert.notNull(proProfQuizVO, "proProfQuizVO cannot be null");
        Assert.notNull(proProfQuizVO.getQPalXEMicroLessonID(), "QPalXEMicroLessonID cannot be null");

        QPalXEMicroLesson qPalXEMicroLesson = iqPalXEMicroLessonService.findByID(proProfQuizVO.getQPalXEMicroLessonID());

        AdaptiveLearningQuiz adaptiveLearningQuiz = AdaptiveLearningQuiz.builder()
                .scorableActivityID(proProfQuizVO.getScorableActivityID())
                .scorableActivityName(proProfQuizVO.getQuizName())
                .scorableActivityDescription(proProfQuizVO.getQuizDescription())
                .maxPossibleActivityScore(proProfQuizVO.getMaxPossibleQuizScore())
                .qPalXEmbedURL(proProfQuizVO.getQuizEmbedURL())
                .entryDate(new DateTime())
                .active(proProfQuizVO.isActive())
                .qPalXEMicroLesson(qPalXEMicroLesson)
                .build();

        LOGGER.debug("Saving newly created adaptiveLearningQuiz: {}", adaptiveLearningQuiz);
        iAdaptiveLearningQuizRepository.save(adaptiveLearningQuiz);
    }

}
