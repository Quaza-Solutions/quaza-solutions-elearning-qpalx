package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService")
public class DefaultAdaptiveProficiencyRankingService  implements IAdaptiveProficiencyRankingService {




    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultAdaptiveProficiencyRankingService.class);


    @Override
    public AdaptiveProficiencyRanking buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(initialAdaptiveProficiencyRankingVOs, "qPalXUser cannot be null");
        return null;
    }
}
