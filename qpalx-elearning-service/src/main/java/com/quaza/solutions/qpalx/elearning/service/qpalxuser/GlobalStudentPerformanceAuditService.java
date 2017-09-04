package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.GlobalStudentPerformanceAudit;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.repository.IGlobalStudentPerformanceAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(GlobalStudentPerformanceAuditService.BEAN_NAME)
public class GlobalStudentPerformanceAuditService implements IGlobalStudentPerformanceAuditService {



    @Autowired
    private IGlobalStudentPerformanceAuditRepository iGlobalStudentPerformanceAuditRepository;


    public static final String BEAN_NAME = "com.quaza.solutions.qpalx.elearning.service.qpalxuser.GlobalStudentPerformanceAuditService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GlobalStudentPerformanceAuditService.class);


    @Override
    public void saveGlobalStudentPerformanceAudit(GlobalStudentPerformanceAudit globalStudentPerformanceAudit) {
        Assert.notNull(globalStudentPerformanceAudit, "globalStudentPerformanceAudit cannot be null");
        LOGGER.info("Saving globalStudentPerformanceAudit: {}", globalStudentPerformanceAudit);
        iGlobalStudentPerformanceAuditRepository.save(globalStudentPerformanceAudit);
    }

    @Override
    public List<GlobalStudentPerformanceAudit> findAllGlobalStudentPerformanceAuditForAuditUser(QPalXUser auditorQPalxUser) {
        Assert.notNull(auditorQPalxUser, "auditorQPalxUser cannot be null");
        LOGGER.info("Finding all global performance monitoring instances for auditorQPalxUser: {}", auditorQPalxUser.getEmail());
        return iGlobalStudentPerformanceAuditRepository.findAllGlobalStudentPerformanceAuditForAuditUser(auditorQPalxUser);
    }

    @Override
    public List<GlobalStudentPerformanceAudit> findGlobalStudentPerformanceAuditByAuditorUserType(QPalXUser studentQPalXUser, QPalxUserTypeE qPalxUserTypeE) {
        Assert.notNull(studentQPalXUser, "studentQPalXUser cannot be null");
        Assert.notNull(qPalxUserTypeE, "qPalxUserTypeE cannot be null");
        LOGGER.info("Finding all GlobalStudentPerformanceAudit instances where auditor userType: {} for studentQPalXUser: {}", qPalxUserTypeE, studentQPalXUser.getEmail());
        return iGlobalStudentPerformanceAuditRepository.findGlobalStudentPerformanceAuditByAuditorUserType(studentQPalXUser, qPalxUserTypeE);
    }

}
