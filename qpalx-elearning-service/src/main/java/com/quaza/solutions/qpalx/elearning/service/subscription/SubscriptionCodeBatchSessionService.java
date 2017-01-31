package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.partner.StrategicPlatformPartner;
import com.quaza.solutions.qpalx.elearning.domain.subscription.IPrepaidSubscriptionGenVO;
import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscriptionStatistic;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSessionStatistic;
import com.quaza.solutions.qpalx.elearning.domain.subscription.repository.ISubscriptionCodeBatchSessionRepository;
import com.quaza.solutions.qpalx.elearning.service.geographical.DefaultGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.geographical.IQPalXMunicipalityService;
import com.quaza.solutions.qpalx.elearning.service.jdbc.JDBCTemplateEnabledService;
import com.quaza.solutions.qpalx.elearning.service.partner.IStrategicPlatformPartnerService;
import com.quaza.solutions.qpalx.elearning.service.prepaidsubscription.IQPalxPrepaidIDService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Random;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.SubscriptionCodeBatchSessionService")
public class SubscriptionCodeBatchSessionService extends JDBCTemplateEnabledService implements ISubscriptionCodeBatchSessionService {



    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Value("classpath:/sql/subscription/view-all-open-subscription-code-batches.sql")
    private Resource externalSQLResource;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledQPalXMunicipalityService")
    private IQPalXMunicipalityService iQPalXMunicipalityService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxSubscriptionService")
    private IQPalxSubscriptionService iQPalxSubscriptionService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.StrategicPlatformPartnerService")
    private IStrategicPlatformPartnerService iStrategicPlatformPartnerService;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.DefaultQPalxPrepaidIDService")
    private IQPalxPrepaidIDService iQpalxPrepaidIDService;

    @Autowired
    private ISubscriptionCodeBatchSessionRepository iSubscriptionCodeBatchSessionRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SubscriptionCodeBatchSessionService.class);


    @Override
    public SubscriptionCodeBatchSession findByID(Long id) {
        Assert.notNull(id, "id cannot be null");
        LOGGER.info("Finding SubscriptionCodeBatchSession with id: {}", id);
        return iSubscriptionCodeBatchSessionRepository.findOne(id);
    }

    @Override
    public List<SubscriptionCodeBatchSession> findAllOpenSubscriptionBatches() {
        LOGGER.info("Finding and returning all open subscription batch sessions....");
        return iSubscriptionCodeBatchSessionRepository.findAllOpenSubscriptionCodeBatchSessions();
    }

    @Override
    public List<SubscriptionCodeBatchSessionStatistic> findAllSubscriptionCodeBatchSessionStatistic() {
        LOGGER.info("Finding and returning statistics on all SubscriptionCodeBatchSession....");
        List<SubscriptionCodeBatchSessionStatistic> results = jdbcTemplate.query(externalSQLString, SubscriptionCodeBatchSessionStatistic.newRowMapper(
                DefaultGeographicalDateTimeFormatter.DB_DATE_TIME_DISPLAY_FORMATTER, DefaultGeographicalDateTimeFormatter.DB_DATE_TIME_DISPLAY_FORMATTER
        ));
        return results;
    }

    @Override
    public List<PrepaidSubscriptionStatistic> findAllSubscriptionBatchPrepaidSubscriptionStatistic(Long subscriptionCodeBatchSessionID) {
        Assert.notNull(subscriptionCodeBatchSessionID, "subscriptionCodeBatchSessionID cannot be null");

        LOGGER.info("Finding all prepaid subscription codes for subscriptionCodeBatchSessionID: {}", subscriptionCodeBatchSessionID);

        String sql = "Select    ppaidSub.UniqueID, qSub.SubscriptionName, qSub.SubscriptionCost, qMun.Name As MunicipalityName, ppaidSub.AlreadyUsed, ppaidSub.DateCreated, ppaidSub.RedemptionDate, qUsr.Email As RedemptionUserEmail "
                   + "From      PrepaidSubscription ppaidSub "
                   + "Left      Outer Join QPalXUser qUsr on qUsr.ID = ppaidSub.QPalxUserID "
                   + "Left      Outer Join QPalXMunicipality qMun on qMun.ID = ppaidSub.QPalXMunicipalityID "
                   + "Left      Outer Join QPalXSubscription qSub on qSub.ID = ppaidSub.QPalXSubscriptionTypeID "
                   + "Where     ppaidSub.SubscriptionCodeBatchSessionID = ? ";

        Long[] uniqueArgs = new Long[]{subscriptionCodeBatchSessionID};

        List<PrepaidSubscriptionStatistic> prepaidSubscriptionStatisticList = jdbcTemplate.query(
                sql, uniqueArgs, PrepaidSubscriptionStatistic.newRowMapper(DefaultGeographicalDateTimeFormatter.DB_DATE_TIME_DISPLAY_FORMATTER, DefaultGeographicalDateTimeFormatter.DB_DATE_TIME_DISPLAY_FORMATTER)
        );

        return prepaidSubscriptionStatisticList;
    }

    @Transactional
    @Override
    public void buildNewSubscriptionBatch(IPrepaidSubscriptionGenVO iPrepaidSubscriptionGenVO) {
        Assert.notNull(iPrepaidSubscriptionGenVO, "iPrepaidSubscriptionGenVO cannot be null");
        LOGGER.info("Building new subscription batch for: {} iPrepaidSubscriptionGenVO", iPrepaidSubscriptionGenVO);

        // Find selected strategic partner
        StrategicPlatformPartner strategicPlatformPartner = iStrategicPlatformPartnerService.findByID(iPrepaidSubscriptionGenVO.getStrategicPlatformPartnerID());

        // Generate platform partner name identifier
        String subscriptionBatchUID = generateBatchStrategicPlatformPartnerUID(strategicPlatformPartner);
        LOGGER.info("Unique subscription batch generated for this batch: {}", subscriptionBatchUID);

        SubscriptionCodeBatchSession subscriptionCodeBatchSession = SubscriptionCodeBatchSession.newBuilder()
                .subscriptionCodeBatchSessionUID(subscriptionBatchUID)
                .batchGenerationDate(new DateTime())
                .strategicPlatformPartner(strategicPlatformPartner)
                .build();

        iSubscriptionCodeBatchSessionRepository.save(subscriptionCodeBatchSession);

        iQpalxPrepaidIDService.generateUniqueIds(iPrepaidSubscriptionGenVO, subscriptionCodeBatchSession);
    }

    private String generateBatchStrategicPlatformPartnerUID(StrategicPlatformPartner strategicPlatformPartner) {
        // Generate platform partner name identifier
        String partnerUID = strategicPlatformPartner.getPlatformPartnerName();
        partnerUID.replace(" ", "-");
        partnerUID = partnerUID + "-" + strategicPlatformPartner.getPlatformPartnerTypeE().toString();
        String uidNumber = generateSubscriptionBatchUID(7);
        partnerUID = partnerUID + "-" + uidNumber;
        return partnerUID;
    }

    private String generateSubscriptionBatchUID(int sIDLength) {
        Random random = new Random();
        char[] digits = new char[sIDLength];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < sIDLength; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }

        return ""+Long.parseLong(new String(digits));
    }

    @Override
    protected Resource getExternalSQLResource() {
        return externalSQLResource;
    }

}
