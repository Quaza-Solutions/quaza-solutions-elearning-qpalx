package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.ContentAdminProfileRecord;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;

import java.util.List;

/**
 * @author manyce400
 */
public interface IContentAdminProfileRecordService {


    public ContentAdminProfileRecord findEnabledContentAdminProfileRecord(QPalXUser qPalXUser);

    public List<StudentTutorialGrade> findContentAdminStudentTutorialGrades(QPalXUser qPalXUser);

}
