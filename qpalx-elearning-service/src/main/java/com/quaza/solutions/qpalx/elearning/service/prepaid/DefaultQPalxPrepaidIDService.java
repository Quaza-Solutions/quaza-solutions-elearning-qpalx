package com.quaza.solutions.qpalx.elearning.service.prepaid;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.repository.IQPalxPrepaidIDRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * Created by Trading_1 on 4/26/2016.
 */

@Service("quaza.solutions.qpalx.elearning.service.DefaultQPalxPrepaidIDService")
public class DefaultQPalxPrepaidIDService implements IQPalxPrepaidIDService{

    @Autowired
    IQPalxPrepaidIDRepository iQPalxPrepaidIDRepository;

    private int counter=0;

    @Override
    public String generateUniquePrepaidId(QPalXMunicipality qPalXMunicipality) {
        PrepaidSubscription prepaidSubscription;

        String uniqueid = "";

        final String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        final int length = alpha.length();

        Random r = new Random();
        //adds random characters to uniqueid based on our alphabet
        for (int i = 0; i < 10; i++) {
            uniqueid += alpha.charAt(r.nextInt(length));
        }

        if(this.findByUniqueId(uniqueid) == null) {
            prepaidSubscription = new PrepaidSubscription();
            prepaidSubscription.setUniqueID(uniqueid);
            prepaidSubscription.setAlreadyUsed(false);

            //the country and city code have to be quantifiable from QPalxMunicipality
            prepaidSubscription.setCityCode("NY");
            prepaidSubscription.setCountryCode("USA");

            prepaidSubscription.setDateCreated(DateTime.now());
            prepaidSubscription.setRedemptionDate(null);
            this.save(prepaidSubscription);
            counter++;
        }else if(this.findByUniqueId(uniqueid) != null){
            System.out.println("Code already generated : Generating new code");
            generateUniquePrepaidId(qPalXMunicipality);
        }
        return uniqueid;
    }

    @Override
    public void generateMultipleUniqueIds(int numberOfCodes, QPalXMunicipality qPalXMunicipality) {
        for(int i=0; i<numberOfCodes; i++){
            generateUniquePrepaidId(qPalXMunicipality);
        }
        System.out.println(counter+" Codes Generated");
    }

    @Override
    public PrepaidSubscription findById(Long obj) {
        return iQPalxPrepaidIDRepository.findOne(obj);
    }

    @Override
    public void save(PrepaidSubscription obj) {
        iQPalxPrepaidIDRepository.save(obj);
    }

    @Override
    public PrepaidSubscription findByUniqueId(String uniqueid){
        return iQPalxPrepaidIDRepository.findByUniqueIdRepo(uniqueid);
    }

    @Override
    public boolean redeemCode(String uniqueid, QPalXMunicipality qPalXMunicipality){
        PrepaidSubscription prepaidSubscription = findByUniqueId(uniqueid);
        if(prepaidSubscription != null){
            if(qPalXMunicipality.getQPalXCountry().equals(prepaidSubscription.getcountryCode())){
                if(qPalXMunicipality.getCode().equals(prepaidSubscription.getcityCode())){
                    return true;
                }
            }
        }
        return false;
    }

}
