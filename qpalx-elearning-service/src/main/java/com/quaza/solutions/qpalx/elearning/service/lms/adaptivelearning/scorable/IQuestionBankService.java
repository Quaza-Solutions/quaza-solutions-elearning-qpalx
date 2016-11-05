package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IQuestionBankVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.List;

/**
 * Created by manyce400 on 10/30/16.
 */
public interface IQuestionBankService {


    public void deleteQuestionBankItemByID(Long questionBankID);

    public QuestionBankItem findByID(Long questionBankID);

    public QuestionBankItem findRandomQuestionBankItem(QPalXELesson qPalXELesson);

    public List<QuestionBankItem> findQuestionBankItems(QPalXELesson qPalXELesson);

    public void createAndSaveQuestionBankItem(IQuestionBankVO iQuestionBankVO);

    public void recordAdaptiveLessonStatistics(QuestionBankItem questionBankItem, QPalXUser qPalXUser);
}
