package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository.IAdaptiveProficiencyRankingRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.service.lms.curriculum.IELearningCurriculumService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;

/**
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService")
public class DefaultAdaptiveProficiencyRankingService implements IAdaptiveProficiencyRankingService {



    @Autowired
    private IAdaptiveProficiencyRankingRepository iAdaptiveProficiencyRankingRepository;

    @Autowired
    @Qualifier("quaza.solutions.qpalx.elearning.service.CacheEnabledELearningCurriculumService")
    private IELearningCurriculumService ieLearningCurriculumService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultAdaptiveProficiencyRankingService.class);


    @Override
    @Transactional
    public void createStudentAdaptiveProficiencyRankings(QPalXUser qPalXUser, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(initialAdaptiveProficiencyRankingVOs, "initialAdaptiveProficiencyRankingVOs cannot be null");

        LOGGER.debug("Building initial AdaptiveProficiencyRanking for qPalXUser: {}", qPalXUser.getEmail());

        initialAdaptiveProficiencyRankingVOs.forEach(iAdaptiveProficiencyRankingVO -> {
            ELearningCurriculum eLearningCurriculum = ieLearningCurriculumService.findByELearningCurriculumID(iAdaptiveProficiencyRankingVO.getELearningCurriculumID());
            ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleEByRanking(iAdaptiveProficiencyRankingVO.getProficiencyRanking());

            createStudentAdaptiveProficiencyRanking(qPalXUser, proficiencyRankingTriggerTypeE, eLearningCurriculum, proficiencyRankingScaleE);
        });
    }

    @Override
    @Transactional
    public void createStudentAdaptiveProficiencyRanking(QPalXUser qPalXUser, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE, ELearningCurriculum eLearningCurriculum, ProficiencyScoreRangeE proficiencyScoreRangeE) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(proficiencyScoreRangeE, "proficiencyScoreRangeE cannot be null");

        LOGGER.info("Creating new AdaptiveProficiencyRanking for user: {} on eLearningCurriculum: {} with proficiencyScoreRangeE: {}",qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName(), proficiencyScoreRangeE);

        Optional<ProficiencyRankingScaleE> proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE);
        if(proficiencyRankingScaleE.isPresent()) {
            createStudentAdaptiveProficiencyRanking(qPalXUser, proficiencyRankingTriggerTypeE, eLearningCurriculum, proficiencyRankingScaleE.get());
        } else {
            LOGGER.warn("Failed to calculate a ProficiencyRankingScaleE for proficiencyScoreRangeE: {}", proficiencyScoreRangeE);
        }
    }

    @Override
    @Transactional
    public void createStudentAdaptiveProficiencyRanking(QPalXUser qPalXUser, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE, ELearningCurriculum eLearningCurriculum, ProficiencyRankingScaleE proficiencyRankingScaleE) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(proficiencyRankingScaleE, "proficiencyRankingScaleE cannot be null");

        // check to see if the user already has a current proficiency ranking for this elearning curriculum.
        AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking =  findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);
        if(currentAdaptiveProficiencyRanking != null) {
            LOGGER.info("Found a current proficiency ranking for user which will be ended now: {} ", currentAdaptiveProficiencyRanking);
            currentAdaptiveProficiencyRanking.endProficiencyRankingNow();
            iAdaptiveProficiencyRankingRepository.save(currentAdaptiveProficiencyRanking);
        }

        LOGGER.info("Creating new AdaptiveProficiencyRanking for curriculum: {} and proficiencyRankingScaleE: {}", eLearningCurriculum.getCurriculumName(), proficiencyRankingScaleE);
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .qpalxUser(qPalXUser)
                .eLearningCurriculum(eLearningCurriculum)
                .proficiencyRankingScaleE(proficiencyRankingScaleE)
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingTriggerTypeE(proficiencyRankingTriggerTypeE)
                .build();
        iAdaptiveProficiencyRankingRepository.save(adaptiveProficiencyRanking);
    }


    @Override
    public AdaptiveProficiencyRanking findCurrentStudentAdaptiveProficiencyRankingForCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        LOGGER.info("Finding current proficiency ranking for student: {} in eLearningCurriculum: {}", qPalXUser.getEmail(), eLearningCurriculum.getCurriculumName());
        return iAdaptiveProficiencyRankingRepository.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);
    }
}
