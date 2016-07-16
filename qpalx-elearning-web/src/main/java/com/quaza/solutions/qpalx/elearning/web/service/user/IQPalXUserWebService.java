package com.quaza.solutions.qpalx.elearning.web.service.user;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;

import java.util.Optional;

/**
 * @author manyce400
 */
public interface IQPalXUserWebService {


    /**
     * Finds and returns the currently logged in QPalXUser session.  IF no user logged in returns Optional#absent
     *
     * @return
     */
    public Optional<QPalXUser> getLoggedInQPalXUser();
}
