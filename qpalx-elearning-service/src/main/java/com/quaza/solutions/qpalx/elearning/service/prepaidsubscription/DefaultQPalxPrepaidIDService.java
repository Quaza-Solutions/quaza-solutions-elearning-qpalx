package com.quaza.solutions.qpalx.elearning.service.prepaidsubscription;

import com.quaza.solutions.qpalx.elearning.domain.geographical.QPalXMunicipality;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subscription.IPrepaidSubscriptionGenVO;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.QPalXSubscription;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;
import com.quaza.solutions.qpalx.elearning.domain.subscription.repository.IQPalxPrepaidIDRepository;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.subscription.IQPalxSubscriptionService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;

/**
 * Created by Trading_1 on 4/26/2016.
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultQPalxPrepaidIDService")
public class DefaultQPalxPrepaidIDService implements IQPalxPrepaidIDService {




    @Autowired
    IQPalxPrepaidIDRepository iQPalxPrepaidIDRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iQPalXMunicipalityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iQPalxSubscriptionService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalxPrepaidIDService.class);



    @Override
    public List<String> getAllUniqueIds() {
        return iQPalxPrepaidIDRepository.getAllUniqueIdsRepo();
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
    public PrepaidSubscription findByUniqueId(String uniqueid) throws NullPointerException{
        return iQPalxPrepaidIDRepository.findByUniqueIdRepo(uniqueid);
    }

    @Transactional
    @Override
    public void generateUniqueIds(IPrepaidSubscriptionGenVO iPrepaidSubscriptionGenVO, SubscriptionCodeBatchSession subscriptionCodeBatchSession) {
        List<String> alluniqueidslist = getAllUniqueIds();
        int numberOfCodes = iPrepaidSubscriptionGenVO.getNumToGenerate();

        // Find municipality and subscription
        QPalXSubscription qPalXSubscription = iQPalxSubscriptionService.findQPalXSubscriptionByID(iPrepaidSubscriptionGenVO.getSubscriptionID());
        QPalXMunicipality qPalXMunicipality = iQPalXMunicipalityService.findQPalXMunicipalityByID(iPrepaidSubscriptionGenVO.getMunicipalityID());

        for(int i=0; i<numberOfCodes; i++){
            generateUniqueId(qPalXMunicipality, qPalXSubscription, alluniqueidslist, subscriptionCodeBatchSession);
        }
    }

    @Transactional
    @Override
    public boolean redeemCode(String uniqueId, Long subscriptionID) {
        Assert.notNull(uniqueId, "uniqueId cannot be null");
        Assert.notNull(subscriptionID, "subscriptionID cannot be null");

        LOGGER.info("Attempting to redeem prepaid code: {} in municipality: {}", uniqueId);

        PrepaidSubscription prepaidSubscription = iQPalxPrepaidIDRepository.findByUniqueIdRepoNotUsed(uniqueId);
        if(prepaidSubscription != null && prepaidSubscription.getAlreadyUsed() == false) {

            LOGGER.info("Checking to see if selected subscriptionID: {} matches subscriptionID on prepaid subscription", subscriptionID);

            if (subscriptionID.equals(prepaidSubscription.getQPalXSubscription().getId())) {
                prepaidSubscription.setRedemptionDate(DateTime.now());
                prepaidSubscription.setAlreadyUsed(true);
                save(prepaidSubscription);
                return true;
            } else {
                LOGGER.warn("Selected subscriptionID: {} does not match prepaid subscriptionID: {}", subscriptionID, prepaidSubscription.getQPalXSubscription().getId());
                return false;
            }

        } else{
            return false;
        }
    }

    @Transactional
    @Override
    public void updateRedemptionDetails(String uniqueId, QPalXUser qPalXUser) {
        Assert.notNull(uniqueId, "uniqueID cannot be null");
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");

        LOGGER.info("Updating redemption details for uniqueID: {} with user: {}", uniqueId, qPalXUser.getEmail());
        PrepaidSubscription prepaidSubscription = iQPalxPrepaidIDRepository.findByUniqueIdRepo(uniqueId);
        prepaidSubscription.setQpalxUser(qPalXUser);
        iQPalxPrepaidIDRepository.save(prepaidSubscription);
    }

    @Override
    public List<PrepaidSubscription> findAllPrepaidSubscriptionForSubscriptionCodeBatchSession(SubscriptionCodeBatchSession subscriptionCodeBatchSession) {
        Assert.notNull(subscriptionCodeBatchSession, "subscriptionCodeBatchSession cannot be null");
        LOGGER.debug("Finding all PrepaidSubscription's for subscriptionCodeBatchSession:> {}", subscriptionCodeBatchSession);
        return iQPalxPrepaidIDRepository.findAllPrepaidSubscriptionForSubscriptionCodeBatchSession(subscriptionCodeBatchSession);
    }

    private String generateUniqueId(QPalXMunicipality qPalXMunicipality, QPalXSubscription qPalXSubscription, List<String> alluniqueidslist, SubscriptionCodeBatchSession subscriptionCodeBatchSession) {
        PrepaidSubscription prepaidSubscription;

        String uniqueid = "";

        final String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        final int length = alpha.length();

        Random r = new Random();
        //adds random characters to uniqueid based on our alphabet
        for (int i = 0; i < 12; i++) {
            uniqueid += alpha.charAt(r.nextInt(length));
        }
        if(alluniqueidslist == null){
            LOGGER.error("Failed to load unique id's");
        }else
        if (!alluniqueidslist.contains(uniqueid)) {
            prepaidSubscription = new PrepaidSubscription();
            prepaidSubscription.setUniqueID(uniqueid);
            prepaidSubscription.setAlreadyUsed(false);
            prepaidSubscription.setDateCreated(DateTime.now());
            prepaidSubscription.setRedemptionDate(null);
            prepaidSubscription.setqPalXMunicipality(qPalXMunicipality);
            prepaidSubscription.setQPalXSubscription(qPalXSubscription);
            prepaidSubscription.setSubscriptionCodeBatchSession(subscriptionCodeBatchSession);
            alluniqueidslist.add(prepaidSubscription.getuniqueId());//adds to list locally making sure not to access DB
            save(prepaidSubscription);
        } else if (alluniqueidslist.contains(uniqueid)) {
            LOGGER.warn("Code already generated : Generating new code");
            generateUniqueId(qPalXMunicipality, qPalXSubscription, alluniqueidslist, subscriptionCodeBatchSession);
        }

        return uniqueid;
    }

}
