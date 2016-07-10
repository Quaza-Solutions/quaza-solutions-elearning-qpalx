package com.quaza.solutions.qpalx.elearning.service.institutions;

import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXEducationalInstitutionService {

    public QPalXEducationalInstitution findByID(Long id);

    public List<QPalXEducationalInstitution> findAll();
}
