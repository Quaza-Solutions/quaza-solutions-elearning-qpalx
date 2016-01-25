package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveLearningProfile;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

/**
 *
 * @author manyce400
 */
public interface IAdaptiveLearningProfileService {


    public AdaptiveLearningProfile findAdaptiveLearningProfileForQPalxUser(QPalXUser qPalXUser);


}
