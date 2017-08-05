package com.quaza.solutions.qpalx.elearning.domain.institutions.repository;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository object for QPalXEducationalInstitution
 * 
 * @author manyce400
 */
public interface IQPalXEducationalInstitutionRepository extends CrudRepository<QPalXEducationalInstitution, Long> {


    @Query("Select               qPalXEducationalInstitution From QPalXEducationalInstitution qPalXEducationalInstitution "+
            "Where               qPalXEducationalInstitution.qPalXMunicipality =?1 "
    )
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipality(QPalXMunicipality qPalXMunicipality);


    @Query("Select               qPalXEducationalInstitution From QPalXEducationalInstitution qPalXEducationalInstitution "+
            "Where               qPalXEducationalInstitution.qPalXMunicipality =?1 "+
            "And                 qPalXEducationalInstitution.hasPrimaryEducation = 1"
    )
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithPrimaryEducation(QPalXMunicipality qPalXMunicipality);



    @Query("Select               qPalXEducationalInstitution From QPalXEducationalInstitution qPalXEducationalInstitution "+
            "Where               qPalXEducationalInstitution.qPalXMunicipality =?1 "+
            "And                 qPalXEducationalInstitution.hasJuniorHighEducation = 1"
    )
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithJHSEducation(QPalXMunicipality qPalXMunicipality);


    @Query("Select               qPalXEducationalInstitution From QPalXEducationalInstitution qPalXEducationalInstitution "+
            "Where               qPalXEducationalInstitution.qPalXMunicipality =?1 "+
            "And                 qPalXEducationalInstitution.hasSeniorHighEducation = 1"
    )
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithSHSEducation(QPalXMunicipality qPalXMunicipality);



    @Query("Select               qPalXEducationalInstitution From QPalXEducationalInstitution qPalXEducationalInstitution "+
            "Where               qPalXEducationalInstitution.qPalXMunicipality =?1 "+
            "And                 qPalXEducationalInstitution.hasCollegeEducation = 1"
    )
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithCollegeEducation(QPalXMunicipality qPalXMunicipality);
}
