package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.ContentAdminProfileRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author manyce400
 */
public interface IContentAdminProfileRecordRepository extends CrudRepository<ContentAdminProfileRecord, Long> {


    /**
     * Find and return Currently enabled Content Admin User ContentAdminProfileRecord.  Will return null IF not enabled.
     *
     * @param qPalXUser
     * @return ContentAdminProfileRecord
     */
    @Query("Select contentAdminProfileRecord From ContentAdminProfileRecord contentAdminProfileRecord Where contentAdminProfileRecord.qpalxUser = ?1 And contentAdminProfileRecord.enabled = 1")
    public ContentAdminProfileRecord findContentAdminProfileRecord(QPalXUser qPalXUser);


}
