package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IQuestionBankVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.QuestionBankItem;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.QPalXELesson;

/**
 * Created by manyce400 on 10/30/16.
 */
public interface IQuestionBankService {


    public QuestionBankItem findByID(Long questionBankID);

    public QuestionBankItem findRandomQuestionBankItem(QPalXELesson qPalXELesson);

    public void createAndSaveQuestionBankItem(IQuestionBankVO iQuestionBankVO);
}
