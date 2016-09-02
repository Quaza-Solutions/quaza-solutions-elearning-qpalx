package com.quaza.solutions.qpalx.elearning.service.prepaidsubscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;

import java.util.List;

/**
 * Created by Trading_1 on 4/29/2016.
 */
public interface IQPalxPrepaidIDService {

    public String generateUniqueId(QPalXMunicipality qPalXMunicipality, QPalXSubscription qPalXSubscription, List<String> prepaidSubscriptions);//get and set countrycode/citycode

    public void generateUniqueIds(int numberOfCodes, QPalXSubscription qPalXSubscription, QPalXMunicipality qPalXMunicipality);//get and set countrycode/citycode

    public PrepaidSubscription findByUniqueId(String obj);

    public void generateAndWritePrepaidIdsToExcel(int numofcodes, QPalXMunicipality qPalXMunicipality, QPalXSubscription qPalXSubscription, String fileName);

    public PrepaidSubscription findById(Long obj);

    public void save(PrepaidSubscription obj);

    public List<String> getAllUniqueIds();

    public void writePrepaidExcelFile(QPalXMunicipality qPalXMunicipality, QPalXSubscription qPalXSubscription, String fileName);

    //upon redemption update table with respective values - pass municipality down for extra verification - return true or false for web end
    public boolean redeemCode(String uniqueId, QPalXMunicipality qPalXMunicipality); //unique id must match country + city code

}
