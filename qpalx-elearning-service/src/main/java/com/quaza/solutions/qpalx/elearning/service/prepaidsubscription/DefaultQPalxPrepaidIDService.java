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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    private List<String> generatedPrepaidSubs = new ArrayList<String>();


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultQPalxPrepaidIDService.class);


    @Override
    public void writePrepaidExcelFile(QPalXMunicipality qPalXMunicipality, QPalXSubscription qPalXSubscription, String fileName) {
        try{
            //add another column for sub type
            File file = new File(fileName + ".xls");
            FileOutputStream fileOut = new FileOutputStream(fileName + ".xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("worksheet");
            Row row = worksheet.createRow((short) 0);
            Cell cellA1 = row.createCell((short) 0);
            Cell cellB2 = row.createCell((short) 1);
            Cell cellC3 = row.createCell((short)2);
            Cell cellD4 = row.createCell((short)3);
            Cell cellE5 = row.createCell((short)4);
            cellA1.setCellValue("UniqueId");
            cellB2.setCellValue("Date Created");
            cellC3.setCellValue("Country Code");
            cellD4.setCellValue("City Code");
            cellE5.setCellValue("Subscription Type");
            int rowCount =0;
            for(int i=5; i <= generatedPrepaidSubs.size(); i+=5){
                row = worksheet.createRow((short) ++rowCount);
                Cell cellA = row.createCell((short) 0);
                Cell cellB = row.createCell((short) 1);
                Cell cellC = row.createCell((short)2);
                Cell cellD = row.createCell((short)3);
                Cell cellE = row.createCell((short)4);
                cellA.setCellValue((String) generatedPrepaidSubs.get(i-5));
                cellB.setCellValue((String)generatedPrepaidSubs.get(i-4));
                cellC.setCellValue((String)generatedPrepaidSubs.get(i-3));
                cellD.setCellValue((String)generatedPrepaidSubs.get(i-2));
                cellE.setCellValue((String)generatedPrepaidSubs.get(i-1));
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

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

        generatedPrepaidSubs.removeAll(generatedPrepaidSubs);
        for(int i=0; i<numberOfCodes; i++){
            generateUniqueId(qPalXMunicipality, qPalXSubscription, alluniqueidslist, subscriptionCodeBatchSession);
        }
    }

    @Transactional
    @Override
    public boolean redeemCode(String uniqueid, QPalXMunicipality qPalXMunicipality){
        //code and country code have to be modified
        PrepaidSubscription prepaidSubscription = iQPalxPrepaidIDRepository.findByUniqueIdRepoNotUsed(uniqueid);
        if(prepaidSubscription != null && prepaidSubscription.getAlreadyUsed() == false) {
            prepaidSubscription.setRedemptionDate(DateTime.now());
            prepaidSubscription.setAlreadyUsed(true);
            save(prepaidSubscription);
            return true;
        }else{
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
            System.out.println("Failure to load uniqueIds : null");
        }else
        if (!alluniqueidslist.contains(uniqueid)) {
            prepaidSubscription = new PrepaidSubscription();
            prepaidSubscription.setUniqueID(uniqueid);
            prepaidSubscription.setAlreadyUsed(false);
            prepaidSubscription.setDateCreated(DateTime.now());
            prepaidSubscription.setRedemptionDate(null);
            prepaidSubscription.setqPalXMunicipality(qPalXMunicipality);
            prepaidSubscription.setqPalXSubscription(qPalXSubscription);
            prepaidSubscription.setSubscriptionCodeBatchSession(subscriptionCodeBatchSession);
            alluniqueidslist.add(prepaidSubscription.getuniqueId());//adds to list locally making sure not to access DB
            save(prepaidSubscription);
            generatedPrepaidSubs.add(uniqueid);
            generatedPrepaidSubs.add(DateTime.now().toString());
            generatedPrepaidSubs.add(qPalXMunicipality.getQPalXCountry().getCountryCode());//country code column
            generatedPrepaidSubs.add(qPalXMunicipality.getCode());//city code column
            generatedPrepaidSubs.add(qPalXSubscription.getSubscriptionType().toString());
        } else if (alluniqueidslist.contains(uniqueid)) {
            System.out.println("Code already generated : Generating new code");
            generateUniqueId(qPalXMunicipality, qPalXSubscription, alluniqueidslist, subscriptionCodeBatchSession);
        }

        return uniqueid;
    }

}
