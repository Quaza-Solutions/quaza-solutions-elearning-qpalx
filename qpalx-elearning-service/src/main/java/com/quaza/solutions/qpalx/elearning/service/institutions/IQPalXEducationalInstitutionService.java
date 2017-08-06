package com.quaza.solutions.qpalx.elearning.service.institutions;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;

import java.util.List;

/**
 * @author manyce400
 */
public interface IQPalXEducationalInstitutionService {

    public QPalXEducationalInstitution findByID(Long id);

    public List<QPalXEducationalInstitution> findAll();

    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipality(QPalXMunicipality qPalXMunicipality);

    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithPrimaryEducation(QPalXMunicipality qPalXMunicipality);

    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithJHSEducation(QPalXMunicipality qPalXMunicipality);

    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithSHSEducation(QPalXMunicipality qPalXMunicipality);

    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithCollegeEducation(QPalXMunicipality qPalXMunicipality);
}
