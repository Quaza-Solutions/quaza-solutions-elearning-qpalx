package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.subscription.PrepaidSubscriptionStatistic;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;
import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSessionStatistic;
import com.quaza.solutions.qpalx.elearning.domain.subscription.repository.ISubscriptionCodeBatchSessionRepository;
import com.quaza.solutions.qpalx.elearning.service.geographical.DefaultGeographicalDateTimeFormatter;
import com.quaza.solutions.qpalx.elearning.service.jdbc.JDBCTemplateEnabledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

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

    @Override
    protected Resource getExternalSQLResource() {
        return externalSQLResource;
    }

}
