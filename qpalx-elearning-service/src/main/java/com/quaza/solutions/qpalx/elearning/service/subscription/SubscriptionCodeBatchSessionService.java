package com.quaza.solutions.qpalx.elearning.service.subscription;

import com.quaza.solutions.qpalx.elearning.domain.subscription.SubscriptionCodeBatchSession;
import com.quaza.solutions.qpalx.elearning.domain.subscription.repository.ISubscriptionCodeBatchSessionRepository;
import com.quaza.solutions.qpalx.elearning.service.jdbc.JDBCTemplateEnabledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
    public List<SubscriptionCodeBatchSession> findAllOpenSubscriptionBatches() {
        LOGGER.info("Finding and returning all open subscription batch sessions....");
        return iSubscriptionCodeBatchSessionRepository.findAllOpenSubscriptionCodeBatchSessions();
    }

    @Override
    protected Resource getExternalSQLResource() {
        return externalSQLResource;
    }

}
