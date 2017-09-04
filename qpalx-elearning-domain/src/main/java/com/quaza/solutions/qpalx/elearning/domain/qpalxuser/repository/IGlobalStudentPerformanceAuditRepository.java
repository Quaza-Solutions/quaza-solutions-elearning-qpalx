package com.quaza.solutions.qpalx.elearning.domain.qpalxuser.repository;

import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.GlobalStudentPerformanceAudit;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalxUserTypeE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author manyce400
 */
public interface IGlobalStudentPerformanceAuditRepository extends CrudRepository<GlobalStudentPerformanceAudit, Long> {



    // Find all GlobalStudentPerformanceAudit instances for the Auditor user passed in as argument
    @Query(
            "Select     globalStudentPerformanceAudit From GlobalStudentPerformanceAudit globalStudentPerformanceAudit " +
            "JOIN       FETCH globalStudentPerformanceAudit.auditorQPalxUser " +
            "Where      globalStudentPerformanceAudit.auditorQPalxUser = ?1 "
    )
    public List<GlobalStudentPerformanceAudit> findAllGlobalStudentPerformanceAuditForAuditUser(QPalXUser auditorQPalxUser);


    // Find all Auditor user's monitoring the performance for the studentQPalXUser passed in as argument.
    @Query(
            "Select     globalStudentPerformanceAudit From GlobalStudentPerformanceAudit globalStudentPerformanceAudit " +
            "JOIN       FETCH globalStudentPerformanceAudit.auditorQPalxUser " +
            "Where      globalStudentPerformanceAudit.studentQPalxUser = ?1 " +
            "And        globalStudentPerformanceAudit.auditorQPalxUser.userType = ?2 "
    )
    public List<GlobalStudentPerformanceAudit> findGlobalStudentPerformanceAuditByAuditorUserType(QPalXUser studentQPalXUser, QPalxUserTypeE qPalxUserTypeE);

}
