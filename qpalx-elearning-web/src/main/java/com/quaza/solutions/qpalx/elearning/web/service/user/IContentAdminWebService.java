package com.quaza.solutions.qpalx.elearning.web.service.user;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.ui.Model;

/**
 * @author manyce400
 */
public interface IContentAdminWebService {


    public void addContentAdminCurriculaOptions(Model model, QPalXUser qPalXUser);

}
