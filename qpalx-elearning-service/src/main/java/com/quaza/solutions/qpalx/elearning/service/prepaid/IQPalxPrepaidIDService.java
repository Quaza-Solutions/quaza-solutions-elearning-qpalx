package com.quaza.solutions.qpalx.elearning.service.prepaid;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;

/**
 * Created by Trading_1 on 4/29/2016.
 */

public interface IQPalxPrepaidIDService {

    public String generateUniquePrepaidId(QPalXMunicipality qPalXMunicipality);//get and set countrycode/citycode

    public void generateMultipleUniqueIds(int numberOfCodes, QPalXMunicipality qPalXMunicipality);//get and set countrycode/citycode

    public PrepaidSubscription findByUniqueId(String obj);

    public PrepaidSubscription findById(Long obj);

    public void save(PrepaidSubscription obj);

    //upon redemption update table with respective values - pass municipality down for extra verification - return true or false for web end
    public boolean redeemCode(String uniqueId, QPalXMunicipality qPalXMunicipality); //unique id must match country + city code


}
