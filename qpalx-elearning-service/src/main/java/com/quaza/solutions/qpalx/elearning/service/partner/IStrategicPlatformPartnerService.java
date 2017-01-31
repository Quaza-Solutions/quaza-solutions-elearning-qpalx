package com.quaza.solutions.qpalx.elearning.service.partner;

import com.quaza.solutions.qpalx.elearning.domain.partner.StrategicPlatformPartner;

import java.util.List;

/**
 * @author manyce400
 */
public interface IStrategicPlatformPartnerService {


    public StrategicPlatformPartner findByID(Long id);

    public List<StrategicPlatformPartner> findAll();

}
