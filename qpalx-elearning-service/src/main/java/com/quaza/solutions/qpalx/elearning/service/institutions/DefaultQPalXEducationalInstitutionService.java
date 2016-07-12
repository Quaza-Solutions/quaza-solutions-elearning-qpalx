package com.quaza.solutions.qpalx.elearning.service.institutions;

import com.google.common.collect.ImmutableList;
import com.quaza.solutions.qpalx.elearning.domain.institutions.QPalXEducationalInstitution;
import com.quaza.solutions.qpalx.elearning.domain.institutions.repository.IQPalXEducationalInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultQPalXEducationalInstitutionService")
public class DefaultQPalXEducationalInstitutionService implements IQPalXEducationalInstitutionService {



    @Autowired
    private IQPalXEducationalInstitutionRepository iqPalXEducationalInstitutionRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalXEducationalInstitutionService.class);


    @Override
    public QPalXEducationalInstitution findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding QPalXEducationalInstitution with id: {}", id);
        return iqPalXEducationalInstitutionRepository.findOne(id);
    }

    @Override
    public List<QPalXEducationalInstitution> findAll() {
        LOGGER.info("Finding all QPalXEducationalInstitutions..");
        Iterable<QPalXEducationalInstitution> qPalXEducationalInstitutionIterable = iqPalXEducationalInstitutionRepository.findAll();
        return ImmutableList.copyOf(qPalXEducationalInstitutionIterable);
    }
}
