package com.quaza.solutions.qpalx.elearning.service.prepaidsubscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.repository.IQPalxPrepaidIDRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by Trading_1 on 4/26/2016.
 */

@Service("quaza.solutions.qpalx.elearning.service.DefaultQPalxPrepaidIDService")
public class DefaultQPalxPrepaidIDService implements IQPalxPrepaidIDService{

    @Autowired
    IQPalxPrepaidIDRepository iQPalxPrepaidIDRepository;

    @Override
    public String generateUniqueId(QPalXMunicipality qPalXMunicipality, Iterable<PrepaidSubscription> thisuniqueidslist) {
        PrepaidSubscription prepaidSubscription;

        String uniqueid = "";

        final String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        final int length = alpha.length();

        Random r = new Random();
        //adds random characters to uniqueid based on our alphabet
        for (int i = 0; i < 10; i++) {
            uniqueid += alpha.charAt(r.nextInt(length));
        }
        /**
        if(thisuniqueidslist.isEmpty()){
            System.out.println("Could not query list : ListNull");
        }
        if(!thisuniqueidslist.contains(uniqueid)) {
            prepaidSubscription = new PrepaidSubscription();
            prepaidSubscription.setUniqueID(uniqueid);
            prepaidSubscription.setAlreadyUsed(false);

            //the country and city code have to be quantifiable from QPalxMunicipality
            prepaidSubscription.setCityCode("NY");
            prepaidSubscription.setCountryCode("USA");

            prepaidSubscription.setDateCreated(DateTime.now());
            prepaidSubscription.setRedemptionDate(null);
            thisuniqueidslist.add(prepaidSubscription);
            this.save(prepaidSubscription);
        }else if(thisuniqueidslist.contains(uniqueid)){
            System.out.println("Code already generated : Generating new code");
            generateUniqueId(qPalXMunicipality, thisuniqueidslist);
        }
         **/
        return uniqueid;
    }

    @Override
    public void generateUniqueIds(int numberOfCodes, QPalXMunicipality qPalXMunicipality) {
        Iterable<PrepaidSubscription> uniqueIdIterable = getAllUniqueIds();
        for(int i=0; i<numberOfCodes; i++){
            generateUniqueId(qPalXMunicipality, uniqueIdIterable);
        }
    }

    @Override
    public Iterable<PrepaidSubscription> getAllUniqueIds() { return iQPalxPrepaidIDRepository.findAll(); }

    @Override
    public PrepaidSubscription findById(Long obj) {
        return iQPalxPrepaidIDRepository.findOne(obj);
    }

    @Override
    public void save(PrepaidSubscription obj) { iQPalxPrepaidIDRepository.save(obj); }

    @Override
    public PrepaidSubscription findByUniqueId(String uniqueid){ return iQPalxPrepaidIDRepository.findByUniqueIdRepo(uniqueid); }

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
