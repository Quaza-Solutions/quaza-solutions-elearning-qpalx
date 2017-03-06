package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.AdaptiveLearningExperience;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.scorable.repository.IAdaptiveLearningExperienceRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Caculates a total proficiency ranking for a QPalX Student cumulatively looking at the total set of all user's Adaptive Learning
 * Experiences across specific ELearningCurriculum.
 *
 * @author manyce400
 */
@Service("quaza.solutions.qpalx.elearning.service.CumulativeAdaptiveProficiencyRankingAnalyticsService")
public class CumulativeAdaptiveProficiencyRankingAnalyticsService implements IAdaptiveProficiencyRankingAnalyticsService {



    @Autowired
    private IAdaptiveLearningExperienceRepository iAdaptiveLearningExperienceRepository;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(CumulativeAdaptiveProficiencyRankingAnalyticsService.class);


    @Override
    public AdaptiveProficiencyRanking calculateAdaptiveProficiencyRanking(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");

        LOGGER.info("Calculating new AdaptiveProficiencyRanking for qPalxUser: {} and eLearningCurriculum: {}", qPalXUser.getEmail(), eLearningCurriculum);

        // Load all the list of user's adaptive learning experience and calculate
//        List<AdaptiveLearningExperience> adaptiveLearningExperiences = iAdaptiveLearningExperienceRepository.findAllQPalxUserCurriculumLearningExperiences(qPalXUser, eLearningCurriculum);
//        Optional<ProficiencyScoreRangeE> newProficiencyScoreRangeE = averageAdaptiveLearningExperience(adaptiveLearningExperiences);
//        ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(newProficiencyScoreRangeE.get()).get();
//
//        AdaptiveProficiencyRanking newQPalxUserAdaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
//                .proficiencyRankingScaleE(proficiencyRankingScaleE)
//                //.proficiencyRankingRecordDateTime(new DateTime())
//                .adaptiveLearningProfile(adaptiveLearningProfile)
//                .eLearningCurriculum(eLearningCurriculum)
//                .build();

        return null;
    }


    Optional<ProficiencyScoreRangeE> averageAdaptiveLearningExperience(List<AdaptiveLearningExperience> adaptiveLearningExperiences) {
        // Create a default initial score using a below average performer. IF there are currently no adaptiveLearningExperiences then average will only be based off initial score.
        Double defaultExperienceScoreAvg = (Double) ProficiencyScoreRangeE.BELOW_AVERAGE_PERFORMER.getScoreRange().getMinimum();
        SummaryStatistics summaryStatistics = new SummaryStatistics();
        summaryStatistics.addValue(defaultExperienceScoreAvg);

        // Add up all the proficiency scores in order to calculate the average/mean.
        if(adaptiveLearningExperiences != null && adaptiveLearningExperiences.size() > 0) {
            // reset summaryStatistics since we have valid learning experiences to average
            summaryStatistics.clear();

            adaptiveLearningExperiences.forEach(adaptiveLearningExperience -> {
                Double proficiencyScore = adaptiveLearningExperience.getProficiencyScore();
                summaryStatistics.addValue(proficiencyScore);
            });
        }

        double averageProficiencyScore = summaryStatistics.getMean();
        LOGGER.debug("Calculated new Proficiency score:> {}", averageProficiencyScore);
        System.out.println("averageProficiencyScore = " + averageProficiencyScore);
        return ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(averageProficiencyScore);
    }
}
