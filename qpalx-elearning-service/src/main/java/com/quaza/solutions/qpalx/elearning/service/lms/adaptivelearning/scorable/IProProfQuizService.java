package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.scorable;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.IProProfQuizVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.ProProfQuiz;

/**
 * @author manyce400
 */
public interface IProProfQuizService {


    public ProProfQuiz findByID(Long id);

    public ProProfQuiz findByScorableActivityID(Long scorableActivityID);

    public void createProProfQuiz(IProProfQuizVO proProfQuizVO);

}
