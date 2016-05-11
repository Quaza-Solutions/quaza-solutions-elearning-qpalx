package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.repository;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalxUserRepository extends CrudRepository<QPalXUser, Long> {

    /**
     * Find and return QPalXUser with the SuccessID passed in as argument.
     *
     * @param successID
     * @return
     */
    @Query("Select qpalxUser From QPalXUser qpalxUser Where qpalxUser.successID = ?1")
    public QPalXUser findQPalxUserBySuccessID(String successID);

    /**
     * Find and return QPalXUser with the email passed in as argument.
     *
     * @param email
     * @return
     */
    @Query("Select qpalxUser From QPalXUser qpalxUser Where qpalxUser.email = ?1")
    public QPalXUser findQPalxUserByEmail(String email);

    /**
     * Find and return QPalXUser with the SuccessID passed in as argument.
     * As part of this operation fetch the UserSocialNetwork lazy Collection as well.
     *
     * @param successID
     * @return
     */
    @Query("Select qpalxUser From QPalXUser qpalxUser JOIN FETCH qpalxUser.socialNetworks Where qpalxUser.successID = ?1")
    public QPalXUser findQPalxUserBySuccessIDAndFetchSocialNetworks(String successID);


//    @Query("Select qpalxUser From QPalXUser qpalxUser JOIN FETCH qpalxUser.educationalInstitutions Where qpalxUser.successID = ?1")
//    public QPalXUser findQPalxUserBySuccessIDAndFetchUserEducationalInstitutions(String successID);


//    @Query("Select qpalxUser From QPalXUser qpalxUser JOIN FETCH qpalxUser.userSubscriptionProfiles Where qpalxUser.successID = ?1")
//    public QPalXUser findQPalxUserBySuccessIDAndFetchUserSubscriptionProfile(String successID);



    @Query("Select qpalxUser From QPalXUser qpalxUser")
    public List<QPalXUser> findAllQPalXUsersWithPageableResults(Pageable pageable);

}
