package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IProProfQuizVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.ProProfQuiz;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IProProfQuizRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXEMicroLesson;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXEMicroLessonService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.ProProfQuizService")
public class ProProfQuizService implements IProProfQuizService {



    @Autowired
    private IProProfQuizRepository iProProfQuizRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXEMicroLessonService")
    private IQPalXEMicroLessonService iqPalXEMicroLessonService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ProProfQuizService.class);


    @Override
    public ProProfQuiz findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.debug("Finding ProProfQuiz with ID: {}", id);
        return iProProfQuizRepository.findOne(id);
    }

    @Override
    public ProProfQuiz findByScorableActivityID(Long scorableActivityID) {
        Assert.notNull(scorableActivityID, "scorableActivityID cannot be null");
        LOGGER.debug("Finding ProProfQuiz with scorableActivityID: {}", scorableActivityID);
        return iProProfQuizRepository.findByScorableActivityID(scorableActivityID);
    }

    @Transactional
    @Override
    public void createProProfQuiz(IProProfQuizVO proProfQuizVO) {
        Assert.notNull(proProfQuizVO, "proProfQuizVO cannot be null");
        Assert.notNull(proProfQuizVO.getQPalXEMicroLessonID(), "QPalXEMicroLessonID cannot be null");

        QPalXEMicroLesson qPalXEMicroLesson = iqPalXEMicroLessonService.findByID(proProfQuizVO.getQPalXEMicroLessonID());

        ProProfQuiz proProfQuiz = ProProfQuiz.builder()
                .scorableActivityID(proProfQuizVO.getScorableActivityID())
                .scorableActivityName(proProfQuizVO.getQuizName())
                .scorableActivityDescription(proProfQuizVO.getQuizDescription())
                .maxPossibleActivityScore(proProfQuizVO.getMaxPossibleQuizScore())
                .qPalXEmbedURL(proProfQuizVO.getQuizEmbedURL())
                .proficiencyRankingScaleFloor(proProfQuizVO.getProficiencyRankingScaleFloorE())
                .proficiencyRankingScaleCeiling(proProfQuizVO.getProficiencyRankingScaleCeilingE())
                .entryDate(new DateTime())
                .active(proProfQuizVO.isActive())
                .qPalXEMicroLesson(qPalXEMicroLesson)
                .build();

        LOGGER.debug("Saving newly created proProfQuiz: {}", proProfQuiz);
        iProProfQuizRepository.save(proProfQuiz);
    }

}
