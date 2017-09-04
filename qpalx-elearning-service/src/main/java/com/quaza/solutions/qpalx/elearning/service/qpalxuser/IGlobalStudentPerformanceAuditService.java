package com.quaza.solutions.qpalx.elearning.service.qpalxuser;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.GlobalStudentPerformanceAudit;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;

import java.util.List;

/**
 * @author manyce400
 */
public interface IGlobalStudentPerformanceAuditService {

    public void saveGlobalStudentPerformanceAudit(GlobalStudentPerformanceAudit globalStudentPerformanceAudit);

    public List<GlobalStudentPerformanceAudit> findAllGlobalStudentPerformanceAuditForAuditUser(QPalXUser auditorQPalxUser);

    public List<GlobalStudentPerformanceAudit> findGlobalStudentPerformanceAuditByAuditorUserType(QPalXUser studentQPalXUser, QPalxUserTypeE qPalxUserTypeE);

}
