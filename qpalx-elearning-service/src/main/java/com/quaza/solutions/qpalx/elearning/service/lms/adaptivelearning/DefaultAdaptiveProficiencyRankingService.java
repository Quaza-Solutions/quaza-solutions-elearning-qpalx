package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository.IAdaptiveProficiencyRankingRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCurriculumRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.SimplifiedProficiencyRankE;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService")
public class DefaultAdaptiveProficiencyRankingService  implements IAdaptiveProficiencyRankingService {



    @Autowired
    private IELearningCurriculumRepository ieLearningCurriculumRepository;

    @Autowired
    private IAdaptiveProficiencyRankingRepository iAdaptiveProficiencyRankingRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultAdaptiveProficiencyRankingService.class);


    @Override
    @Transactional
    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(initialAdaptiveProficiencyRankingVOs, "qPalXUser cannot be null");


        initialAdaptiveProficiencyRankingVOs.forEach(adptiveProficiencyRanking -> {
            AdaptiveProficiencyRanking adaptiveProficiencyRanking = buildSingleAdaptiveProficiencyRanking(qPalXUser, adptiveProficiencyRanking);

            if (adaptiveProficiencyRanking != null) {
                LOGGER.info("Saving adaptiveProficiencyRanking: {}", adaptiveProficiencyRanking);
                iAdaptiveProficiencyRankingRepository.save(adaptiveProficiencyRanking);
            }
        });

    }


    protected AdaptiveProficiencyRanking buildSingleAdaptiveProficiencyRanking(QPalXUser qPalXUser, IAdaptiveProficiencyRankingVO iAdaptiveProficiencyRankingVO) {
        LOGGER.info("Building new adaptive proficiency ranking for user: {} with proficiency details: {}", qPalXUser.getEmail(), iAdaptiveProficiencyRankingVO);

        // lookup proficiency and curriculum details
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumRepository.findByELearningCurriculumNameAndType(iAdaptiveProficiencyRankingVO.getELearningCurriculumName(), CurriculumType.CORE);
        SimplifiedProficiencyRankE simplifiedProficiencyRankE = SimplifiedProficiencyRankE.valueOf(iAdaptiveProficiencyRankingVO.getSimplifiedProficiencyRank());

        if (eLearningCurriculum != null && simplifiedProficiencyRankE != null) {
            // For proficiency doesnt matter what user selects always default to the lower range of actual proficiency
            ProficiencyRankingScaleE lowestProficiency = simplifiedProficiencyRankE.getProficiencyRankingScaleRange().getMinimum();

            AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                    .qpalxUser(qPalXUser)
                    .proficiencyRankingScaleE(lowestProficiency)
                    .eLearningCurriculum(eLearningCurriculum)
                    .proficiencyRankingEffectiveDateTime(new DateTime())
                    .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.ENROLMENT)
                    .build();

            return adaptiveProficiencyRanking;
        }

        LOGGER.info("Failed to load elearningCurriculum: {} or simplifiedProficiencyRankE: {} cannot build AdaptiveProficiencyRanking", eLearningCurriculum, simplifiedProficiencyRankE);
        return null;
    }

}
