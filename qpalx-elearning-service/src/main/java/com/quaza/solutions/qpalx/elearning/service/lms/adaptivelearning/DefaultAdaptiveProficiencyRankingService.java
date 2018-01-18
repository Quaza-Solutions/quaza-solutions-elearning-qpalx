package com.quaza.solutions.qpalx.elearning.service.lms.adaptivelearning;

import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRanking;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.AdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.IAdaptiveProficiencyRankingVO;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.ProficiencyRankingTriggerTypeE;
import com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.repository.IAdaptiveProficiencyRankingRepository;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.CurriculumType;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.ELearningCurriculum;
import com.quaza.solutions.qpalx.elearning.domain.lms.curriculum.repository.IELearningCurriculumRepository;
import com.quaza.solutions.qpalx.elearning.domain.qpalxuser.QPalXUser;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyRankingScaleE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.ProficiencyScoreRangeE;
import com.quaza.solutions.qpalx.elearning.domain.subjectmatter.proficiency.SimplifiedProficiencyRankE;
import com.quaza.solutions.qpalx.elearning.domain.tutoriallevel.StudentTutorialGrade;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author manyce400
 */
@Service(DefaultAdaptiveProficiencyRankingService.SPRING_BEAN_NAME)
public class DefaultAdaptiveProficiencyRankingService  implements IAdaptiveProficiencyRankingService {



    @Autowired
    private IELearningCurriculumRepository ieLearningCurriculumRepository;

    @Autowired
    private IAdaptiveProficiencyRankingRepository iAdaptiveProficiencyRankingRepository;


    public static final String SPRING_BEAN_NAME = "quaza.solutions.qpalx.elearning.service.DefaultAdaptiveProficiencyRankingService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DefaultAdaptiveProficiencyRankingService.class);



    @Override
    @Transactional
    public void save(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        Assert.notNull(adaptiveProficiencyRanking, "adaptiveProficiencyRanking");
        LOGGER.debug("Saving adaptiveProficiencyRanking: {}", adaptiveProficiencyRanking);
        iAdaptiveProficiencyRankingRepository.save(adaptiveProficiencyRanking);
    }

