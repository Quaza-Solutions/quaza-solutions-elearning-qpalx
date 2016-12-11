package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for handling all business actions on a QPalXUser.
 *
 * @author manyce400
 */
public interface IQPalxUserService {


    public void saveQPalXUser(QPalXUser qPalXUser);

    public void deleteQPalXUser(QPalXUser qPalXUser);

    public QPalXUser findQPalXUserByEmail(String userEmail);

    public QPalXUser findQPalXUserBySuccessID(String successID);

    public String generateQPalXUserSuccessID(QPalXUser qPalXUser);

    public boolean isUniqueUserMobilePhoneNumber(String mobilePhoneNumber);

    public List<QPalXUser> findAllQPalXUsersWithPageableResults(Pageable pageable);

}
