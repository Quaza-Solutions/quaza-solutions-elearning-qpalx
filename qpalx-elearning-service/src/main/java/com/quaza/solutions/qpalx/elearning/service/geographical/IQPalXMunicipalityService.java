package com.quaza.solutions.qpalx.elearning.service.geographical;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;

import java.util.List;

/**
 * Provides service functionality around dealing with QPalXMunicipality.
 *
 * @author manyce400
 */
public interface IQPalXMunicipalityService {


    public QPalXMunicipality findQPalXMunicipalityByID(Long id);

    public List<QPalXMunicipality> findAllQPalXMunicipalities();


}
