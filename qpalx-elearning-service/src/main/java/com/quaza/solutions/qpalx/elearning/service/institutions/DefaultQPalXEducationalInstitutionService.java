package com.quaza.solutions.qpalx.elearning.service.institutions;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.institutions.repository.IQPalXEducationalInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(DefaultQPalXEducationalInstitutionService.SPRING_BEAN)
public class DefaultQPalXEducationalInstitutionService implements IQPalXEducationalInstitutionService {



    @Autowired
    private IQPalXEducationalInstitutionRepository iqPalXEducationalInstitutionRepository;


    public static final String SPRING_BEAN = "quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalXEducationalInstitutionService.class);


    @Override
    public QPalXEducationalInstitution findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding QPalXEducationalInstitution with id: {}", id);
        return iqPalXEducationalInstitutionRepository.findOne(id);
    }

    @Override
    public List<QPalXEducationalInstitution> findAll() {
        LOGGER.debug("Finding all QPalXEducationalInstitutions..");
        Iterable<QPalXEducationalInstitution> qPalXEducationalInstitutionIterable = iqPalXEducationalInstitutionRepository.findAll();
        return ImmutableList.copyOf(qPalXEducationalInstitutionIterable);
    }

    @Override
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipality(QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.debug("Finding all QPalXEducationalInstitution in Municipality: {}", qPalXMunicipality);
        return iqPalXEducationalInstitutionRepository.findAlEducationalInstitutionsInMunicipality(qPalXMunicipality);
    }

    @Override
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithPrimaryEducation(QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.debug("Finding all QPalXEducationalInstitution with Primary Educaiton in Municipality: {}", qPalXMunicipality);
        return iqPalXEducationalInstitutionRepository.findAlEducationalInstitutionsInMunicipalityWithPrimaryEducation(qPalXMunicipality);
    }

    @Override
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithJHSEducation(QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.debug("Finding all QPalXEducationalInstitution with JHS Education in Municipality: {}", qPalXMunicipality);
        return iqPalXEducationalInstitutionRepository.findAlEducationalInstitutionsInMunicipalityWithJHSEducation(qPalXMunicipality);
    }

    @Override
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithSHSEducation(QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.debug("Finding all QPalXEducationalInstitution with SHS Education in Municipality: {}", qPalXMunicipality);
        return iqPalXEducationalInstitutionRepository.findAlEducationalInstitutionsInMunicipalityWithSHSEducation(qPalXMunicipality);
    }

    @Override
    public List<QPalXEducationalInstitution> findAlEducationalInstitutionsInMunicipalityWithCollegeEducation(QPalXMunicipality qPalXMunicipality) {
        Assert.notNull(qPalXMunicipality, "qPalXMunicipality cannot be null");
        LOGGER.debug("Finding all QPalXEducationalInstitution with College Education in Municipality: {}", qPalXMunicipality);
        return iqPalXEducationalInstitutionRepository.findAlEducationalInstitutionsInMunicipalityWithCollegeEducation(qPalXMunicipality);
    }
}
