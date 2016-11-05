package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.QuestionBankProgress;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.progress.repository.IQuestionBankProgressRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IQuestionBankVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IQuestionBankItemRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QuestionBankService")
public class QuestionBankService implements IQuestionBankService {


    @Autowired
    private IQuestionBankItemRepository iQuestionBankItemRepository;

    @Autowired
    private IQuestionBankProgressRepository iQuestionBankProgressRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
    private IQPalXELessonService iqPalXELessonService;

    private static final Random RANDOM_GEN = new Random();


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QuestionBankService.class);


    @Override
    public void deleteQuestionBankItemByID(Long questionBankID) {
        Assert.notNull(questionBankID, "questionBankID cannot be null");
        LOGGER.debug("Deleting QuestionBankItem with ID: {}", questionBankID);
        iQuestionBankItemRepository.delete(questionBankID);
    }

    @Override
    public QuestionBankItem findByID(Long questionBankID) {
        Assert.notNull(questionBankID, "questionBankID cannot be null");
        LOGGER.debug("Finding QuestionBankItem with questionBankID: {}", questionBankID);
        return iQuestionBankItemRepository.findOne(questionBankID);
    }

    @Override
    public QuestionBankItem findRandomQuestionBankItem(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.debug("Finding random QuestionBankItem for qPalXELesson {}", qPalXELesson.getLessonName());

        // Find all question bank items
        List<QuestionBankItem> questionBankItemList =  iQuestionBankItemRepository.findAllQuestionBankItemByLesson(qPalXELesson);

        if (questionBankItemList.size() > 1) {
            int low = 0;
            int high = questionBankItemList.size();
            int randomIndex = RANDOM_GEN.nextInt(high - low) + low;
            return questionBankItemList.get(randomIndex);
        } else if(questionBankItemList.size() == 1) {
            return questionBankItemList.get(0);
        }

        return null;
    }

    @Override
    public List<QuestionBankItem> findQuestionBankItems(QPalXELesson qPalXELesson) {
        Assert.notNull(qPalXELesson, "qPalXELesson cannot be null");
        LOGGER.debug("Finding all QuestionBankItems for qPalXELesson {}", qPalXELesson.getLessonName());
        return iQuestionBankItemRepository.findAllQuestionBankItemByLesson(qPalXELesson);
    }

    @Override
    public void createAndSaveQuestionBankItem(IQuestionBankVO iQuestionBankVO) {
        Assert.notNull(iQuestionBankVO, "iQuestionBankVO cannot be null");

        LOGGER.info("Building and saving new QuestionBankItem from iQuestionBankVO: {}", iQuestionBankVO);

        QPalXELesson qPalXELesson = iqPalXELessonService.findQPalXELessonByID(iQuestionBankVO.getQPalXELessonID());
        QuestionBankItem questionBankItem = QuestionBankItem.builder()
                .questionTitle(iQuestionBankVO.getQuestionTitle())
                .questionDescription(iQuestionBankVO.getQuestionDescription())
                .entryDate(new DateTime())
                .qPalXELesson(qPalXELesson)
                .active(iQuestionBankVO.isActive())
                .build();

        iQuestionBankItemRepository.save(questionBankItem);
    }

    @Transactional
    @Override
    public void recordAdaptiveLessonStatistics(QuestionBankItem questionBankItem, QPalXUser qPalXUser) {
        Assert.notNull(questionBankItem, "questionBankItem cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Recording QuestionBankItem with ID: {} progress for user: {}", questionBankItem.getId(), qPalXUser.getEmail());

        QuestionBankProgress questionBankProgress = iQuestionBankProgressRepository.findByQuestionBankItemAndUser(questionBankItem.getId(), qPalXUser.getId());
        if(questionBankProgress != null) {
            questionBankProgress.increaseNumberOfAttempts();
            questionBankProgress.setLastAttemptEntryDate(new DateTime());
        } else {
            questionBankProgress = QuestionBankProgress.builder()
                    .questionBankItemID(questionBankItem.getId())
                    .qPalxUserID(qPalXUser.getId())
                    .numberOfAttempts(1L)
                    .lastAttemptEntryDate(new DateTime())
                    .build();
        }

        iQuestionBankProgressRepository.save(questionBankProgress);
    }
}
