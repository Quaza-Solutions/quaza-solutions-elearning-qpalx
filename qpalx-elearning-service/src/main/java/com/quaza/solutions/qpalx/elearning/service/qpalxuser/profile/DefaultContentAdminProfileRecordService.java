package com.quaza.solutions.qpalx.elearning.service.qpalxuser.profile;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.ContentAdminProfileRecord;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.profile.repository.IContentAdminProfileRecordRepository;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialLevel;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.repository.IQPalXTutorialLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultContentAdminProfileRecordService")
public class DefaultContentAdminProfileRecordService implements IContentAdminProfileRecordService {



    @Autowired
    private IQPalXTutorialLevelRepository iqPalXTutorialLevelRepository;

    @Autowired
    private IContentAdminProfileRecordRepository iContentAdminProfileRecordRepository;

    public static final String SPRING_BEAN = "quaza.solutions.qpalx.elearning.service.DefaultContentAdminProfileRecordService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultContentAdminProfileRecordService.class);


    @Override
    public ContentAdminProfileRecord findEnabledContentAdminProfileRecord(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding ContentAdminProfileRecord for qPalXUser:> {}", qPalXUser.getEmail());
        ContentAdminProfileRecord contentAdminProfileRecord = iContentAdminProfileRecordRepository.findContentAdminProfileRecord(qPalXUser);
        return contentAdminProfileRecord;
    }

    @Override
    public List<StudentTutorialGrade> findContentAdminStudentTutorialGrades(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        // Find the ContentAdminProfileRecord first to locate a StudentTutorialLevel
        ContentAdminProfileRecord contentAdminProfileRecord = findEnabledContentAdminProfileRecord(qPalXUser);
        StudentTutorialLevel studentTutorialLevel = contentAdminProfileRecord.getStudentTutorialLevel();
        LOGGER.info("Looking up student tutorial grades for content admin with tutorial level:> {}", studentTutorialLevel);

        List<StudentTutorialGrade> studentTutorialGrades = iqPalXTutorialLevelRepository.findAllTutorialGradeForQPalXTutorialLevel(studentTutorialLevel);
        return studentTutorialGrades;
    }

}