    @Override
    public void defaultToLowestProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        Assert.notNull(adaptiveProficiencyRanking, "adaptiveProficiencyRanking");
        LOGGER.debug("Defaulting proficiency ranking to lowest minimum possible value");
        adaptiveProficiencyRanking.setProficiencyRankingScaleE(ProficiencyRankingScaleE.ONE);
    }

    @Override
    public double getAdaptiveProficiencyRankingMinScore(AdaptiveProficiencyRanking adaptiveProficiencyRanking) {
        if(adaptiveProficiencyRanking != null
                && adaptiveProficiencyRanking.getProficiencyRankingScaleE() != null) {
            return adaptiveProficiencyRanking.getProficiencyRankingScaleE().getProficiencyScoreRangeE().getScoreRange().getMinimum();
        }
        return 0;
    }

    @Override
    public List<AdaptiveProficiencyRanking> findStudentAdaptiveProficiencyRankings(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding all adaptive proficiency rankings for qPalxUser: {}", qPalXUser.getEmail());
        return iAdaptiveProficiencyRankingRepository.findStudentAdaptiveProficiencyRankings(qPalXUser);
    }

    @Override
    public List<AdaptiveProficiencyRanking> findStudentAdaptiveProficiencyRankings(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");
        LOGGER.debug("Finding all adaptive proficiency rankings for student: {} in studentTutorialGrade: {}", qPalXUser.getEmail(), studentTutorialGrade.getTutorialGrade());
        List<AdaptiveProficiencyRanking> adaptiveProficiencyRankings = iAdaptiveProficiencyRankingRepository.findStudentAdaptiveProficiencyRankingInStudentTutorialGrade(qPalXUser, studentTutorialGrade);
        return adaptiveProficiencyRankings;
    }

    @Override
    public List<AdaptiveProficiencyRanking> findBelowAverageAdaptiveProficiencyRankings(QPalXUser qPalXUser) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        LOGGER.info("Finding all Adaptive performance areas where student: {} is currently performing below average", qPalXUser.getEmail());

        List<AdaptiveProficiencyRanking> areasBelowAverage = new ArrayList<>();
        List<AdaptiveProficiencyRanking> currentAdaptiveProficiencyRankings = findStudentAdaptiveProficiencyRankings(qPalXUser);

        for (AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking : currentAdaptiveProficiencyRankings) {
            ELearningCurriculum eLearningCurriculum = currentAdaptiveProficiencyRanking.geteLearningCurriculum();
            ProficiencyRankingScaleE curriculumProficiencyRanking = currentAdaptiveProficiencyRanking.getProficiencyRankingScaleE();

            if(curriculumProficiencyRanking.isBelowProficiencyRanking(ProficiencyRankingScaleE.SEVEN)) {
                LOGGER.debug("Student is currently performing below average in eLearningCurriculum: {}", eLearningCurriculum.getCurriculumName());
                areasBelowAverage.add(currentAdaptiveProficiencyRanking);
            }
        }

        return areasBelowAverage;
    }

    @Override
    public AdaptiveProficiencyRanking findCurrentStudentAdaptiveProficiencyRankingForCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        LOGGER.info("Finding AdaptiveProficiencyRanking in Curriculum: {} for student: {}", eLearningCurriculum.getCurriculumName(), qPalXUser.getEmail());
        return iAdaptiveProficiencyRankingRepository.findCurrentStudentAdaptiveProficiencyRankingForCurriculum(qPalXUser, eLearningCurriculum);
    }

    @Override
    public AdaptiveProficiencyRanking buildAdaptiveProficiencyRanking(double proficiencyScore, ProficiencyRankingTriggerTypeE proficiencyRankingTriggerTypeE) {
        Assert.notNull(proficiencyRankingTriggerTypeE, "proficiencyRankingTriggerTypeE cannot be null");
        Optional<ProficiencyScoreRangeE> proficiencyScoreRangeE = ProficiencyScoreRangeE.getProficiencyScoreRangeForScore(proficiencyScore);
        ProficiencyRankingScaleE proficiencyRankingScaleE = ProficiencyRankingScaleE.getProficiencyRankingScaleForRange(proficiencyScoreRangeE.get()).get();

        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingScaleE(proficiencyRankingScaleE)
                .proficiencyRankingTriggerTypeE(proficiencyRankingTriggerTypeE)
                .proficiencyRankingEffectiveDateTime(DateTime.now())
                .build();

        return adaptiveProficiencyRanking;
    }

    @Override
    public AdaptiveProficiencyRanking averageAdaptiveProficiencyRanking(AdaptiveProficiencyRanking adaptiveProficiencyRanking1, AdaptiveProficiencyRanking adaptiveProficiencyRanking2) {
        Assert.notNull(adaptiveProficiencyRanking1, "adaptiveProficiencyRanking1 cannot be null");
        Assert.notNull(adaptiveProficiencyRanking2, "adaptiveProficiencyRanking2 cannot be null");

        LOGGER.debug("Averaging proficiency ranking for adaptiveProficiencyRanking1: {} and adaptiveProficiencyRanking2: {}", adaptiveProficiencyRanking1, adaptiveProficiencyRanking2);

        // Calculate the average
        ProficiencyRankingScaleE proficiencyRankingScaleE1 = adaptiveProficiencyRanking1.getProficiencyRankingScaleE();
        ProficiencyRankingScaleE proficiencyRankingScaleE2 = adaptiveProficiencyRanking2.getProficiencyRankingScaleE();
        ProficiencyRankingScaleE avgProficiencyRankingScaleE = ProficiencyRankingScaleE.averageProficiencyRankingScale(proficiencyRankingScaleE1, proficiencyRankingScaleE2);

        AdaptiveProficiencyRanking avgAdaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .proficiencyRankingScaleE(avgProficiencyRankingScaleE)
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.ON_DEMAND)
                .proficiencyRankingEffectiveDateTime(DateTime.now())
                .build();

        return avgAdaptiveProficiencyRanking;
    }

    @Override
    @Transactional
    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(studentTutorialGrade, "studentTutorialGrade cannot be null");

        LOGGER.debug("Building initial AdaptiveProficiencyRankings for student: {} in studentTutorialGrade: {}", qPalXUser.getEmail(), studentTutorialGrade.getTutorialGrade());

        // Find all ELearningCurriculum for this StudentTutorialGrade
        List<ELearningCurriculum> coreELearningCurriculumList = ieLearningCurriculumRepository.findAllCurriculumByTutorialGradeAndType(CurriculumType.CORE, studentTutorialGrade);
        List<ELearningCurriculum> electiveELearningCurriculumList = ieLearningCurriculumRepository.findAllCurriculumByTutorialGradeAndType(CurriculumType.ELECTIVE, studentTutorialGrade);

        List<ELearningCurriculum> allELearningCurriculumList = new LinkedList<>();
        allELearningCurriculumList.addAll(coreELearningCurriculumList);
        allELearningCurriculumList.addAll(electiveELearningCurriculumList);

        Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs = new HashSet<>();

        for(ELearningCurriculum eLearningCurriculum : allELearningCurriculumList) {
            AdaptiveProficiencyRankingVO adaptiveProficiencyRankingVO = AdaptiveProficiencyRankingVO
                    .newInstance(eLearningCurriculum.getCurriculumName(), SimplifiedProficiencyRankE.Beginner.toString());
            initialAdaptiveProficiencyRankingVOs.add(adaptiveProficiencyRankingVO);
        }

        buildInitialAdaptiveProficiencyRanking(qPalXUser, studentTutorialGrade, initialAdaptiveProficiencyRankingVOs);
    }

    @Override
    @Transactional
    public void buildInitialAdaptiveProficiencyRanking(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade, Set<IAdaptiveProficiencyRankingVO> initialAdaptiveProficiencyRankingVOs) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(initialAdaptiveProficiencyRankingVOs, "qPalXUser cannot be null");


        initialAdaptiveProficiencyRankingVOs.forEach(adptiveProficiencyRanking -> {
            if (adptiveProficiencyRanking.getSimplifiedProficiencyRank() != null) {
                AdaptiveProficiencyRanking adaptiveProficiencyRanking = buildSingleAdaptiveProficiencyRanking(qPalXUser, studentTutorialGrade, adptiveProficiencyRanking);

                if (adaptiveProficiencyRanking != null) {
                    LOGGER.info("Saving adaptiveProficiencyRanking: {}", adaptiveProficiencyRanking);
                    iAdaptiveProficiencyRankingRepository.save(adaptiveProficiencyRanking);
                }
            }
        });
    }

    @Override
    @Transactional
    public void buildAndSaveProficiencyRankingForCurriculum(QPalXUser qPalXUser, ELearningCurriculum eLearningCurriculum, SimplifiedProficiencyRankE simplifiedProficiencyRankE) {
        Assert.notNull(qPalXUser, "qPalXUser cannot be null");
        Assert.notNull(eLearningCurriculum, "eLearningCurriculum cannot be null");
        Assert.notNull(simplifiedProficiencyRankE, "simplifiedProficiencyRankE cannot be null");

        LOGGER.info("Building and saving AdaptiveProficiencyRanking for curriculum: {} with simplifiedProficiencyRankE: {}", eLearningCurriculum, simplifiedProficiencyRankE);

        ProficiencyRankingScaleE lowestProficiency = simplifiedProficiencyRankE.getProficiencyRankingScaleRange().getMinimum();
        AdaptiveProficiencyRanking adaptiveProficiencyRanking = AdaptiveProficiencyRanking.builder()
                .qpalxUser(qPalXUser)
                .proficiencyRankingScaleE(lowestProficiency)
                .eLearningCurriculum(eLearningCurriculum)
                .proficiencyRankingEffectiveDateTime(new DateTime())
                .proficiencyRankingTriggerTypeE(ProficiencyRankingTriggerTypeE.ENROLMENT)
                .build();

        iAdaptiveProficiencyRankingRepository.save(adaptiveProficiencyRanking);
    }

    protected AdaptiveProficiencyRanking buildSingleAdaptiveProficiencyRanking(QPalXUser qPalXUser, StudentTutorialGrade studentTutorialGrade, IAdaptiveProficiencyRankingVO iAdaptiveProficiencyRankingVO) {
        LOGGER.info("Building new adaptive proficiency ranking for user: {} with proficiency details: {}", qPalXUser.getEmail(), iAdaptiveProficiencyRankingVO);

        // lookup proficiency and curriculum details
        ELearningCurriculum eLearningCurriculum = ieLearningCurriculumRepository.findByELearningCurriculumNameTypeAndTutorialGrade(iAdaptiveProficiencyRankingVO.getELearningCurriculumName(), CurriculumType.CORE, studentTutorialGrade);
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
                    .curriculumCompletionPercentage(0d)
                    .build();

            return adaptiveProficiencyRanking;
        }

        LOGGER.info("Failed to load elearningCurriculum: {} or simplifiedProficiencyRankE: {} cannot build AdaptiveProficiencyRanking", eLearningCurriculum, simplifiedProficiencyRankE);
        return null;
    }


    @Override
    @Transactional
    public void closeOutAdaptiveProficiencyRanking(List<AdaptiveProficiencyRanking> adaptiveProficiencyRankingList) {
        Assert.notNull(adaptiveProficiencyRankingList, "adaptiveProficiencyRankingList cannot be null");
        Assert.isTrue(!adaptiveProficiencyRankingList.isEmpty(), "adaptiveProficiencyRankingList cannot be empty");

        LOGGER.debug("Closing out all AdaptiveProficiencyRankings in adaptiveProficiencyRankingList: {}", adaptiveProficiencyRankingList);

        for(AdaptiveProficiencyRanking adaptiveProficiencyRanking : adaptiveProficiencyRankingList) {
            adaptiveProficiencyRanking.setProficiencyRankingEndDateTime(DateTime.now());
            iAdaptiveProficiencyRankingRepository.save(adaptiveProficiencyRanking);
        }
    }

    @Override
    @Transactional
    public void recordNew(AdaptiveProficiencyRanking newAdaptiveProficiencyRanking, AdaptiveProficiencyRanking currentAdaptiveProficiencyRanking) {
        Assert.notNull(newAdaptiveProficiencyRanking, "adaptiveProficiencyRanking");
        Assert.notNull(newAdaptiveProficiencyRanking.getQPalXUser(), "QPalxUser cannot be null");
        Assert.notNull(newAdaptiveProficiencyRanking.geteLearningCurriculum(), "ELearningCurriculum cannot be null");

        Assert.notNull(currentAdaptiveProficiencyRanking, "currentAdaptiveProficiencyRanking");
        Assert.notNull(currentAdaptiveProficiencyRanking.getQPalXUser(), "Current ProficiencyRanking QPalxUser cannot be null");
        Assert.notNull(currentAdaptiveProficiencyRanking.geteLearningCurriculum(), "Current ELearningCurriculum cannot be null");

        LOGGER.debug("Recording new adaptiveProficiencyRanking: {}", newAdaptiveProficiencyRanking);

        // We need to close out the current AdaptiveProficiencyRanking before recording new one
        currentAdaptiveProficiencyRanking.setProficiencyRankingEndDateTime(DateTime.now());
        save(currentAdaptiveProficiencyRanking);

        // Save the new AdaptiveProficiencyRanking
        save(newAdaptiveProficiencyRanking);
    }

}
