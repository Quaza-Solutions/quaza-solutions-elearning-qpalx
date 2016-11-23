package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning.statistics;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 * @author manyce400
 */
public interface IAdaptiveLearningQuizStatisticsService {


    public void recordAdaptiveLearningQuizStatistic(Long scoreableActivityID, QPalXUser qPalXUser);

}
