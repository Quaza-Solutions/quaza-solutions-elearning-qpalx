package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IQuestionBankVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IQuestionBankItemRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IQPalXELessonService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.QuestionBankService")
public class QuestionBankService implements IQuestionBankService {


    @Autowired
    private IQuestionBankItemRepository iQuestionBankItemRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.QPalXELessonService")
    private IQPalXELessonService iqPalXELessonService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(QuestionBankService.class);


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
        return null;
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
}
